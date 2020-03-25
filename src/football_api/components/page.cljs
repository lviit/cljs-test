(ns football-api.components.page
  (:require
   [re-frame.core :as rf]
   [football-api.components.select-competition :refer [select-competition]]
   [football-api.components.pager :refer [pager]]
   [cljs-styled-components.reagent :refer [defstyled]]))

(defstyled header :div {:display "flex"
                        :justify-content "space-between"
                        :align-items "center"
                        :text-transform "uppercase"
                        :margin "20px"})

(defstyled styled-title :h1 {:font-size "70px"
                             :margin "0"})

(defn page [children]
  (let  [competitions @(rf/subscribe [:competitions])
         active-competition @(rf/subscribe [:active-competition-data])]
    [:div
     [header
      [styled-title (get active-competition :name)]
      [select-competition]]
     [pager]
     [:div.content children]]))
