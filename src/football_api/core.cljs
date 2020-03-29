(ns football-api.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [football-api.helpers :refer [styled]]
            [football-api.db]
            [football-api.subs]
            [football-api.events]
            [football-api.components.matches-list :refer [matches-list]]
            [football-api.components.page :refer [page]]))

(def styled-app (styled :div {:font-family "'Noto Sans', sans-serif"
                              :color "#383838"}))

(defn app []
  [:> styled-app
   [page
    [matches-list]]])

(defn ^:dev/after-load start
  []
  (r/render [app]
            (.getElementById js/document "root")))

(defn ^:export init
  []
  (rf/dispatch-sync [:init-db])
  (rf/dispatch [:get-competitions])
  (rf/dispatch [:set-active-competition "PL"])
  (start))
