(ns football-api.components.header
  (:require
   [re-frame.core :as rf]
   [football-api.components.competition-switcher.select :refer [select-competition]]
   [football-api.components.pager :refer [pager]]
   [football-api.helpers :refer [styled styled-theme-color]]))

(def styled-top-bar (styled :div {:display "flex"
                                  :justify-content "space-between"
                                  :align-items "center"
                                  :padding "0 20px"
                                  :background-color #(styled-theme-color % :gray1)
                                  :position "relative"
                                  :z-index "2"}))

(def styled-container (styled :div {:color "white"
                                    :text-transform "uppercase"
                                    :font-weight "600"
                                    :margin-bottom "20px"}))

(def styled-header (styled :div {:display "flex"
                                 :background #(styled-theme-color % :gray2)
                                 :justify-content "space-between"
                                 :align-items "flex-end"
                                 :padding "30px 20px 20px"}))

(def styled-title (styled :h1 {:font-size "50px"
                               :margin "0 0 0 -2px"
                               :text-transform "none"}))

(defn header []
  (let [{active-comp-name :name} @(rf/subscribe [:active-competition-data])
        site-name "Cljs football live scores"]
    [:> styled-container
     [:> styled-top-bar
      site-name
      [:> select-competition]]
     [:> styled-header
      [:> styled-title active-comp-name]
      [pager]]]))
