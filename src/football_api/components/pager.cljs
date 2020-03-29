(ns football-api.components.pager
  (:require
   [re-frame.core :as rf]
   [football-api.helpers :refer [styled]]))

(def styled-container (styled :div {:display "flex"
                                    :justify-content "center"
                                    :align-items "center"
                                    :margin-bottom "-40px"}))

(def styled-text (styled :div {:font-weight "600"
                               :margin "0 10px"
                               :align-items "center"
                               :font-size "20px"}))

(def styled-button (styled :button {:font-weight "600"
                                    :padding "5px 10px"
                                    :font-size "14px"
                                    :background-color "#eae8e8"
                                    :text-transform "uppercase"
                                    :border "none"
                                    :cursor "pointer"}))

(defn pager []
  (let  [active-matchday @(rf/subscribe [:active-matchday])
         last-matchday (-> @(rf/subscribe [:matches])
                           last
                           (get :matchday))]
    [:> styled-container
     [:> styled-button {:on-click #(rf/dispatch [:set-active-matchday (- active-matchday 1)])
                        :disabled (= active-matchday 1)} "< prev"]
     [:> styled-text (str "gameweek " active-matchday)]
     [:> styled-button {:on-click #(rf/dispatch [:set-active-matchday (+ active-matchday 1)])
                        :disabled (= active-matchday last-matchday)} "next >"]]))
