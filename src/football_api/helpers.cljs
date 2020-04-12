(ns football-api.helpers
  (:require [goog.object :as obj]
            ["date-fns" :refer [format parseISO formatISO]]
            ["styled-components" :default styled-components]
            ["framer-motion" :refer [motion]]))

(defn styled [element styles] ((styled-components (if (keyword? element) (name element) element)) (clj->js styles)))

(defn animated [element] (obj/get motion (name element)))

(defn formatTime [date]  (-> date parseISO (format "HH:mm")))
(defn formatTimeFull [date] (-> date parseISO (format "HH:mm:ss")))
(defn formatDate [date]  (-> date parseISO (format "dd.MM.yyyy")))
(defn currentTime [] (-> (js/Date.) .getTime formatISO))