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
