(ns gannet.core
  (:require [reagent.core :as r]
            [gannet.components.gannet-form :as gannet-form]
            [gannet.components.result-table :as result-table]))

(defn root-app []
  (let [url (r/atom "")
        result (r/atom nil)]
    (fn []
      [:div
        [gannet-form/component url result]
        [result-table/component (get-in @result [:items])]])))

(defn mount-app! []
  (r/render-component [root-app]
                      (.getElementById js/document "app")))

(mount-app!)
