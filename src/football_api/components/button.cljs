(ns football-api.components.button
  (:require
   [football-api.components.chevron :refer [chevron]]
   [football-api.helpers :refer [animated styled]]))

(def styled-button (styled (animated :button) {:display "flex"
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

(defn button [{:keys [on-click disabled]} & children]
  [:> styled-button {:while-hover {:scale 1.15}
                     :while-tap {:scale 0.9}
                     :on-click on-click
                     :disabled disabled} children])