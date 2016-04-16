(ns ring-app.go
  (:require [net.cgrand.enlive-html :as html]
            [clj-http.client :as client]
            [org.httpkit.client :as http]
            [clojurewerkz.urly.core :as urly]
            [clojure.java.io :as io]))

(defn resolve-url [protocol hostname href]
  (if (urly/absolute? href)
    href
    (str protocol "://" hostname href)))

(defn extract-attrs [{:keys [attrs]}]
  (let [{:keys [href class]} attrs]
    {:href href :class class}))

(defn build-attrs [protocol hostname {:keys [href], :as attrs}]
  (resolve-url protocol hostname href))

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn add-status-code [{:keys [absolute-href], :as attrs}]
  (let [status (get-status-code absolute-href)]
    (assoc attrs :status status)))

(defn extract-absolute-hrefs [url]
  (let [-url (urly/url-like url)
        hostname (urly/host-of -url)
        protocol (urly/protocol-of -url)]
    (->> (html/select (fetch-url url) [:a])
         (map extract-attrs)
         (map (partial build-attrs protocol hostname)))))

(defn response-map [resp]
  {:status (:status resp)
   :url (-> resp :opts :url)
   :redirects (-> resp :opts :trace-redirects)})

(defn http-get [url]
  ;; can turn off redirect flag here
  (http/get url {:follow-redirects true}))

(defn analyse-url [url]
  (let [data []
        hrefs (extract-absolute-hrefs url)
        results (doall (map http-get hrefs))]
    (for [result results]
      (conj data (response-map @result)))))

(defn wrap-results [results]
  (let [total (count (set results))]
    (assoc {} :total-count total :items results)))

(defn grab [url]
 (-> url analyse-url wrap-results)) 

(grab "https://github.com")

;; ------ parallel http request bench marking ------
;; clj-http + map 57s
;; clj-http + pmap 10s
;; clj-http + future 33s
;; map + http/get 5s  <---- current implementation
;; pmap + http/get 8s
