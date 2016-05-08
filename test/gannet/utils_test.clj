(ns gannet.utils-test 
  (:require [clojure.test :refer :all]
            [gannet.test-helper.fake-page :refer [fake-scrap-webpage]]
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

(deftest extract-absolute-hrefs-test
  (binding [scrap-webpage fake-scrap-webpage]
    (testing "Get all absolute url from webpage"
      (let [result (extract-absolute-hrefs "http://example.com")]
        (is (=
             (count result)
             4))
        (is (=
             (set result)
             #{"http://ok-link.com",
               "http://not-found-link.com",
               "http://error-link.com",
               "http://example.com/relative-link"
               }))))
    (testing "Get all  absolute url from webpage"
      (let [result (extract-absolute-hrefs "http://example.com" true)]
        (is (=
             (count result)
             1))
        (is (=
             (set result)
             #{"http://example.com/relative-link"}))))))
