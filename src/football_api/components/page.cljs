(ns football-api.components.page
  (:require
   [re-frame.core :as rf]
   [football-api.components.select-competition :refer [select-competition]]
   [cljs-styled-components.reagent :refer [defstyled]]))

(defstyled container :div {:text-align "center"
                              :text-transform "uppercase"})

(defn page [{:keys [title]} & children]
  (let  [competitions @(rf/subscribe [:competitions])
         active-competition @(rf/subscribe [:active-competition])]
    [container
     [:div.header
      [:h1 title]
      [:p (->> competitions
               (filter #(= (:code %) active-competition))
               first
               )]
      [select-competition]]
     [:div.content children]]))
