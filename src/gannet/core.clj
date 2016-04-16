(ns gannet.core 
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :as compojure]
            [gannet.go :as go]
            [gannet.validate :as validate]
            [gannet.pages :as pages]
            [ring.util.http-response :as response]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn response-handler [request-map]
  (response/ok
    (apply str (pages/home))))

(defn analyse-handler [req]
  (let [url (get-in req [:body :url])]
    (if-let [valid-url? (validate/valid-url? url)]
      (response/ok {:url url})
      (response/forbidden "Please make sure your URL is in correct format."))))

(compojure/defroutes handler
  (compojure/GET "/" request response-handler)
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
