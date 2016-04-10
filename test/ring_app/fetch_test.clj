(ns ring-app.fetch-test
  (:require [clojure.test :refer :all]
            [ring-app.fetch :as fetch]))

(deftest resolve-url-test
  (testing "Resolve url correctly"
    (is (=
         (fetch/resolve-url "http://example.com" "/faq")
         "http://example.com/faq"))))

(deftest extract-attrs-test
  (testing "Extract the needed attributes"
    (let [node-data {:tag "a" :attrs {:href "http://example.com" :class "my-class" :id "my-id"}}]
    (is (=
         (fetch/extract-attrs node-data)
         {:href "http://example.com" :class "my-class"})))))

(deftest build-attrs-test 
  (testing "Extracting useful attributes from a-node and add useful information"

    (testing "When href is absolute path"
      (let [hostname "http://example.com"
            node-data {:href "http://example2.com/path" :class "my-class"}]
      (is (=
           (fetch/build-attrs hostname node-data)
           {:href "http://example2.com/path" :class "my-class" :absolute-href "http://example2.com/path" :absolute-link? true}))))

    (testing "When href is relative path"
      (let [hostname "http://example.com"
            node-data {:href "/path" :class "my-class"}]
        (is (=
             (fetch/build-attrs hostname node-data)
             {:href "/path" :class "my-class" :absolute-href "http://example.com/path" :absolute-link? false}))))))
