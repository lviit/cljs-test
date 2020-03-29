(ns football-api.components.matches-list
  (:require
   [re-frame.core :as rf]
   [cljs-styled-components.reagent :refer [defstyled]]
   [football-api.helpers :refer [animated formatDate]]
   [football-api.components.loading-spinner :refer [loading-spinner]]
   [football-api.components.match :refer [match]]))

(defstyled group-title :h2 {:margin "20px 20px 0"
                            :font-size "18px"})

(defn animated-ul [children] [:> (animated :ul) {:initial "closed"
                                                 :animate "open"
                                                 :variants {:open {:transition {:stagger-children 0.05}}}}
                              children])

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
                       [animated-ul
                        (for [match-data matches-for-date]
                          ^{:key (:id match-data) :i (:id match-data)}
                          [match match-data])]]))]))
