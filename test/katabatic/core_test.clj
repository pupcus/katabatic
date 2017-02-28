(ns katabatic.core-test
  (:require [katabatic.core :as subject]
            [clojure.test :refer :all]))

(deftest base-url-test
  (testing "building the base-url to weather underground"
    (let [topics ["topic1" "topic2" "topic3"]
          apikey "APIKEY"
          expected "http://api.wunderground.com/api/APIKEY/topic1/topic2/topic3"]
      (is (= expected (subject/base-url apikey topics))))))

(deftest format-specified-test
  (testing "does this string already have the format specified in it somewhere"
    (is (subject/format-specified? "this has json in it"))
    (is (subject/format-specified? "this has xml in it"))
    (is (not (subject/format-specified? "this has neither in it")))))

(deftest query-string-test
  (testing "building a query string"
    (is (= "view.json"
           (subject/query-string "json")))
    (is (= "view.xml"
           (subject/query-string "xml")))
    (is (= "q/arg1.format"
           (subject/query-string "format" "arg1")))
    (is (= "q/arg1/arg2/arg3.format"
           (subject/query-string "format" "arg1" "arg2" "arg3")))))

(deftest extract-args-test
  (testing "extracting the location args for the query string"
    (is (= ["airport"]
           (subject/extract-args "" {:airport "airport"})))
    (is (= ["zipcode"]
           (subject/extract-args "" {:zip "zipcode"})))
    (is (= ["pws:pwscode"]
           (subject/extract-args "" {:pws "pwscode"})))
    (is (= ["lat,lon"]
           (subject/extract-args "" {:lon "lon" :lat "lat"})))
    (is (= ["state" "city"]
           (subject/extract-args "" {:city "city" :state "state"})))
    (is (= ["country" "city"]
           (subject/extract-args "" {:city "city" :country "country"})))
    (is (= ["country" "state" "city"]
           (subject/extract-args "" {:city "city" :state "state" :country "country"})))
    (is (= ["autoip"]
           (subject/extract-args "" {:auto true})))
    (is (= ["autoip.format?geo_ip=ip_address"]
           (subject/extract-args "format" {:ip "ip_address"})))
    (is (= ["autoip.format?geo_ip=ip_address"]
           (subject/extract-args "format" {:auto true :ip "ip_address"})))
    (is (= ["autoip"]
           (subject/extract-args "" {})))))

(deftest build-url-test
  (testing "building complete urls"
    (is (= "http://api.wunderground.com/api/APIKEY/topic1/topic2/q/11111.xml"
           (subject/build-url {:apikey "APIKEY"
                               :topics ["topic1" "topic2"]
                               :location {:zip "11111"}
                               :format "xml"})))))
