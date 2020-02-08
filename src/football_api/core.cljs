(ns football-api.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))
            ;;[cljss.core :refer [defstyles]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload
(defonce matches (r/atom []))

(defonce requestmatches (go (let [response (<! (http/get "https://api.football-data.org/v2/competitions/PL/matches" {:with-credentials? false :headers {"X-Auth-Token" ""}}))]
  ;(prn (:matches (:body response)))
                             (reset! matches (:matches (:body response))))))

(defn match [match-data]
  (fn []
    [:div.match
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