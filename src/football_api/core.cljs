(ns football-api.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [cljs-styled-components.reagent :refer-macros [defglobalstyle]]
            [football-api.db]
            [football-api.subs]
            [football-api.events]
            [football-api.components.match :refer [match]]
            [football-api.components.page :refer [page]]
            [football-api.components.loading-spinner :refer [loading-spinner]]
            [football-api.helpers :refer [animated formatDate]]))

(defglobalstyle
  global-styles
  {"body" {:font-family "'Noto Sans', sans-serif"}})

(defn matches-list []
  (let [matches @(rf/subscribe [:matches])
        matches-loading @(rf/subscribe [:matches-loading])
        active-matchday @(rf/subscribe [:active-matchday])]
    [:div
     [global-styles]
     (if matches-loading [loading-spinner])
     [page {:title "Matches"}
      (as-> matches m
        (filter #(= (:matchday %) active-matchday) m)
        (group-by #(formatDate (get % :utcDate)) m)
        (seq m)
        (for [[date matches-for-date] m]
          ^{:key date} [:div
                        [:h2 date]
                        (for [match-data matches-for-date]
                          ^{:key (:id match-data)} [:> (animated "div")
                                                    [match match-data]])]))]]))

(defn ^:dev/after-load start
  []
  (r/render [matches-list]
            (.getElementById js/document "root")))

(defn ^:export init
  []
  (rf/dispatch-sync [:init-db])
  (rf/dispatch [:get-competitions])
  (rf/dispatch [:set-active-competition "PL"])
  (start))
