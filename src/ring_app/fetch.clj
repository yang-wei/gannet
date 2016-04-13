(ns ring-app.fetch
  (:require [net.cgrand.enlive-html :as html]
            [clj-http.client :as client]
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
    (let [new-attr {:absolute-href (resolve-url protocol hostname href)
                    :absolute-link? (urly/absolute? href)}]
     (merge attrs new-attr)))

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn get-status-code [url]
  (try
    (:status (client/get url {:throw-exceptions false :follow-redirects false}))
    (catch Exception e
      nil)))

(defn add-status-code [{:keys [absolute-href], :as attrs}]
  (let [status (get-status-code absolute-href)]
    (assoc attrs :status status)))

(defn log [{:keys [absolute-href status absolute-link?]}]
  (spit "result.txt" (str absolute-href " " status " " absolute-link? "\n") :append true))

(defn links [url]
  (let [-url (urly/url-like url)
        hostname (urly/host-of -url)
        protocol (urly/protocol-of -url)]
    (->> (html/select (fetch-url url) [:a])
         (map extract-attrs)
         (map (partial build-attrs protocol hostname))
         (map add-status-code))))
