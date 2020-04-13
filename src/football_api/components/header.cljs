(ns football-api.components.header
  (:require
   [re-frame.core :as rf]
   [football-api.components.select-competition :refer [select-competition]]
   [football-api.components.pager :refer [pager]]
   [football-api.helpers :refer [styled]]))

(def styled-top-bar (styled :div {:display "flex"
                                  :justify-content "space-between"
                                  :align-items "center"
                                  :padding "0 20px"
                                  :margin-bottom "10px"
                                  :background-color "#333333"
                                  :position "relative"
                                  :z-index "2"}))

(def styled-container (styled :div {:color "white"
                                    :text-transform "uppercase"
                                    :font-weight "600"
                                    :margin-bottom "20px"
                                    :background "#444"}))

(def styled-header (styled :div {:display "flex"
                                           :justify-content "space-between"
                                           :align-items "flex-end"
                                           :padding "20px"}))

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
