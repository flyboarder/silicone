(ns silicone.core
  (:require [clojure.string :as string]
            [hiccup.core :as hiccup]))

(def defaultjs [:script {:type "text/javascript" :src "index.html.js"}])

(def iron-elements [:iron-elements
                    [:iron-icons :av-icons]
                    [:iron-icons :communication-icons]
                    [:iron-icons :device-icons]
                    [:iron-icons :editor-icons]
                    [:iron-icons :hardware-icons]
                    [:iron-icons :image-icons]
                    [:iron-icons :maps-icons]
                    [:iron-icons :notification-icons]
                    [:iron-icons :social-icons]])

(def paper-elements [:paper-elements
                     [:paper-styles :paper-styles-classes]])

(def neon-elements [[:neon-animation :neon-animation]])

(defn import-polymer [path]
  [:link {:rel "import" :href (clojure.string/join [path "/polymer/polymer.html"])}])

(defn import-element [path element]
  (let [to-str #(-> % name str)
        epath  (cond (keyword? element) (string/join "/" [(to-str element) (to-str element)])
                     (vector?  element) (string/join "/" (mapv to-str element)))
        epath  (string/join [epath ".html"])
        fullpath (string/join "/" [path epath])]
    [:link {:rel "import" :href fullpath}]))

(defn import-elements [path elements]
  (concat (map #(import-element path %) (into [] elements))))

(defn import-iron [path]
  (import-elements path iron-elements))

(defn import-paper [path]
  (import-elements path paper-elements))

(defn import-neon [path]
  (import-elements path neon-elements))

(defn polymer-elements [elements & [path]]
  (let [path (or path "bower_components")
        head (import-elements path elements)]
    (hiccup/html
     [:head head])))
