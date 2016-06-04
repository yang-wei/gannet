(ns gannet.go-test 
  (:use clj-http.fake)
  (:require [clojure.test :refer :all]
            [gannet.test-helper.fake-page :refer [fake-scrap-webpage]]
            [gannet.utils :refer [scrap-webpage]]
            [gannet.go :refer :all]))

(with-fake-routes {"http://ok-link.com" (fn [_] {:status 200 :headers {} :body "ok-link"})
                   "http://example.com/relative-link" (fn [_] {:status 200 :headers {} :body "relative-link"})
                   "http://not-found-link.com" (fn [_] {:status 404 :headers {} :body "not-found"})
                   "http://error-link.com" (fn [_] {:status 500 :headers {} :body "error"})}
  (binding [scrap-webpage fake-scrap-webpage]
    (let [result (grab "http://example.com")]
      (deftest grab-test 
        (testing "Successfully get 3 links from fake page"
          (is (=
               (get result :total-count)
               4)))))))
