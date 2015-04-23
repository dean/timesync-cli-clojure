(ns cli.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojure.data.json :as json])
  (:require [clj-http.lite.client :as client])
  (:gen-class))


(def get-projects
  (let [url "http://140.211.168.242/projects"]
    (json/read-str
      (get 
        (client/get url {:accept :json}) :body))))


(def get-users
  (let [url "http://140.211.168.242/users"]
    (json/read-str
      (get 
        (client/get url {:accept :json}) :body))))


(defn add-entry [args]
  (let [url "http://140.211.168.242/activities/add"]
    (client/post url {:query-params args} )))


(defn proj-in-list [project]
  (some true?
      (map #(= (get % "name") project) get-projects)))


(defn user-in-list[user]
  (some true?
      (map #(= (get % "username") user) get-users)))



(def cli-options
  [["-p" "--project PROJECT" "Project Slug"
    :validate [#(proj-in-list %)]]
    ["-d" "--duration DURATION" "Duration (Minutes)"
      :parse-fn #(Integer/parseInt %)
      :validate [#(> % 0) "Must be a number greater than 0."]]
    ["-u" "--username USERNAME" "Username to log time under."
    :validate [#(user-in-list %)]]
    ["-a" "--activity ACTIVITY" "Activity time spent working on."
      :id :activity]
    ["-n" "--notes NOTES" "Notes about activity worked on."
      :id :notes]
    ["-i" "--issue ISSUE" "URI for issue being worked on."
      :id :issue]])


(defn -main [& args]
  ;; Comments/Docstrings?
  (let [cli-args (parse-opts args cli-options)]
    (println
      (add-entry (json/write-str (get cli-args :options))))))
