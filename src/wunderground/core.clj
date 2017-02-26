(ns wunderground.core
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(defn get-configuration []
  (-> ".lein-env"
      slurp
      read-string))

(get-configuration)

(defn get-key []
  (get-in (get-configuration) [:wunderground :key]))

(get-key)

(def base-url "http://api.wunderground.com/api")

(defn get-some-data [topic {:keys [city state zip] :as location}]
  (:body
   (http/get (format "%s/%s/%s/q/%s/%s.json" base-url (get-key) (name topic) state city))))

(get-in

 [:current_observation :wind_mph])


(json/decode
 (get-some-data :conditions {:state "VA" :city "Midlothian"})
 true)
