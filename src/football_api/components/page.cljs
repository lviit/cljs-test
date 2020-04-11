(ns football-api.components.page
  (:require
   [re-frame.core :as rf]
   [football-api.components.select-competition :refer [select-competition]]
   [football-api.components.match-details :refer [match-details]]
   [football-api.helpers :refer [styled]]
   ["framer-motion" :rename {AnimatePresence animate-presence}]))

(def styled-header (styled :div {:display "flex"
                                 :justify-content "space-between"
                                 :align-items "center"
                                 :margin "20px"}))

(def styled-container (styled :div {:filter #(if (get (js->clj %) "blurred") "blur(10px)" "none")}))

(def styled-title (styled :h1 {:font-size "60px"
                               :margin "0"}))


(def styled-text (styled :p {:margin "0"
                             :font-weight "600"
                             :font-size "14px"
                             :text-transform "uppercase"}))


(defn page [children]
  (let  [competitions @(rf/subscribe [:competitions])
         active-competition @(rf/subscribe [:active-competition-data])
         active-match-data @(rf/subscribe [:active-match-data])
         match-details-open (boolean active-match-data)]
    [:div
     [:> animate-presence
      (if match-details-open [match-details active-match-data])]
     [:> styled-container {:blurred match-details-open}
      [:> styled-header
       [:div
        [:> styled-text "Cljs football api"]
        [:> styled-title (get active-competition :name)]]
       [select-competition]]
      [:div children]]]))
