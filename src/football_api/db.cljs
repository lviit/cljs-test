(ns football-api.db)

(def initial-db {:matches []
                 :matches-last-updated nil
                 :matches-updater 0
                 :matches-loading false
                 :standings []
                 :competitions []
                 :active-competition ""
                 :active-matchday 1
                 :active-match-details nil})
