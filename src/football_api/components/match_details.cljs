(ns football-api.components.match-details
  (:require
   [re-frame.core :as rf]
   ["date-fns" :refer [format parseISO]]
   [football-api.helpers :refer [animated styled formatTime formatDate]]))

(def styled-overlay (styled :div {:z-index "1"
                                  :position "fixed"
                                  :top 0
                                  :left 0
                                  :right 0
                                  :bottom 0
                                  :background-color "rgba(12, 38, 69, 0.38)"}))

(def styled-container (styled (animated :div) {:font-size "20px"
                                               :font-weight "700"
                                               :padding "0 20px"
                                               :background-color "#fff"
                                               :width "300px"
                                               :height "300px"
                                               :position "absolute"
                                               :top "50%"
                                               :left "50%"
                                               :transform "translate(-50%, -50%)"}))

(def styled-score (styled :div {:background-color "#eae8e8"
                                :padding "10px 15px"
                                :margin "0 10px"}))

(def styled-time (styled :div {:font-size "16px"
                               :font-weight 400}))

(defn match-details [{:keys [homeTeam awayTeam utcDate id]
                      {{home-team-score :homeTeam
                        away-team-score :awayTeam} :fullTime} :score}]
  [:> styled-overlay {:on-click #(rf/dispatch [:set-active-match-details nil])}
   [:> styled-container {:key "match-details"
                         :initial {:opacity 0}
                         :animate {:opacity 1}
                         :exit {:opacity 0}}
    [:> styled-time (formatTime utcDate)]
    [:div
     [:div (homeTeam :name)]
     [:> styled-score (or home-team-score "-")] " - " [:> styled-score (or away-team-score "-")]
     [:div (awayTeam :name)]]]])
