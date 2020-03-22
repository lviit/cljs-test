(ns football-api.helpers
  (:require [goog.object :as obj]
            ["date-fns" :refer [format parseISO]]
            ["framer-motion" :refer [motion]]))

(defn animated [element] (obj/get motion element))

(defn formatTime [date]  (-> date parseISO (format "HH:mm")))
(defn formatDate [date]  (-> date parseISO (format "dd.MM.yyyy")))