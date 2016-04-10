(ns ring-app.fetch
  (:require [net.cgrand.enlive-html :as html]
            [clj-http.client :as client]
            [clojurewerkz.urly.core :as urly]
            [clojure.java.io :as io]))

(defn resolve-url [hostname href]
  (if (urly/absolute? href)
    href
    (str hostname href)))

(defn extract-attrs [{:keys [attrs]}]
  (let [{:keys [href class]} attrs]
    {:href href :class class}))

(defn build-attrs [hostname {:keys [href], :as attrs}]
    (let [new-attr {:absolute-href (resolve-url hostname href)
                    :absolute-link? (urly/absolute? href)}]
     (merge attrs new-attr)))

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(fetch-url "http://github.com/)

(defn get-status-code [url]
  (:status (client/get url)))

(defn add-status-code [{:keys [absolute-href], :as attrs}]
  (let [status (get-status-code absolute-href)]
    (assoc attrs :status status)))

(defn links [url]
  (let [hostname (urly/host-of (urly/url-like url))]
    (->> (html/select (fetch-url url) [:a])
         (map extract-attrs)
         (map (partial build-attrs hostname))
         (map add-status-code))))

