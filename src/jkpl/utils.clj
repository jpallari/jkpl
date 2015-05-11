(ns jkpl.utils
  (:require [clojure.string :as string]))

(def slash-pattern #"^[/]*([^/]*)[/]*$")

(defn- trim-slashes
  [s]
  (second (re-find slash-pattern s)))

(defn uri
  [& args]
  (->> args
       (map str)
       (map trim-slashes)
       (remove string/blank?)
       (string/join "/")
       (str "/")))

