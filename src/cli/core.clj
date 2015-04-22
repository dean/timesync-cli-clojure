(ns cli.core
  (:require [clojure.tools.cli :refer [parse-opts]])
  (:require [clojure.data.json :as json])
  (:require [clj-http.lite.client :as client])
  (:gen-class))

  ;; (some
  ;;   (for [proj [seq get-avail-projects]
  ;;     [#(= (get proj "name") to-find)]])))

(defn get-avail-projects []
  (let [url "http://140.211.168.242/projects"]
    (json/read-str
      (get 
        (client/get url {:accept :json}) :body))))


(defn proj-in-list [to-find]
  (some true?
    (iterate #(= (get % :name) to-find) get-avail-projects)))


(def cli-options
  [["-p" "--project PROJECT" "Project Slug"
    :validate [#(proj-in-list %)]]
    ["-d" "--duration DURATION" "Duration (Minutes)"
      :parse-fn #(Integer/parseInt %)
      :validate [#(> % 0) "Must be a number greater than 0."]]
    ["-u" "--username USERNAME" "Username to log time under."]
    ;; :validate ] This will later validate against the current users in the db.
    ["-a" "--activity ACTIVITY" "Activity time spent working on."
      :id :activity]
    ["-n" "--notes NOTES" "Notes about activity worked on."
      :id :notes]
    ["-i" "--issue ISSUE" "URI for issue being worked on."
      :id :issue]])

;; (defn send_req []
;;  )

(defn -main [& args]
  ;; Comments/Docstrings?
  ;;(println (parse-opts args cli-options)))
  (println (proj-in-list "orvsd")))
