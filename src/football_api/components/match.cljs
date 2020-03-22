(ns football-api.components.match
  (:require
   ["date-fns" :refer [format parseISO]]
   [cljs-styled-components.reagent :refer [defstyled]]
   [football-api.helpers :refer [formatTime formatDate]]))

(defstyled container :div {:font-size "20px"
                           :font-weight "700"
                           :letter-spacing "1px"
                           :display "flex"
                           :padding "20px"
                           :margin "30px 0"
                           :box-shadow "0 7px 20px 0 rgba(12,38,69,0.2)"})

(defstyled styled-score :div {:margin "0 5px"
                              :font-weight "600"})

(defn match [{:keys [homeTeam awayTeam utcDate score]}]
  [container
   [:div (homeTeam :name)]
   [styled-score (str (get-in score [:fullTime :homeTeam]) " - " (get-in score [:fullTime :awayTeam]))]
   [:div (awayTeam :name)]
   [:div (str (formatDate utcDate) " - " (formatTime utcDate))]])
