(ns football-api.components.pager
  (:require
   [re-frame.core :as rf]
   [football-api.components.button :refer [button]]
   [football-api.components.chevron :refer [chevron]]
   [football-api.helpers :refer [styled]]))

(def styled-container (styled :div {:display "flex"
                                    :align-items "center"}))

(def styled-text (styled :div {:align-items "center"
                               :line-height "24px"}))

(defn pager []
  (let  [active-matchday @(rf/subscribe [:active-matchday])
         last-matchday (-> @(rf/subscribe [:matches])
                           last
                           :matchday)]
    [:> styled-container
     [button {:on-click #(rf/dispatch [:set-active-matchday (- active-matchday 1)])
              :disabled (= active-matchday 1)}
      [chevron {:direction :left}]]
     [:> styled-text (str "gameweek " active-matchday)]
     [button {:on-click #(rf/dispatch [:set-active-matchday (+ active-matchday 1)])
              :disabled (= active-matchday last-matchday)}
      [chevron {:direction :right}]]]))
