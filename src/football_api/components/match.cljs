(ns football-api.components.match
  (:require
   ["date-fns" :refer [format parseISO]]
   [football-api.helpers :refer [animated styled formatTime formatDate]]))

(def styled-container (styled (animated :li) {:font-size "20px"
                                              :font-weight "700"
                                              :display "flex"
                                              :align-items "center"
                                              :padding "0 20px"
                                              :margin "10px 0"
                                              :background-color "#f7f7f7"}))

(def styled-score (styled :div {:background-color "#eae8e8"
                                :padding "10px 15px"
                                :margin "0 10px"}))

(def styled-match (styled :div {:display "flex"
                                :align-items "center"
                                :flex-grow "1"}))

(def styled-home-team (styled :div {:flex "0 1 calc(50% - 85px)"
                                    :text-align "right"}))

(def styled-time (styled :div {:font-size "16px"
                               :font-weight 400}))

(defn match [{:keys [homeTeam awayTeam utcDate score]}]
  [:> styled-container {:variants {:open {:y 0
                                          :opacity 1
                                          :transition {:y {:stiffness 1000
                                                           :velocity -100}}}
                                   :closed {:y 50
                                            :opacity 0
                                            :transition {:y {:stiffness 1000}}}}}

   [:> styled-time (formatTime utcDate)]
   [:> styled-match
    [:> styled-home-team (homeTeam :name)]
    [:> styled-score (get-in score [:fullTime :homeTeam])] " - " [:> styled-score (get-in score [:fullTime :awayTeam])]
    [:div (awayTeam :name)]]])
