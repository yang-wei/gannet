(ns gannet.core
  (:require [reagent.core :as r
             gannet.style :as style]))

(defn form-component []
  [:div {:class "form-input"}
   [:input {:style style/form-input
            :placeholder "Paste your URL here"
            :type "input"}]
   [:button {:style style/form-button} "Grab"]])

(defn mount-app! []
  (r/render-component [form-component]
                      (.getElementById js/document "app")))

(mount-app!)
