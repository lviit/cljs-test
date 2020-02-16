(ns football-api.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            ["framer-motion" :refer [AnimatePresence]]
            [football-api.helpers :refer [Styled]]))
            ;;[cljss.core :refer [defstyles]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload
(defonce matches (r/atom []))

(defonce requestmatches (go (let [response (<! (http/get "https://api.football-data.org/v2/competitions/PL/matches" {:with-credentials? false :headers {"X-Auth-Token" "f6298db6c5cc49879444befb31485cf6"}}))]
  ;(prn (:matches (:body response)))
                              (reset! matches (:matches (:body response))))))

(def Container (Styled "div" {:font-family "'Rubik', sans-serif"
                              :font-size "20px"
                              :display "flex"
                              :padding "10px"
                              :margin "30px"
                              :box-shadow "0 7px 20px 0 rgba(12,38,69,0.2)"}))

(def Score (Styled "div" {:margin "0 5px"
                          :font-weight "600"}))

(defn match [{:keys [homeTeam awayTeam utcDate score]}]
  (fn []
    [:> Container
     [:div.home (homeTeam :name)]
     [:> Score (get-in score [:fullTime :homeTeam]) " - " (get-in score [:fullTime :awayTeam])]
     [:div.away (awayTeam :name)]
     [:div.date utcDate]]))

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
