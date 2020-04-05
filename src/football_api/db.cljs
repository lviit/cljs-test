(ns football-api.db
  (:require [re-frame.core :refer [reg-event-fx]]))

(def initial-db {:matches []
                 :matches-updater 0
                 :matches-loading false
                 :standings []
                 :competitions []
                 :active-competition ""
                 :active-matchday 1
                 :active-match-details nil})
