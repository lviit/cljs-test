(ns football-api.components.pager
  (:require
   [re-frame.core :as rf]
   [cljs-styled-components.reagent :refer [defstyled]]))

(defstyled container :div {:display "flex"
                           :justify-content "center"
                           :align-items "center"
                           :margin-bottom "-40px"})


(defstyled text :div {:font-weight "600"
                      :margin "0 10px"
                      :align-items "center"
                      :font-size "20px"})

(defstyled button :div {:font-weight "600"
                        :padding "5px 10px"
                        :font-size "14px"
                        :background-color "#eae8e8"
                        :text-transform "uppercase"})

(defn pager []
  (let  [active-matchday @(rf/subscribe [:active-matchday])
         last-matchday (-> @(rf/subscribe [:matches])
                           last
                           (get :matchday))]
    [container
     [button {:on-click #(rf/dispatch [:set-active-matchday (- active-matchday 1)])
              :disabled (= active-matchday 1)} "< prev"]
     [text (str "gameweek " active-matchday)]
     [button {:on-click #(rf/dispatch [:set-active-matchday (+ active-matchday 1)])
              :disabled (= active-matchday last-matchday)} "next >"]]))
