(ns jkpl.assets
  (:require [optimus.prime :as optimus]
            [optimus.assets :as assets]
            [optimus.optimizations :as optimizations]
            [optimus.strategies :refer [serve-live-assets]]
            [optimus.export]))

(def asset-sources
  ["/styles/main.css"
   "/favicon.png"
   #"/img/.*"
   #"/files/.*"])

(defn- get-assets []
  (assets/load-assets "assets" asset-sources))

(defn server
  [app]
  (optimus/wrap app get-assets optimizations/none serve-live-assets))

(defn save
  [target-dir]
  (optimus.export/save-assets
    (optimizations/none (get-assets) {})
    target-dir))
