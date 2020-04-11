(ns football-api.components.match
  (:require
   [re-frame.core :as rf]
   ["date-fns" :refer [format parseISO]]
   [football-api.helpers :refer [animated styled formatTime formatDate]]))

(def styled-container (styled (animated :li) {:font-size "18px"
                                              :font-weight "700"
                                              :display "flex"
                                              :align-items "center"
                                              :padding "0 20px"
                                              :margin "10px 0"
                                              :background-color "#efefef"
                                              :cursor "pointer"}))

(def styled-score (styled :div {:background-color "#e5e5e5"
                                :padding "10px 15px"
                                :margin "0 10px"}))

(def styled-match (styled :div {:display "flex"
                                :align-items "center"
                                :flex-grow "1"}))

(def styled-home-team (styled :div {:flex "0 1 calc(50% - 85px)"
                                    :text-align "right"}))

(def styled-time (styled :div {:font-size "16px"
                               :font-weight 400}))

(defn match [{:keys [homeTeam awayTeam utcDate id]
              {{home-team-score :homeTeam
                away-team-score :awayTeam} :fullTime} :score}]
  (let [active-match-details @(rf/subscribe [:active-match-details])
        is-open (= id active-match-details)]
    [:> styled-container {:on-click #(rf/dispatch [:set-active-match-details id])
                          :variants {:visible {:y 0
                                               :opacity 1
                                               :transition {:y {:stiffness 1000
                                                                :velocity -100}}}
                                     :hidden {:y 50
                                              :opacity 0
                                              :transition {:y {:stiffness 1000}}}}}
     [:> styled-time (formatTime utcDate)]
     [:> styled-match
      [:> styled-home-team (homeTeam :name)]
      [:> styled-score (or home-team-score "-")] " - " [:> styled-score (or away-team-score "-")]
      [:div (awayTeam :name)]]]))



