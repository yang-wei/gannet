(ns gannet.utils-test 
  (:use org.httpkit.fake)
  (:require [clojure.test :refer :all]
            [gannet.utils :refer :all]))

(deftest resolve-url-test
  (testing "Resolve url correctly"
    (is (=
         (resolve-url "http" "example.com" "/faq")
         "http://example.com/faq")
        (=
         (resolve-url "http" "example.com" "http://example.com")
         "http://example.com"))))


(deftest extract-attrs-test
  (testing "Extract the needed attributes"
    (let [node-data {:tag "a" :attrs {:href "http://example.com" :class "my-class" :id "my-id"}}]
    (is (=
         (extract-attrs node-data)
         {:href "http://example.com" :class "my-class"})))))

(deftest build-attrs-test 
  (testing "Extracting useful attributes from a-node and add useful information"

    (testing "When href is absolute path"
      (let [protocol "http"
            hostname "example.com"
            node-data {:href "http://example2.com/path" :class "my-class"}]
      (is (=
           (build-attrs protocol hostname node-data)
           "http://example2.com/path"))))

    (testing "When href is relative path"
      (let [protocol "https"
            hostname "example.com"
            node-data {:href "/path" :class "my-class"}]
        (is (=
             (build-attrs protocol hostname node-data)
             "https://example.com/path"))))))
