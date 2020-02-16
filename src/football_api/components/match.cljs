(ns football-api.components.match
  (:require
   ["date-fns" :refer [format parseISO]]
   [football-api.helpers :refer [styled]]))

(def container (styled "div" {:font-size "20px"
                              :letter-spacing "1px"
                              :display "flex"
                              :padding "10px"
                              :margin "30px"
                              :box-shadow "0 7px 20px 0 rgba(12,38,69,0.2)"}))

(def Score (styled "div" {:margin "0 5px"
                          :font-weight "600"}))

(defn match [{:keys [homeTeam awayTeam utcDate score]}]
  (fn []
    [:> container
     [:div (homeTeam :name)]
     [:> Score (get-in score [:fullTime :homeTeam]) " - " (get-in score [:fullTime :awayTeam])]
     [:div (awayTeam :name)]
     [:div (-> utcDate parseISO (format "HH:mm"))]
     [:div (-> utcDate parseISO (format "dd.MM.yyyy"))]]))
