(ns football-api.components.loading-spinner
  (:require
   [football-api.helpers :refer [animated styled formatTimeFull]]
   ["framer-motion" :rename {AnimatePresence animate-presence}]))

(def styled-container (styled (animated :div) {:position "absolute"
                                               :right "20px"
                                               :display "flex"
                                               :font-size "16px"
                                               :font-weight "600"
                                               :align-items "center"}))

(def styled-spinner (styled (animated :div) {:border-top "5px solid rgba(0, 0, 0, 0.3)"
                                             :border-bottom "5px solid rgba(0, 0, 0, 0.3)"
                                             :border-left "5px solid rgba(0, 0, 0, 0.3)"
                                             :border-right "5px solid rgba(0, 0, 0, 0.6)"
                                             :border-radius "50%"
                                             :width "10px"
                                             :height "10px"
                                             :margin-left "5px"}))

(def motion-props {:initial :hidden
                   :animate :visible
                   :exit :hidden
                   :variants {:hidden {:opacity 0
                                       :x 50
                                       :transition {:x {:stiffness 1000}}}
                              :visible {:opacity 1
                                        :x 0
                                        :transition {:delay 0.5
                                                     :x {:stiffness 1000}}}}})

(defn loading-spinner [{:keys [last-updated loading]}]
  [:> animate-presence
   (if loading
     [:> styled-container (merge motion-props {:key "loading-indicator"})
      "updating..."
      [:> styled-spinner {:animate {:rotate [0, 360]}
                          :transition {:duration 1
                                       :loop js/Infinity}}]]
     (if last-updated
       [:> styled-container (merge motion-props {:key "last-updated"})
        (str "Last updated: " (formatTimeFull last-updated))]))])
