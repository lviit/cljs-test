(ns football-api.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :matches
 (fn [db _]
   (get-in db [:matches (db :active-competition)])))

(reg-sub
 :matches-loading
 (fn [db _]
   (db :matches-loading)))

(reg-sub
 :matches-last-updated
 (fn [db _]
   (db :matches-last-updated)))

(reg-sub
 :competitions
 (fn [db _]
   (db :competitions)))

(reg-sub
 :active-competition
 (fn [db _]
   (db :active-competition)))

(reg-sub
 :active-competition-data
 (fn [db _]
   (->> (db :competitions)
        (filter #(= (:code %) (db :active-competition)))
        first)))

(reg-sub
 :active-matchday
 (fn [db _]
   (db :active-matchday)))

(reg-sub
 :active-match-details
 (fn [db _]
   (db :active-match-details)))

(reg-sub
 :active-match-data
 (fn [db _]
   (->> (get-in db [:matches (db :active-competition)])
        (filter #(= (:id %) (db :active-match-details)))
        first)))
