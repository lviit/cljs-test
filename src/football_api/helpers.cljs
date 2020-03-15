(ns football-api.helpers
  (:require [goog.object :as obj]
            ["date-fns" :refer [format parseISO]]
            ["framer-motion" :refer [motion]]
            ["styled-components" :default styled-components]))

(defn animated [element] (obj/get motion element))

(defn styled [element styles] ((obj/get styled-components element) (clj->js styles)))

(defn formatTime [date]  (-> date parseISO (format "HH:mm")))
(defn formatDate [date]  (-> date parseISO (format "dd.MM.yyyy")))