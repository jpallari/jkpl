(ns jkpl.rss
  (:require [clojure.data.xml :as xml]))

(defn- post
  [conf post]
  [:entry
   [:title (:title post)]
   [:updated (:date post)]
   [:author [:name (:author conf)]]
   [:link {:href (str (:site-url conf) (:uri post))}]
   [:id (str (:site-urn conf) ":post:" (:id post))]
   [:content {:type "html"} (:content post)]])

(defn- feed
  [conf]
  (let [posts (:all-posts conf)
        atom-url (str (:site-url conf) (:atom-uri conf))]
    [:feed {:xmlns "http://www.w3.org/2005/Atom"}
      [:id (:site-urn conf)]
      [:updated (-> posts first :date)]
      [:title {:type "text"} (:site-title conf)]
      [:link {:rel "self" :href atom-url}]
      (map (partial post conf) posts)]))

(defn atom-xml
  [conf]
  (->> (feed conf)
       xml/sexp-as-element
       xml/emit-str))
