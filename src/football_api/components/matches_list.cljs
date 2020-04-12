(ns football-api.components.matches-list
  (:require
   [re-frame.core :as rf]
   [football-api.helpers :refer [animated styled formatDate]]
   [football-api.components.loading-spinner :refer [loading-spinner]]
   [football-api.components.match :refer [match]]
   ["framer-motion" :rename {AnimatePresence animate-presence}]))

(def styled-ul (styled (animated :ul) {:list-style-type "none"
                                       :padding 0}))

(def styled-title (styled :h2 {:margin "20px 20px 0"
                               :font-size "18px"}))

(def styled-container (styled :div {:position "relative"}))

(defn matches-list []
  (let [matches @(rf/subscribe [:matches])
        matches-loading @(rf/subscribe [:matches-loading])
        active-matchday @(rf/subscribe [:active-matchday])]
    [:styled-container
     [:> animate-presence
      (if matches-loading [loading-spinner])]
     (as-> matches m
       (filter #(= (:matchday %) active-matchday) m)
       (group-by #(formatDate (get % :utcDate)) m)
       (seq m)
       (for [[date matches-for-date] m]
         ^{:key date} [:div
                       [:> styled-title date]
                       [:> styled-ul {:initial :hidden
                                      :animate :visible
                                      :variants {:visible {:transition {:stagger-children 0.05}}}}
                        (for [match-data matches-for-date]
                          ^{:key (:id match-data) :i (:id match-data)}
                          [match match-data])]]))]))
