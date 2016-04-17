(ns gannet.core 
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :as compojure]
            [gannet.handler :refer [index-handler analyse-handler]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.reload :refer [wrap-reload]]))

(compojure/defroutes handler
  (compojure/GET "/" request index-handler)
  (compojure/POST "/analyse" request
    (analyse-handler request)))

(defn -main []
  (jetty/run-jetty
   (-> #'handler
       (wrap-json-body {:keywords? true})
       wrap-json-response
       wrap-reload)
   {:port 3000
    :join? false}))
