(ns football-api.components.pager
  (:require
   [re-frame.core :as rf]
   [cljs-styled-components.reagent :refer [defstyled]]))

(defstyled container :div {:display "flex"
                           :justify-content "space-between"
                           :align-items "center"})

(defn pager []
  (let  [active-matchday @(rf/subscribe [:active-matchday])
         last-matchday (-> @(rf/subscribe [:matches])
                           last
                           (get :matchday))]
    [container
     [:button {:on-click #(rf/dispatch [:set-active-matchday (- active-matchday 1)])
               :disabled (= active-matchday 1)} "prev"]
     [:span (str "gameweek " active-matchday)]
     [:button {:on-click #(rf/dispatch [:set-active-matchday (+ active-matchday 1)])
               :disabled (= active-matchday last-matchday)} "next"]]))
