(ns jkpl.render
  (:require [selmer.parser :as selmer]
            [selmer.filters :refer [add-filter!]]
            [jkpl.page :as page]
            [clj-time.core :as t]
            [clojure.java.io :as io]))

(add-filter! :tag-uri page/tag-uri)

(defn- template-path
  [template-name]
  (.getPath (io/file "templates" (str template-name ".html"))))

(defn- render
  [template-name conf]
  (selmer/render-file (template-path template-name) conf))

(defn page
  [conf page]
  (render (or (:template page) "page")
          (assoc conf :page page)))

(defn post
  [conf post]
  (render "post" (assoc conf :post post)))

(defn- filter-by-tag
  [posts tag]
  (filter (partial page/has-tag? tag) posts))

(defn tag
  [conf tag]
  (render "tag"
          (assoc conf
                 :name tag
                 :posts (filter-by-tag (:all-posts conf) tag))))

(defn- post-year [post] (t/year (:date post)))

(defn- to-group
  [[group posts]]
  {:group group :posts posts})

(defn- reverse-compare [a b] (compare b a))

(defn- group-posts
  [posts]
  (->> posts
       (group-by post-year)
       (sort-by first reverse-compare)
       (map to-group)))

(defn archives
  [conf]
  (render "archives" (assoc conf :groups (group-posts (:all-posts conf)))))

