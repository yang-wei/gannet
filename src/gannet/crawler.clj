(ns gannet.crawler
  (:require [gannet.utils :refer [extract-absolute-hrefs]]
            [clojure.core.async :as async :refer [chan <!! >! go timeout]]))

(defn async-scrape-targets
  "scrap all a link from a link (pun intended)"
  ([targets] (async-scrape-targets targets 10000))
  ([targets ch-timeout]
   (let [c (timeout ch-timeout)]
     (doseq [t targets]
       (go (>! c (extract-absolute-hrefs t))))
     (loop [cur-t 0
            output #{}]
       (if (= cur-t (count targets))
         output
         (let [new-ts (<!! c)]
           (recur (inc cur-t)
                  (concat output new-ts))))))))

(defn run-spider
  ([landing-url] (run-spider landing-url 1 async-scrape-targets))
  ([landing-url depth] (run-spider landing-url depth async-scrape-targets))
  ([landing-url depth scrape-fn]
   (loop [targets (scrape-fn landing-url)
          output #{landing-url}
          cur-depth 0]
     (if (= cur-depth depth)
       output
       (let [new-targets (scrape-fn targets)]
         (recur new-targets
                (concat output new-targets)
                (inc cur-depth)))))))
