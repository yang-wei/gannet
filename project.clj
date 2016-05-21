(defproject gannet "1.0.0"
  :description "Gannet: Analyse URL on your website"
  :url "http://gannet.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.2.374"]
                 [http-kit "2.1.19"]
                 [clojurewerkz/urly "1.0.0"]
                 [enlive "1.1.6"]
                 [commons-validator "1.5.0"]
                 ;; web/api stack
                 [compojure "1.5.0"]
                 [ring "1.4.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [ring/ring-json "0.4.0"]
                 ;; config
                 [environ "1.0.3"]
                 ;; test
                 [http-kit.fake "0.2.1"]
                 ;; database
                 [org.clojure/java.jdbc "0.6.1"]
                 [mysql/mysql-connector-java "5.1.39"]
                 [migratus "0.8.21"]
                 ;; frontend
                 [org.clojure/clojurescript "1.8.40" :scope "provided"]
                 [reagent "0.5.1"]
                 [cljsjs/fixed-data-table "0.6.0-1"]
                 [cljs-ajax "0.5.4"]]
  :main gannet.core
  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-figwheel "0.5.1"]
            [migratus-lein "0.3.3"]
            [lein-environ "1.0.3"]]
  :cljsbuild {
    :builds {:app {:source-paths ["front"]
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
   [:cljsbuild :builds :app :compiler :output-to]]
  ;; setup are done in profiles.clj
  :migratus {:store :database
             :migration-dir "migrations"}
  )
