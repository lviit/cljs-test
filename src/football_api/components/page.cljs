(ns football-api.components.page
  (:require
   [football-api.helpers :refer [styled]]))

(def container (styled "div" {:text-align "center"
                              :text-transform "uppercase"}))

(defn page [{:keys [title]} & children]
  [:> container
   [:div.header
    [:h1 title]]
   [:div.content children]])
