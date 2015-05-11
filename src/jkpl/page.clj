(ns jkpl.page
  (:require [jkpl.utils :as utils]))

(defn tag-uri
  [tag]
  (utils/uri "tag" (str tag ".html")))

(defn has-tag?
  [tag post]
  (some #{tag} (:tags post)))

