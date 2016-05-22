(ns gannet.crawler
  (:require [gannet.utils :refer [extract-absolute-hrefs]]
            [clojure.core.async :as async :refer [chan <!! >! go]]))

(defn async-scrape-targets
  "scrap all a link from a link (pun intended)"
  [targets]
   (let [c (chan)]
     (doseq [t targets]
       ;; grab only internal link
       (go (>! c {:page t :link (extract-absolute-hrefs t true)})))
     (loop [cur-t 0
            output #{}]
       (if (= cur-t (count targets))
         output
         (let [new-ts (<!! c)]
           ;; do something with new ts
           ;; save it into database
           ;; or analyse each link page 
           ;; or both
           ;; (do-something-with-new-ts new-ts)
           (recur (inc cur-t)
                  (concat output (:link new-ts))))))))

(defn run-spider
  ([landing-url] (run-spider landing-url 1 async-scrape-targets))
  ([landing-url depth] (run-spider landing-url depth async-scrape-targets))
  ([landing-url depth scrape-fn]
   (loop [targets (scrape-fn landing-url)
          output []
          cur-depth 0]
     (if (= cur-depth depth)
       ;; remove duplicate data
       (distinct output)
       (let [new-targets (scrape-fn (distinct targets))]
         ;; do thing or whatever for this 
         (recur new-targets
                (concat output new-targets)
                (inc cur-depth)))))))

;; (count (run-spider ["https://yang-wei.github.io"])) ;; 160
(run-spider ["http://yang-wei.github.io"] 1) ;; 15
;; (count (run-spider ["https://yang-wei.github.io"] 2)) ;; 2544
