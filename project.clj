(defproject gannet "1.0.0"
  :description "Gannet: Analyse URL on your website"
  :url "http://gannet.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.4.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [ring/ring-json "0.4.0"]
                 [http-kit "2.1.19"]
                 [compojure "1.5.0"]
                 [clojurewerkz/urly "1.0.0"]
                 [enlive "1.1.6"]
                 [commons-validator "1.5.0"]
                 [org.clojure/clojurescript "1.8.40" :scope "provided"]
                 [reagent "0.6.0-alpha"]
                 [cljs-ajax "0.5.4"]]
  :main gannet.core
  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-figwheel "0.5.1"]]
  :cljsbuild {
    :builds {:app {:source-paths ["src-cljs"]
                   :figwheel true
                   :compiler {:output-to "resources/public/js/app.js"
                              :output-dir "resources/public/js/out"
                              :main "gannet.core"
                              :asset-path "js/out"
                              :optimizations :none
                              :source-map true
                              :pretty-print true}}}}
  :clean-targets
  ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]])
