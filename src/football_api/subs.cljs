(ns football-api.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :competitions
 (fn [db _]
   (db :competitions)))

(reg-sub
 :active-competition
 (fn [db _]
   (db :active-competition)))
