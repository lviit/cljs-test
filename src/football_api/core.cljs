(ns football-api.core
  (:require [reagent.dom :as r]
            [re-frame.core :as rf]
            [football-api.helpers :refer [styled]]
            [football-api.db]
            [football-api.subs]
            [football-api.events]
            [football-api.components.matches-list :refer [matches-list]]
            [football-api.components.match-details :refer [match-details]]
            [football-api.components.header :refer [header]]
            ["styled-normalize" :rename {Normalize normalize}]))

(def styled-app (styled :div {:font-family "'Noto Sans', sans-serif"
                              :color "#383838"
                              :background-color "#f2efef"}))

(defn app []
  [:> styled-app
   [:> normalize]
   [match-details]
   [header]
   [matches-list]])

(defn ^:dev/after-load start
  []
  (r/render [app]
            (.getElementById js/document "root")))

(defn ^:export init
  []
  (rf/dispatch-sync [:init])
  (start))
