(ns gannet.handler
  (:require [gannet.go :as go]
            [gannet.validate :as validate]
            [gannet.pages :as pages]
            [ring.util.http-response :as response]))

(defn index-handler [request-map]
  (response/ok
    (apply str (pages/home))))

(defn analyse-handler [req]
  (let [url (get-in req [:body :url])]
    (if-let [valid-url? (validate/valid-url? url)]
      (response/ok (go/grab url))
      (response/forbidden "Please make sure your URL is in correct format."))))

(defn crawler-handler [req]
  (let [urls (get-in req [:body :urls])]
    (if (every? validate/valid-url? urls)
      (response/ok (go/crawl urls))
      (response/forbidden "Please make sure your URLs are in correct format."))))

