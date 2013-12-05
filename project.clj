(defproject n-bodies "0.1.0-SNAPSHOT"
  :description "GUI stuff for N-Bodies Kata"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.0"]]
  :profiles {:dev {:dependencies [[speclj "2.7.4"]
                                  [quil "1.6.0"]]}}
  :main n-bodies.core
  :plugins [[speclj "2.7.4"]]
  :test-paths ["spec"])
