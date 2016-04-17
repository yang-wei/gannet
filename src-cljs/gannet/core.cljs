(ns gannet.core
  (:require [reagent.core :as r]))

(defn form-component []
  [:div {:class "form-input"}
   [:input {:placeholder "Paste your URL here"
            :type "input"}]
   [:button "Grab"]])

(defn mount-app! []
  (r/render-component [form-component]
                            (.getElementById js/document "app")))

(mount-app!)
