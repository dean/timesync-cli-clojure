(ns cli.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojure.data.json :as json])
  (:require [clj-http.lite.client :as client])
  (:gen-class))

(defn get-avail-projects []
  (let [url "timetracking.io/projects"]
    (get (get (client/get url {:accept :json}) "data") "projects")))

(def cli-options
  [["-p" "--project PROJECT" "Project Slug"]
    ;; :validate #(contains? get-avail-projects %)]
    ["-d" "--duration DURATION" "Duration (Minutes)"
    :parse-fn #(Integer/parseInt %)
    :validate [#(> % 0) "Must be a number greater than 0."]]
    ["-u" "--username USERNAME" "Username to log time under."]
    ;; :validate ] This will later validate against the current users in the db.
    ["-a" nil "Activity time spent working on."]
    ["-n" nil "Notes about activity worked on."]
    ["-i" nil "URI for issue being worked on."]])

(defn -main [& args]
  ;; Comments/Docstrings?
  (parse-opts args cli-options)
  (println "Hello!"))
