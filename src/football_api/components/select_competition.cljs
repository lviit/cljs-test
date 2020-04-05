(ns football-api.components.select-competition
  (:require
   [re-frame.core :as rf]
   [football-api.helpers :refer [styled]]))

(def styled-select (styled :select {:font-size "20px"
                                    :background "#efefef url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGhlaWdodD0iMjQiIHZpZXdCb3g9IjAgMCAyNCAyNCIgd2lkdGg9IjI0Ij48cGF0aCBkPSJNMTYuNTkgOC41OUwxMiAxMy4xNyA3LjQxIDguNTkgNiAxMGw2IDYgNi02eiIvPjxwYXRoIGQ9Ik0wIDBoMjR2MjRIMHoiIGZpbGw9Im5vbmUiLz48L3N2Zz4=) no-repeat right 5px center"
                                    :appearance "none"
                                    :padding "5px 30px 5px 10px"
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
