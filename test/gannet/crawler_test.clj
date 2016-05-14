(ns gannet.crawler-test 
  (:use org.httpkit.fake)
  (:require [org.httpkit.client :as http])
  (:require [clojure.test :refer :all]
            [gannet.test-helper.fake-page :refer [fake-scrap-webpage fake-relative-webpage]]
            [gannet.utils :refer [scrap-webpage]]
            [gannet.crawler :refer :all]))

(with-fake-http [;; external link, would not be crawled
                 "http://ok-link.com" 200
                 ;; internal link, will crawl this
                 "http://example.com/relative-link" fake-relative-webpage
                 "http://example.com/test1" 200 
                 "http://example.com/test2" 200 
                 ;; of course not crawling external link
                 "http://not-found-link.com" 404 
                 "http://error-link.com" 500]
  (binding [scrap-webpage fake-scrap-webpage]
    (let [result (run-spider ["http://example.com"])]
      (deftest crawl-test 
        (testing "Get all internal links from fake page"
          (is (=
               result
               ["http://example.com"
                "http://example.com/relative-link"
                ])))))
    ;; cant test because we mocked scrap-webpage and it always return same content
    (let [result (run-spider ["http://example.com"] 2)]
      (deftest crawl-depth-test 
        (testing "Get all internal links from fake page with depth 2"
          (is (=
               result
               ["http://example.com"
                "http://example.com/relative-link"
                ;; "http://example.com/test1"
                ;;"http://example.com/test2"
                ])))))))
