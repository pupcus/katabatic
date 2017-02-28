(ns katabatic.core
  (:require [clj-http.client :as http]
            [clojure.string :as str]))

(defn wunderground [url]
  (:body
   (http/get url)))

(def api-url "http://api.wunderground.com/api")

(defn base-url [apikey topics]
  (format "%s/%s/%s" api-url apikey (str/join "/" (map name topics))))

(defn format-specified? [^String s]
  (some
   (fn [fs]
     (.contains s fs))
   ["json" "xml"]))

(defn query-string [format & args]
  (let [base_qs (when args
                  (str/join "/" args))
        qs (when base_qs
             (if (format-specified? base_qs)
               base_qs
               (str base_qs "." format)))]
    (str/join
     "/"
     (if qs
       ["q" qs]
       [(str "view." format)]))))

(defn extract-args [format {:keys [lat lon country city state zip airport pws auto ip]
                            :as location}]
  (cond
    airport [airport]

    zip [zip]

    pws [(str "pws:" pws)]

    (and lat
         lon) [(str lat "," lon)]

    (and city
         state
         (empty? country)) [state city]

    (and country
         city
         (empty? state)) [country city]

    (or auto
        ip) [(str "autoip"
                  (when ip
                    (str "." format "?geo_ip=" ip)))]

    :default ["autoip"]))

(defn build-url [{:keys [apikey topics format location]
                  :or {topics ["conditions"]
                       format "json"}
                  :as options}]
  (let [args (extract-args format location)]
    (str/join
     "/"
     [(base-url apikey topics)
      (apply query-string format args)])))

(defn get-weather [options]
  (wunderground (build-url options)))
