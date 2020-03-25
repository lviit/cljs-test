(ns football-api.core
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [cljs-styled-components.reagent :refer-macros [defglobalstyle]]
            [football-api.db]
            [football-api.subs]
            [football-api.events]
            [football-api.components.matches-list :refer [matches-list]]
            [football-api.components.page :refer [page]]))

(defglobalstyle
  global-styles
  {"body" {:font-family "'Noto Sans', sans-serif"}})

(defn app []
  [:div
   [global-styles]
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
