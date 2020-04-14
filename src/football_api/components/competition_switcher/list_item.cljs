(ns football-api.components.competition-switcher.list-item
  (:require
   [football-api.helpers :refer [styled styled-props styled-theme-color]]))

(def styled-li (styled :li {:padding "7px 10px"
                            :&:first-child {:padding-top "12px"}
                            :&:last-child {:padding-bottom "12px"}
                            :display "flex"
                            :align-items "center"
                            :text-transform "none"
                            :background-color #(if (styled-props % :highlighted)
                                                 (styled-theme-color % :gray3)
                                                 "none")
                            :color #(if (styled-props % :highlighted)
                                      "white"
                                      (styled-theme-color % :gray1))
                            :cursor "pointer"}))

(def styled-flag (styled :img {:width "25px"
                               :min-height "15px"
                               :background-color #(styled-theme-color % :gray3)
                               :filter "grayscale(100%)"
                               :margin-right "10px"}))

(defn list-item [{:keys [highlighted props]
                  {{flag-url :ensignUrl} :area
                   name :name} :item}]
  [:> styled-li (merge props {:highlighted highlighted})
   [:> styled-flag {:src flag-url}]
   name])