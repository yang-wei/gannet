(ns gannet.core 
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :as compojure]
            [compojure.route :as route]
            [gannet.handler :refer [index-handler analyse-handler crawler-handler]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [ring.middleware.reload :refer [wrap-reload]]))

(compojure/defroutes handler
  (compojure/GET "/" request index-handler)
  (compojure/POST "/analyse" request (analyse-handler request))
  (compojure/POST "/crawler" request (crawler-handler request))
  (route/resources "/"))

(defn status-code-for [cause]
  (case cause
    :forbidden-request 403
    400))

(defn wrap-exception-handling
  [handler]
  (fn [request]
    (try
      (handler request)
      (catch clojure.lang.ExceptionInfo e
        (let [cause (:cause (ex-info e))
              status-code (status-code-for cause)]
          {:status status-code :body (.getMessage e)})))))

(defn -main []
  (jetty/run-jetty
   (-> #'handler
       (wrap-json-body {:keywords? true})
       wrap-json-response
       wrap-exception-handling
       wrap-reload)
   {:port 3000
    :join? false}))
