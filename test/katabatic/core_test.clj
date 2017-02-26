(ns katabatic.core-test
  (:require [katabatic.core :as subject]
            [clojure.test :refer :all]))

(deftest forecast-function
  (testing "forecast functin returns a valid forecast"
    (let [city "Richmond"
          state "VA"
          expected-forecast {:forecast {:temp "71"}}]
      (is (= expected-forecast (subject/forecast city state))))) )

