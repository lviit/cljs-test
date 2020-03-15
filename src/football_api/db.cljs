(ns football-api.db
  (:require [re-frame.core :as rf]))

(def initial-db {:matches []
                 :standings []
                 :competitions []
                 :loading false})

(rf/reg-event-db
 :init-db
 (fn [_ _]
   initial-db))