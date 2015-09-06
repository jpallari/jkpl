(ns jkpl.assets
  (:require [optimus.prime :as optimus]
            [optimus.assets :as assets]
            [optimus.optimizations :as optimizations]
            [optimus.strategies :refer [serve-live-assets]]
            [optimus.export]
            [optimus-sass.core]))

(def asset-dir "assets")

(defn- sass-bundle []
  (assets/load-bundle
    asset-dir
    "main.css"
    [#"/styles/.+\.scss"]))

(def static-asset-files
  ["/favicon.png"
   #"/img/.*"
   #"/files/.*"])

(defn- static-file-bundle []
  (assets/load-assets asset-dir static-asset-files))

(defn- get-assets []
  (concat
    (sass-bundle)
    (static-file-bundle)))

(defn server
  [app]
  (optimus/wrap app get-assets optimizations/none serve-live-assets))

(defn optz [assets options]
  (-> assets
      (optimizations/minify-css-assets options)))

(defn save
  [target-dir]
  (optimus.export/save-assets
    (optz (get-assets) {})
    target-dir))
