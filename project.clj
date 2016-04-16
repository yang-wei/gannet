(defproject ring-app "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.4.0"]
                 [metosin/ring-http-response "0.6.5"]
                 [clj-http "2.1.0"]
                 [compojure "1.5.0"]
                 [clojurewerkz/urly "1.0.0"]
                 [enlive "1.1.6"]
                 [commons-validator "1.5.0"]
                 [org.clojure/clojurescript "1.8.40" :scope "provided"]]
  :main ring-app.core
  :plugins [[lein-cljsbuild "1.1.3"]]
  :cljsbuild {
    :builds {:app {:source-paths ["src-cljs"]
                   :compiler {:output-to "target/cljs-build/public/js/app.js"   
                              :output-dir "target/cljs-build/public/js/out"
                              :main "gannet.core"
                              :asset-path "/js/out"
                              :optimizations :none
                              :source-map true
                              :pretty-print true}}}}
  :clean-targets
  ^{:protect false}
  [:target-path
   [:cljsbuild :builds :app :compiler :output-dir]
   [:cljsbuild :builds :app :compiler :output-to]])
