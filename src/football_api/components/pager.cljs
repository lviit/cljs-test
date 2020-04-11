(ns football-api.components.pager
  (:require
   [re-frame.core :as rf]
   [football-api.components.button :refer [button]]
   [football-api.components.chevron :refer [chevron]]
   [football-api.helpers :refer [styled]]))

(def styled-container (styled :div {:display "flex"
                                    :align-items "center"}))

(def styled-text (styled :div {:font-weight "600"
                               :margin "0 10px"
                               :align-items "center"
                               :text-transform "uppercase"
                               :font-size "18px"}))

(defn pager []
  (let  [active-matchday @(rf/subscribe [:active-matchday])
         last-matchday (-> @(rf/subscribe [:matches])
                           last
                           (get :matchday))]
    [:> styled-container
     [button {:on-click #(rf/dispatch [:set-active-matchday (- active-matchday 1)])
              :disabled (= active-matchday 1)}
      [chevron {:direction :left}]
      "prev"]
     [:> styled-text (str "gameweek " active-matchday)]
     [button {:on-click #(rf/dispatch [:set-active-matchday (+ active-matchday 1)])
              :disabled (= active-matchday last-matchday)}
      "next"
      [chevron {:direction :right}]]]))
