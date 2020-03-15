(ns football-api.components.page
  (:require
   [football-api.components.select-competition :refer [select-competition]]
   [football-api.helpers :refer [styled]]))

(def container (styled "div" {:text-align "center"
                              :text-transform "uppercase"}))

(defn page [{:keys [title]} & children]
  [:> container
   [:div.header
    [:h1 title]
    [select-competition]]
   [:div.content children]])
