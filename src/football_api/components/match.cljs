(ns football-api.components.match
  (:require
   ["date-fns" :refer [format parseISO]]
   [cljs-styled-components.reagent :refer [defstyled]]
   [football-api.helpers :refer [formatTime formatDate]]))

(defstyled container :div {:font-size "20px"
                           :font-weight "700"
                           :display "flex"
                           :align-items "center"
                           :padding "0 20px"
                           :margin "10px 0"
                           :box-shadow "0 0 10px 0 rgba(12,38,69,0.1)"})

(defstyled styled-score :div {:background-color "#f2f2f2"
                              :padding "10px 15px"
                              :margin "0 10px"})

(defstyled styled-match :div {:display "flex"
                              :align-items "center"
                              :flex-grow 1})

(defstyled home-team :div {:flex "0 1 43%"
                           :text-align "right"})

(defstyled styled-time :div {:font-size "16px"})

(defn match [{:keys [homeTeam awayTeam utcDate score]}]
  [container
   [styled-time (formatTime utcDate)]
   [styled-match
    [home-team (homeTeam :name)]
    [styled-score (get-in score [:fullTime :homeTeam])] " - " [styled-score (get-in score [:fullTime :awayTeam])]
    [:div (awayTeam :name)]]])
