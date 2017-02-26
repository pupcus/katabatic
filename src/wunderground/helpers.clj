(ns wunderground.helpers)

(defmacro helper-fn [kw]
  `(fn fn#
     ([{:keys [~'topics] :as ~'m :or { ~'topics #{}}}]
      (assoc ~'m :topics (conj ~'topics ~kw)))
     ([] (fn# {}))))

(def alerts (helper-fn :alerts))
(def almanac (helper-fn :almanac))
(def astronomy (helper-fn :astronomy))
(def conditions (helper-fn :conditions))
(def hurricane (helper-fn :currenthurricane))
(def forecast (helper-fn :forecast))
(def ten-day-forecast (helper-fn :forecast10day))
(def geolookup (helper-fn :geolookup))
(def history (helper-fn :history))
(def hourly-forecast (helper-fn :hourly))
(def ten-day-hourly-forecast (helper-fn :hourly10day))
(def raw-tide (helper-fn :rawtide))
(def satellite (helper-fn :satellite))
(def tide (helper-fn :tide))
(def webcams (helper-fn :webcams))
(def yesterday (helper-fn :yesterday))

(defn for-location [& args]
  (let [arg1 (first args)
        have-map? (and arg1 (map? arg1))
        m (if have-map? arg1 {})
        args (if have-map? (rest args) args)]
    (assoc m :location (apply hash-map args))))

(defn with-format
  ([format] (with-format {} format))
  ([m format]
   (assoc m :format (name format))))
