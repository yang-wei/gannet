(ns gannet.components.result-table
  (:require [reagent.core :as r]
            [gannet.style :as style]
            [cljsjs.fixed-data-table]))

(enable-console-print!)

(def Table(r/adapt-react-class js/FixedDataTable.Table))
(def Column (r/adapt-react-class js/FixedDataTable.Column))
(def Cell (r/adapt-react-class js/FixedDataTable.Cell))

(defn get-value [data index key] (-> data (nth index) (get key) str))

(defn header-cell [{:keys [title]}]
  (fn [props]
    (r/as-element [Cell title])))

(defn text-cell [{:keys [row-index field data]}]
  (fn [props]
    (let [index (.-rowIndex props)
          keyword-field (keyword field)
          value (get-value data index keyword-field)]
      (r/as-element [Cell value]))))

(defn component [data]
  (let [url #(:url %)
        status #(:status %)
        rows-count (count data)] 
    (when (> rows-count 0)
      [Table {:width 500
              :height 400
              :rowHeight 50
              :headerHeight 50
              :rowsCount rows-count}
       [Column {:cell (text-cell {:data data :field "url"})
                :header (header-cell {:title "URL"})
                :width 400}]
       [Column {:cell (text-cell {:data data :field "status"})
                :header (header-cell {:title "Status"})
                :width 100}]])))
