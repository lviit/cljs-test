(ns football-api.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [football-api.db]
            [football-api.subs]
            [football-api.events]
            [football-api.components.match :refer [match]]
            [football-api.components.page :refer [page]]
            [football-api.helpers :refer [styled animated]]))

;; define your app data so that it doesn't get over-written on reload
(defonce matches (r/atom []))

(defonce standings (r/atom []))

(def app (styled "div" {:font-family "'Noto Sans', sans-serif"}))

(defn matches-list []
  [:> app
   [page {:title "Matches"}
    (for [match-data @matches]
      ^{:key (:id match-data)} [:> (animated "div")
                                [match match-data]])]])

(defn standings-list []
  [:> app
   [page {:title "Standings"}
    (for [standings-data @standings]
      ^{:key (:id standings-data)} [:> (animated "div")
                                [[:div "pling!"] standings-data]])]])

(defn ^:dev/after-load start
  []
  (r/render [matches-list]
            (.getElementById js/document "root")))

(defn ^:export init
  []
  (rf/dispatch-sync [:init-db])
  (rf/dispatch [:get-competitions])
  (rf/dispatch [:get-matches])
  (start))
