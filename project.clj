(defproject jkpl "0.1.0-SNAPSHOT"
  :description "Homepage for jkpl"
  :url "http://jkpl.lepovirta.org"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [stasis "2.2.2"]
                 [optimus "0.18.3"]
                 [optimus-sass "0.0.3"]
                 [ring "1.4.0"]
                 [org.clojure/data.xml "0.0.8"]
                 [clj-time "0.9.0"]
                 [selmer "0.8.9"]]
  :main ^:skip-aot jkpl.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [[lein-ring "0.9.6"]]
  :ring {:handler jkpl.core/app
         :init jkpl.core/app-init}
  :aliases {"build-site" ["run" "-m" "jkpl.core/export"]})
