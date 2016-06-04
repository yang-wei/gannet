(ns gannet.core 
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :as compojure]
            [compojure.route :as route]
            [gannet.handler :refer [index-handler analyse-handler crawler-handler]]
            [gannet.db.migration :as migration]
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

(defn start-app []
  (jetty/run-jetty
    (-> #'handler
        (wrap-json-body {:keywords? true})
        wrap-json-response
        wrap-exception-handling
        wrap-reload)
    {:port 3000
     :join? false}))

(defn -main [& args]
  (cond
    (some #{"migrate" "rollback" "reset"} args)
    (do (migration/migrate args) (System/exit 0))
    :else
    (start-app)))
