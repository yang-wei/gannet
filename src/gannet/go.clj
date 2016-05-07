(ns gannet.go
  (:require [gannet.utils :refer [extract-absolute-hrefs http-get gannet-resopnse-map]]))

(defn analyse-url [url]
  (let [hrefs (extract-absolute-hrefs url)
        results (doall (map http-get hrefs))]
    (for [result results]
      (gannet-resopnse-map @result))))

(defn wrap-results [results]
  (let [total (count (set results))]
    (assoc {} :total-count total :items results)))

(defn grab [url]
 (-> url analyse-url wrap-results)) 

;; ------ parallel http request bench marking ------
;; clj-http + map 57s
;; clj-http + pmap 10s
;; clj-http + future 33s
;; map + http/get 5s  <---- current implementation
;; pmap + http/get 8s
