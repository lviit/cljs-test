(ns football-api.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :matches
 (fn [db _]
   (db :matches)))

(reg-sub
 :matches-loading
 (fn [db _]
   (db :matches-loading)))

(reg-sub
 :competitions
 (fn [db _]
   (db :competitions)))

(reg-sub
 :active-competition
 (fn [db _]
   (db :active-competition)))
