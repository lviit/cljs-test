(ns football-api.db
  (:require [re-frame.core :as rf]))

(def initial-db {:matches []
                 :matches-loading false
                 :standings []
                 :competitions []
                 :active-competition ""
                 :active-matchday 1})

(rf/reg-event-db
 :init-db
 (fn [_ _]
   initial-db))
