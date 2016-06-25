(ns gannet.utils
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
  "Filter all needed attributes"
  (resolve-url protocol hostname href))

(defn ^:dynamic scrap-webpage [url]
  (try
    (html/html-resource (java.net.URL. url))
    (catch Exception e
      (throw
        (ex-info
          (format "%s is not available" url)
          {:cause :forbidden-request})))))

(defn extract-absolute-hrefs
  "Extract all url on a webpage. If second arg is set to true, only urls which have same domain will be returned"
  ([url] (extract-absolute-hrefs url false))
  ([url same-origin?]
   (try
     (let [-url (urly/url-like url)
           hostname (urly/host-of -url)
           protocol (urly/protocol-of -url)
           same-origin-predicate (if same-origin?
                                   #(= hostname (urly/host-of %))
                                   identity)]
    (->> (html/select (scrap-webpage url) [:a])
         (map extract-attrs) ;; TODO: minus the rounds of iteration
         (map (partial build-attrs protocol hostname))
         set                 ;; make sure it's unique
         (filter same-origin-predicate)))
     ;; TODO: handler invalid url
     (catch Exception e []))))

(defn gannet-response-map [resp]
  {:status (:status resp)
   :url (:url resp)})

(defn http-get [url]
  ;; can turn off redirect flag here
  (let [result (client/get url {:throw-exceptions false :insecure? true :follow-redirects false })]
    {:url url
     :status (:status result)}))
