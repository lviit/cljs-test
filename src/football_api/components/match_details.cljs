(ns football-api.components.match-details
  (:require
   [re-frame.core :as rf]
   ["date-fns" :refer [format parseISO]]
   ["framer-motion" :rename {AnimatePresence animate-presence}]
   [football-api.helpers :refer [animated styled formatTime formatDate]]))

(def styled-overlay (styled :div {:z-index "3"
                                  :position "fixed"
                                  :top 0
                                  :left 0
                                  :right 0
                                  :bottom 0
                                  :background-color "rgba(72, 72, 72, 0.5)"
                                  :backdrop-filter "blur(10px)"}))

(def styled-container (styled (animated :div) {:font-size "20px"
                                               :font-weight "700"
                                               :padding "0 20px"
                                               :background-color "#fff"
                                               :width "500px"
                                               :height "500px"
                                               :position "absolute"
                                               :top "calc(50% - 250px)"
                                               :left "calc(50% - 250px)"}))

(def styled-score (styled :div {:background-color "#eae8e8"
                                :padding "10px 15px"
                                :margin "0 10px"}))

(def styled-time (styled :div {:font-size "16px"
                               :font-weight 400}))

(defn match-details []
  (let [{:keys [homeTeam awayTeam utcDate id]
         {{home-team-score :homeTeam
           away-team-score :awayTeam} :fullTime} :score} @(rf/subscribe [:active-match-data])
        match-details-open (boolean id)]
    [:> animate-presence
     (if match-details-open [:> styled-overlay {:on-click #(rf/dispatch [:set-active-match-details nil])}
                             [:> styled-container {:key "match-details"
                                                   :initial {:opacity 0 :y -100 :transition {:x {:stiffness 1000}}}
                                                   :animate {:opacity 1 :y 0 :transition {:x {:stiffness 1000}}}
                                                   :exit {:opacity 0 :y 100 :transition {:x {:stiffness 1000}}}}
                              [:> styled-time (formatTime utcDate)]
                              [:div
                               [:div (homeTeam :name)]
                               [:> styled-score (or home-team-score "-")] " - " [:> styled-score (or away-team-score "-")]
                               [:div (awayTeam :name)]]]])]))
