(ns football-api.components.matches-list
  (:require
   [re-frame.core :as rf]
   [cljs-styled-components.reagent :refer [defstyled]]
   [football-api.helpers :refer [animated formatDate]]
   [football-api.components.loading-spinner :refer [loading-spinner]]
   [football-api.components.match :refer [match]]))

(defstyled group-title :h2 {:margin "0 20px"})


(defn matches-list []
  (let [matches @(rf/subscribe [:matches])
        matches-loading @(rf/subscribe [:matches-loading])
        active-matchday @(rf/subscribe [:active-matchday])]
    [:div
     (if matches-loading [loading-spinner])
     (as-> matches m
       (filter #(= (:matchday %) active-matchday) m)
       (group-by #(formatDate (get % :utcDate)) m)
       (seq m)
       (for [[date matches-for-date] m]
         ^{:key date} [:div
                       [group-title date]
                       (for [match-data matches-for-date]
                         ^{:key (:id match-data)} [:> (animated "div")
                                                   [match match-data]])]))]))
