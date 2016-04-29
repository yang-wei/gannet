(ns gannet.components.result-table
  (:require [reagent.core :as r]
            [gannet.style :as style]
            [ajax.core :refer [POST]]))

(defn component [links]
  [:ul
   (for [link links]
     [:li (:url link) " " (:status link)])])
