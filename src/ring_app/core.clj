(ns ring-app.core
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :as compojure]
            [ring-app.fetch :as fetch]
            [ring.util.http-response :as response]
            [ring.middleware.reload :refer [wrap-reload]]))

(fetch/links "https://github.com")

(defn response-handler [request-map]
  (response/ok
    (str "<html><body> your IP is: "
        (:remote-addr request-map)
        "</body></html>")))

(compojure/defroutes handler
  (compojure/GET "/" request response-handler)
  (compojure/GET "/:url" [url] (str "The url is " url)))

(defn -main []
  (jetty/run-jetty
   (-> #'handler wrap-reload)
   {:port 3000
    :join? false}))
