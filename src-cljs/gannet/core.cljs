(ns gannet.core
  (:require [reagent.core :as r]
            [gannet.style :as style]
            [ajax.core :refer [POST]]))

(defn table-component [links]
  [:ul
   (for [link links]
     [:li (:url link) " " (:status link)])])

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

(defn form-component [url result]
  [:div {:class "form-input"}
   [:input {:style style/form-input
            :placeholder "Paste your URL here"
            :type "text"
            :value @url
            :on-change #(reset! url (-> % .-target .-value))}]
   [:button {:style style/form-button
             :on-click #(analyse-link @url result)} "Grab"]])

(defn root-app []
  (let [url (r/atom "")
        result (r/atom nil)]
    (fn []
      [:div
        [form-component url result]
        [table-component (get-in @result [:items])]])))

(defn mount-app! []
  (r/render-component [root-app]
                      (.getElementById js/document "app")))

(mount-app!)
