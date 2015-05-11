(defproject jkpl "0.1.0-SNAPSHOT"
  :description "Homepage for jkpl"
  :url "http://jkpl.lepovirta.org"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [stasis "2.2.2"]
                 [optimus "0.17.1"]
                 [ring "1.3.2"]
                 [org.clojure/data.xml "0.0.8"]
                 [clj-time "0.9.0"]
                 [selmer "0.8.2"]]
  :main ^:skip-aot jkpl.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :plugins [[lein-ring "0.9.3"]
            [lein-scss "0.2.2"]]
  :ring {:handler jkpl.core/app
         :init jkpl.core/app-init}
  :aliases {"build-site" ["run" "-m" "jkpl.core/export"]}
  :scss {:builds
         {:default {:source-dir "resources/scss/"
                    :dest-dir "resources/assets/styles/"
                    :executable "sass"
                    :args ["-I" "resources/scss/"]}}})
