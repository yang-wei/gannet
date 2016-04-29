(ns gannet.components.gannet-form
  (:require [reagent.core :as r]
            [gannet.style :as style]
            [ajax.core :refer [POST]]))

(defn err-handler [{:keys [status status-text]}]
  (.log js/console (str "oops: " status " " status-text)))

(defn analyse-link [url result]
  (POST "/analyse"
        {:params {:url url}
         :format :json
         :response-format :json
         :keywords? true
         :handler #(reset! result %)
         :error-handler err-handler}))

(defn component [url result]
  [:div {:class "form-input"}
   [:input {:style style/form-input
            :placeholder "Paste your URL here"
            :type "text"
            :value @url
            :on-change #(reset! url (-> % .-target .-value))}]
   [:button {:style style/form-button
             :on-click #(analyse-link @url result)} "Grab"]])
