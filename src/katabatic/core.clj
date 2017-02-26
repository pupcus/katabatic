(ns katabatic.core
  (:require [cheshire.core :as json]
            [clj-http.client :as http]
            [clojure.string :as str]))

(defn get-configuration []
  (-> ".lein-env"
      slurp
      read-string))

(defn get-key []
  (get-in (get-configuration) [:wunderground :key]))

(defn decode [results]
  (try
    (json/decode results true)
    (catch Exception e
      results)))

(defn wunderground [url]
  (decode
   (:body
    (http/get url))))

(def api-url "http://api.wunderground.com/api")

(defn base-url [topics]
  (format "%s/%s/%s" api-url (get-key) (str/join "/" (map name topics))))

(defn format-specified? [s]
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

(defn extract-args [{:keys [format lat lon country city state zip airport pws auto ip]
                     :or {format "json"}}]
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

(defn build-url
  ([topics] (build-url topics {:format "json"}))
  ([topics {:keys [format] :as options :or {format "json"}}]
   (let [args (extract-args options)]
     (str/join
      "/"
      [(base-url topics)
       (apply query-string format args)]))))

(defn weather-info
  ([topics] (weather-info topics {}))
  ([topics options]
   (wunderground (build-url topics options))))

(defn get-weather [{:keys [topics location format] :as request :or {format "json"}}]
  (let [options (merge location {:format format})]
    (weather-info topics options)))

(comment
  "Example request"
  (weather-info [:conditions
                 :forecast10day
                 :yesterday
                 :geolookup
                 :astronomy
                 :alerts
                 :satellite
                 :tide
                 :rawtide
                 :almanac
                 :currenthurricane
                 :radar
                 :webcams]
                {:lat "38.876078"
                 :lon "-77.157309"
                 :format "json"}))





