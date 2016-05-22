(ns gannet.db.migration
  (:require
    [migratus.core :as migratus]
    [gannet.db.core :refer [db-spec]]))

(defn parse-ids [args]
  (map #(Long/parseLong %) (rest args)))

(defn migrate [args]
  (let [config {:store :database
                :migration-dir "migrations"
                :db db-spec}]
    (case (first args)
      "migrate"
      (if (> (count args) 1)
        (apply migratus/up config (parse-ids args))
        (migratus/migrate config))
      "rollback"
      (if (> (count args) 1)
        (apply migratus/down config (parse-ids args))
        (migratus/rollback config))
      "reset"
        (migratus/reset config))))
