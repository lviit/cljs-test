(ns football-api.components.page
  (:require
   [re-frame.core :as rf]
   [football-api.components.select-competition :refer [select-competition]]
   [cljs-styled-components.reagent :refer [defstyled]]))

(defstyled header :div {:display "flex"
                        :justify-content "space-between"
                        :align-items "center"
                        :text-transform "uppercase"})

(defstyled styled-title :h1 {:font-size "70px"
                             :margin "0"})


(defn page [{:keys [title]} & children]
  (let  [competitions @(rf/subscribe [:competitions])
         active-competition @(rf/subscribe [:active-competition])]
    [:div
     [header
      [styled-title (as-> competitions comp
                      (filter #(= (:code %) active-competition) comp)
                      (first comp)
                      (get comp :name))]
      [select-competition]]
     [:div.content children]]))
