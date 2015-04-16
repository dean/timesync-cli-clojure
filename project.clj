(defproject cli "0.1.0-SNAPSHOT"
  :description "Time Tracking CLI"
  :main cli.core
  :url "http://github.com/dean/timetracking-cli"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                  [org.clojure/tools.cli "0.3.1"]
                  [org.clojure/data.json "0.2.6"]
                  [clj-http-lite "0.2.1"]
                  [environ "1.0.0"]]
  :plugins [[lein-environ "1.0.0"]]
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.7.0"]]}})
