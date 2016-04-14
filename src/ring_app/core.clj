(ns ring-app.core
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :as compojure]
            [ring-app.fetch :as fetch]
            [ring-app.valid-url? :as valid-url?]
            [ring.util.http-response :as response]
            [ring.middleware.reload :refer [wrap-reload]]))


(defn response-handler [request-map]
  (response/ok
    (str "<html><body> your IP is: "
        (:remote-addr request-map)
        "</body></html>")))

(defn gannet-handler [url]
  (if (valid-url? url)
    fetch/links url
    {:valid-url false :message "Url is not valid"})

(compojure/defroutes handler
  (compojure/GET "/" request response-handler)
  (compojure/GET "/:url" [url] (gannet-handler url)))

(defn -main []
  (jetty/run-jetty
   (-> #'handler wrap-reload)
   {:port 3000
    :join? false}))
