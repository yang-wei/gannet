(ns gannet.crawler-test 
  (:use clj-http.fake)
  (:require [clojure.test :refer :all]
            [gannet.test-helper.fake-page :refer [fake-scrap-webpage fake-relative-webpage]]
            [gannet.utils :refer [scrap-webpage]]
            [gannet.crawler :refer :all]))

(with-fake-routes {;; external link, would not be crawled
                  "http://ok-link.com" (fn [_] {:status 200 :headers {} :body "ok-link"})
                 ;; internal link, will crawl this
                  "http://example.com/relative-link" (fn [_] {:status 200 :headers {} :body fake-relative-webpage})
                  "http://example.com/test1" (fn [_] {:status 200 :headers {} :body "test1"})
                  "http://example.com/test2" (fn [_] {:status 200 :headers {} :body "test2"})
                  ;; of course not crawling external link
                  "http://not-found-link.com" (fn [_] {:status 404 :headers {} :body "not-found"})
                  "http://error-link.com" (fn [_] {:status 500 :headers {} :body "error"})}
  (binding [scrap-webpage fake-scrap-webpage]
    (let [result (run-spider ["http://example.com"])]
      (deftest crawl-test 
        (testing "Get all internal links from fake page"
          (is (=
               result
               ["http://example.com/relative-link"
                ])))))
    ;; cant test because we mocked scrap-webpage and it always return same content
    (let [result (run-spider ["http://example.com"] 2)]
      (deftest crawl-depth-test 
        (testing "Get all internal links from fake page with depth 2"
          (is (=
               result
               ["http://example.com/relative-link"
                ;; "http://example.com/test1"
                ;;"http://example.com/test2"
                ])))))))
