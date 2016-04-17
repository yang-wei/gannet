(ns gannet.core)

(-> (.getElementById js/document "app")
    (.-innerHTML)
    (set! "hello clojurescript"))
