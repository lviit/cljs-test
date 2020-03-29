(ns football-api.components.select-competition
  (:require
   [re-frame.core :as rf]
   [football-api.helpers :refer [styled]]))

(def styled-select (styled :select {:font-size "16px"
                                    :background-color "#eee"
                                    :appearance "none"
                                    :padding "5px 10px"
                                    :border "none"
                                    :border-radius 0}))

(defn select-competition []
  (let  [competitions @(rf/subscribe [:competitions])
         active-competition @(rf/subscribe [:active-competition])]
    [:> styled-select {:value active-competition :on-change #(rf/dispatch [:set-active-competition (.. % -target -value)])}
     (for [{:keys [id name code]} competitions]
       ^{:key id} [:option {:value code} name])]))
