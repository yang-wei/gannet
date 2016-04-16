(ns gannet.pages
  (:require [net.cgrand.enlive-html :as html]))

(html/deftemplate home "template/home.html" []
  [:title] (html/content "Gannet - Analyze your Links"))
