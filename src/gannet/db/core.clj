(ns gannet.db.core
  (:require
    [yesql.core :refer [defqueries]]
    [environ.core :refer [env]]))

;; https://github.com/yogthos/config/issues/5
;; environ always cast value to string but here we need a map
;; so read-string to the rescue !
(def db-spec
  (when-let [config (:db-config env)]
    (read-string config)))

(defqueries "gannet/db/sql/domain.sql" {:connection db-spec})

;; (fetch-domains)
;;(create-domain! {:name "princess hours" :domain_url "https://princesshours.com"})

(defqueries "gannet/db/sql/link.sql" {:connection db-spec})
;;(fetch-links-by-domain {:domain_id 1})

;;(create-domain<! {:url "/help-2"
;;                  :domain_id 1
;;                  :page "/hehehe"
;;                  :status_code nil
;;                  :content_type nil})

;;(update-domain! {:status_code 200
;;                 :content_type "text/html"
;;                 :id 1})
