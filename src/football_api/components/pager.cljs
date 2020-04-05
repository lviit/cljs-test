(ns football-api.components.pager
  (:require
   [re-frame.core :as rf]
   [football-api.components.chevron :refer [chevron]]
   [football-api.helpers :refer [styled]]))

(def styled-container (styled :div {:display "flex"
                                    :align-items "center"}))

(def styled-text (styled :div {:font-weight "600"
                               :margin "0 10px"
                               :align-items "center"
                               :text-transform "uppercase"
                               :font-size "20px"}))

(def styled-button (styled :button {:display "flex"
                                    :align-items "center"
                                    :font-weight "600"
                                    :padding "3px 10px"
                                    :font-size "14px"
                                    :line-height "24px"
                                    :background-color "#efefef"
                                    :text-transform "uppercase"
                                    :border "none"
                                    :cursor "pointer"
                                    :color "#383838"}))

(defn pager []
  (let  [active-matchday @(rf/subscribe [:active-matchday])
         last-matchday (-> @(rf/subscribe [:matches])
                           last
                           (get :matchday))]
    [:> styled-container
     [:> styled-button {:on-click #(rf/dispatch [:set-active-matchday (- active-matchday 1)])
                        :disabled (= active-matchday 1)}
      [chevron {:direction :left}]
      "prev"]
     [:> styled-text (str "gameweek " active-matchday)]
     [:> styled-button {:on-click #(rf/dispatch [:set-active-matchday (+ active-matchday 1)])
                        :disabled (= active-matchday last-matchday)}
      "next"
      [chevron {:direction :right}]]]))
