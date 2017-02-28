(ns katabatic.helpers-test
  (:require [katabatic.helpers :as subject]
            [clojure.test :refer :all]))

(deftest helper-fn-macro-test
  (testing "helper function code generation"
    (let [fn (subject/helper-fn :test)]
      (is (= {:topics #{:test}}) (fn))
      (is (= {:topics #{:test}}) (fn {}))
      (is (= {:topics #{:other :test}}) (fn {:topics #{:other}})))))

(deftest threading-helper-functions
  (testing "order doesn't matter"
    (is
     (=
      (-> (subject/alerts)
          (subject/conditions)
          (subject/tide)
          (subject/with-api-key "APIKEY")
          (subject/for-location :city "city" :state "state")
          (subject/with-format "xml"))

      (-> (subject/for-location :city "city" :state "state")
          (subject/alerts)
          (subject/with-api-key "APIKEY")
          (subject/tide)
          (subject/with-format "xml")
          (subject/conditions))))))

(deftest for-location-test
  (testing "building the location map"

    (is (= {:location {:airport "airport"}}
           (subject/for-location :airport "airport")))

    (is (= {:location {:zip "zipcode"}}
           (subject/for-location :zip "zipcode")))

    (is (= {:location {:pws "pwscode"}}
           (subject/for-location :pws "pwscode")))

    (is (= {:location {:lat "latitude" :lon "longitude"}}
           (subject/for-location :lon "longitude" :lat "latitude")))

    (is (= {:location {:city "city" :state "state"}}
           (subject/for-location :city "city" :state "state")))

    (is (= {:location {:city "city" :country "country"}}
           (subject/for-location :country "country" :city "city")))

    (is (= {:location {:auto true}}
           (subject/for-location :auto true)))

    (is (= {:location {:ip "ip_address"}}
           (subject/for-location :ip "ip_address")))))

(deftest with-format-test
  (testing "adding the format key"
    (is (= {:format "format"}
           (subject/with-format "format")))
    (is (= {:format "format" :topics #{"topic1"}}
           (subject/with-format {:topics #{"topic1"}} "format")))))

(deftest with-api-key-test
  (testing "adding the api key"
    (is (= {:apikey "APIKEY"}
           (subject/with-api-key "APIKEY")))
    (is (= {:apikey "APIKEY" :a :b}
           (subject/with-api-key {:a :b} "APIKEY")))))
