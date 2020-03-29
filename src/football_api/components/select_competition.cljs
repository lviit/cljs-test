(ns football-api.components.select-competition
  (:require
   [re-frame.core :as rf]
   [football-api.helpers :refer [styled]]))

(def styled-select (styled :select {:font-size "20px"
                                    :background-color "#eae8e8"
                                    :appearance "none"
                                    :padding "5px 10px"
                                    :border "none"
                                    :border-radius 0
                                    :font-family "'Noto Sans', sans-serif"
                                    :font-weight "600"
                                    :color "#383838"
                                    :cursor "pointer"}))

(defn select-competition []
  (let  [competitions @(rf/subscribe [:competitions])
         active-competition @(rf/subscribe [:active-competition])]
    [:> styled-select {:value active-competition :on-change #(rf/dispatch [:set-active-competition (.. % -target -value)])}
     (for [{:keys [id name code]} competitions]
       ^{:key id} [:option {:value code} name])]))
