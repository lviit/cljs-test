(ns football-api.components.select-competition
  (:require
   [reagent.core :as r]
   [re-frame.core :as rf]
   [football-api.helpers :refer [styled]]))

(def select (styled "select" {:font-size "16px"}))

(defn select-competition []
  (let  [competitions @(rf/subscribe [:competitions])]
    [:> select
     (for [{:keys [id name code]} competitions]
       ^{:key id} [:option {:value code :selected "selected"} name])]))
