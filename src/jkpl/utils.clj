(ns jkpl.utils
  (:require [clojure.string :as string]))

(def slash-pattern #"^[/]*([^/]*)[/]*$")

(defn trim-slashes
  [s]
  (second (re-find slash-pattern s)))

(defn remove-file-extension
  [s]
  (let [index (.lastIndexOf s ".")]
    (if (> index 0)
      (.substring s 0 index)
      s)))

(defn uri
  [& args]
  (->> args
       (map str)
       (map trim-slashes)
       (remove string/blank?)
       (string/join "/")
       (str "/")))

