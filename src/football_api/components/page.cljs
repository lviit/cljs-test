(ns football-api.components.page
  (:require
   [re-frame.core :as rf]
   [football-api.components.select-competition :refer [select-competition]]
   [football-api.components.pager :refer [pager]]
   [football-api.helpers :refer [styled]]))

(def styled-header (styled :div {:display "flex"
                                 :justify-content "space-between"
                                 :align-items "center"
                                 :text-transform "uppercase"
                                 :margin "20px"}))

(def styled-title (styled :h1 {:font-size "70px"
                               :margin "0"}))


(defn page [children]
  (let  [competitions @(rf/subscribe [:competitions])
         active-competition @(rf/subscribe [:active-competition-data])]
    [:div
     [:> styled-header
      [:> styled-title (get active-competition :name)]
      [select-competition]]
     [pager]
     [:div.content children]]))
