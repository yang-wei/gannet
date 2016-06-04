(ns gannet.go
  (:require [gannet.utils :refer [extract-absolute-hrefs http-get gannet-response-map]]
            [clojure.core.async :refer [chan <!! >! go]]
            [gannet.crawler :refer [run-spider]]))

(defn analyse-url [url]
  (let [hrefs (extract-absolute-hrefs url)
        c (chan)]
    (doseq [href hrefs]
      (go (>! c (http-get href))))
    (loop [cur-href 0
           output []]
      (if (= cur-href (count hrefs))
        output
        (let [resp (<!! c)]
          (recur (inc cur-href)
                 (conj output resp)))))))

(defn wrap-results [results]
  (let [total (count (set results))]
    (assoc {} :total-count total :items results)))

(defn grab [url]
 (-> url analyse-url wrap-results)) 

;; ------ parallel http request bench marking ------
;;
;; (time (doall (analyse-url "https://github.com")))
;; -------------------------------------------- 
;;    naive solution: clj-http + map
;; --------------------------------------------
;; "Elapsed time: 20000 msecs"
;; 
;; -------------------------------------------- 
;;    pmap: clj-http + pmap
;; -------------------------------------------- 
;; "Elapsed time: 5389.229782 msecs"
;; 
;; -------------------------------------------- 
;;    async go, channel, clj-http
;; -------------------------------------------- 
;; "Elapsed time: 2524.214618 msecs"
;; "Elapsed time: 2720.940945 msecs"

(defn crawl [urls]
  (-> urls run-spider wrap-results))
