(ns football-api.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [football-api.helpers :refer [Styled]]))
            ;;[cljss.core :refer [defstyles]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload
(defonce matches (r/atom []))

(defonce requestmatches (go (let [response (<! (http/get "https://api.football-data.org/v2/competitions/PL/matches" {:with-credentials? false :headers {"X-Auth-Token" "f6298db6c5cc49879444befb31485cf6"}}))]
  ;(prn (:matches (:body response)))
                              (reset! matches (:matches (:body response))))))

(def Container (Styled "div" {:color "red"
                              :background-color "green"}))

(defn match [match-data]
  (fn []
    [:> Container
     [:div.home (get-in match-data [:homeTeam :name])]
     [:div.score (get-in match-data [:score :fullTime :homeTeam]) " - " (get-in match-data [:score :fullTime :awayTeam])]
     [:div.away (get-in match-data [:awayTeam :name])]
     [:div.date (:utcDate match-data)]]))

(defn matches-list []
  [:div
   (for [match-data @matches]
     ^{:key (:id match-data)} [match match-data])])

(defn ^:dev/after-load start
  []
  (r/render [matches-list]
    (.getElementById js/document "root")))

(defn ^:export init
  []
  (start))
