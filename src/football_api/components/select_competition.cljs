(ns football-api.components.select-competition
  (:require
   [re-frame.core :as rf]
   [cljs-styled-components.reagent :refer [defstyled]]))

(defstyled select :select {:font-size "16px"})

(defn select-competition []
  (let  [competitions @(rf/subscribe [:competitions])
         active-competition @(rf/subscribe [:active-competition])]
    [:select {:value active-competition :on-change #(rf/dispatch [:set-active-competition (.. % -target -value)])}
     (for [{:keys [id name code]} competitions]
       ^{:key id} [:option {:value code} name])]))
