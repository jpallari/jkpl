(ns jkpl.core
  (:require [jkpl.parse :as parse]
            [jkpl.render :as render]
            [jkpl.assets :as assets]
            [jkpl.rss :as rss]
            [jkpl.page :as page]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [clojure.edn :as edn]
            [stasis.core :as stasis]))

(def default-export-target-dir "target/website/")

(defn- load-config []
  (edn/read-string (slurp "resources/config.edn")))

(defn- raw-post-source []
  (stasis/slurp-directory "resources/posts/" #"\.html$"))

(defn- raw-page-source []
  (stasis/slurp-directory "resources/pages/" #"\.html$"))

(defn- render-kv
  [renderer site m]
  [(:uri m) (fn [_] (renderer site m))])

(defn- render-key
  [site renderer k]
  (->> (get site k)
       (map (partial render-kv renderer site))
       (into {})))

(defn- pages
  [site]
  (render-key site render/page :all-pages))

(defn- posts
  [site]
  (render-key site render/post :all-posts))

(defn- tag-pair
  [site tag]
  [(page/tag-uri tag) (fn [_] (render/tag site tag))])

(defn- tags
  [site]
  (->> (:tags site)
       (map (partial tag-pair site))
       (into {})))

(defn- archive
  [site]
  {(:archive-uri site) (fn [_] (render/archives site))})

(defn- rss
  [site]
  {(:atom-uri site) (fn [_] (rss/atom-xml site))})

(defn site []
  (let [s (parse/site (load-config) (raw-page-source) (raw-post-source))]
    (stasis/merge-page-sources
      {:pages (pages s)
       :posts (posts s)
       :tags (tags s)
       :archive (archive s)
       :rss (rss s)})))

(defn app-init []
  (selmer.parser/cache-off!))

(def app
  (-> (stasis/serve-pages site)
       assets/server
       wrap-content-type))

(defn export
  ([directory]
    (stasis/empty-directory! directory)
    (assets/save directory)
    (stasis/export-pages (site) directory))
  ([] (export default-export-target-dir)))

