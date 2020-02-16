(ns football-api.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [football-api.components.match :refer [match]]
            [football-api.helpers :refer [styled animated]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload
(defonce matches (r/atom []))

(defonce requestmatches (go (let [response (<! (http/get "https://api.football-data.org/v2/competitions/PL/matches" {:with-credentials? false :headers {"X-Auth-Token" "f6298db6c5cc49879444befb31485cf6"}}))]
  ;(prn (:matches (:body response)))
                              (reset! matches (:matches (:body response))))))

(def app (styled "div" {:font-family "'Rubik', sans-serif"}))

(defn matches-list []
  [:> app
   (for [match-data @matches]
     ^{:key (:id match-data)} [:> (animated "div")
                               [match match-data]])])

(defn ^:dev/after-load start
  []
  (r/render [matches-list]
    (.getElementById js/document "root")))

(defn ^:export init
  []
  (start))
