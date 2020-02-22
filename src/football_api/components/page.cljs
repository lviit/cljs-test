(ns football-api.components.page
  (:require
   [football-api.helpers :refer [styled]]))

(defn page [{:keys [title]} & children]
  [:div.page
   [:div.header 
    [:h2 title]]
   [:div.content children]])
