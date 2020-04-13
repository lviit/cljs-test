(ns football-api.components.button
  (:require
   [football-api.components.chevron :refer [chevron]]
   [football-api.helpers :refer [animated styled]]))

(def styled-button (styled (animated :button) {:display "flex"
                                               :align-items "center"
                                               :font-weight "600"
                                               :padding "0"
                                               :margin "0"
                                               :font-size "14px"
                                               :line-height "24px"
                                               :background "none"
                                               :border "none"
                                               :cursor "pointer"
                                               :color "white"
                                               :outline "none"}))

(defn button [{:keys [on-click disabled]} & children]
  [:> styled-button {:while-hover {:scale 1.15}
                     :while-tap {:scale 0.8}
                     :on-click on-click
                     :disabled disabled} children])