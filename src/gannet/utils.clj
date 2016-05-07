(ns gannet.utils
  (:require [net.cgrand.enlive-html :as html]
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

(defn ^:dynamic scrap-webpage [url]
  (html/html-resource (java.net.URL. url)))

(defn extract-absolute-hrefs [url]
  (let [-url (urly/url-like url)
        hostname (urly/host-of -url)
        protocol (urly/protocol-of -url)]
    (->> (html/select (scrap-webpage url) [:a])
         (map extract-attrs)
         (map (partial build-attrs protocol hostname)))))

(defn gannet-response-map [resp]
  {:status (:status resp)
   :url (-> resp :opts :url)
   :redirects (-> resp :opts :trace-redirects)})

(defn http-get [url]
  ;; can turn off redirect flag here
  (http/get url {:follow-redirects true}))
