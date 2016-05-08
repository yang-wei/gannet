(ns gannet.go-test 
  (:use org.httpkit.fake)
  (:require [org.httpkit.client :as http])
  (:require [clojure.test :refer :all]
            [gannet.test-helper.fake-page :refer [fake-scrap-webpage]]
            [gannet.utils :refer [scrap-webpage]]
            [gannet.go :refer :all]))

(with-fake-http ["http://ok-link.com" 200
                 "http://example.com/relative-link" 200
                 "http://not-found-link.com" 404 
                 "http://error-link.com" 500]
  (binding [scrap-webpage fake-scrap-webpage]
    (let [result (grab "http://example.com")]
      (deftest grab-test 
        (testing "Successfully get 3 links from fake page"
          (is (=
               (get result :total-count)
               4)))))))
