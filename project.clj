(defproject n-bodies-harness "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.0"]]
  :profiles {:dev {:dependencies [[speclj "2.7.4"]
                                  [quil "1.6.0"]]}}
  :main n-bodies-harness.core
  :plugins [[speclj "2.7.4"]]
  :test-paths ["spec"])
