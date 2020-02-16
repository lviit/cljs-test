(ns football-api.helpers
  (:require [goog.object :as obj]
            ["framer-motion" :refer [motion]]
            ["styled-components" :default styled-components]))

(defn animated [element] (obj/get motion element))

(defn styled [element styles] ((obj/get styled-components element) (clj->js styles)))
