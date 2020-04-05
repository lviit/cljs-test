(ns football-api.components.loading-spinner
  (:require
   [football-api.helpers :refer [animated styled]]))

(def styled-container (styled (animated :div) {:position "absolute"
                                               :right "0"
                                               :display "flex"
                                               :font-size "16px"
                                               :align-items "center"}))

(def styled-spinner (styled (animated :div) {:border-top "5px solid rgba(0, 0, 0, 0.3)"
                                             :border-bottom "5px solid rgba(0, 0, 0, 0.3)"
                                             :border-left "5px solid rgba(0, 0, 0, 0.3)"
                                             :border-right "5px solid rgba(0, 0, 0, 0.6)"
                                             :border-radius "50%"
                                             :width "10px"
                                             :height "10px"
                                             :margin-left "5px"}))

(defn loading-spinner [] [:> styled-container {:key "loading-indicator"
                                               :initial :hidden
                                               :animate :visible
                                               :exit :hidden
                                               :variants {:hidden {:opacity 0
                                                                   :x 50
                                                                   :transition {:x {:stiffness 1000}}}
                                                          :visible {:opacity 1
                                                                    :x 0
                                                                    :transition {:x {:stiffness 1000}}}}}
                          "loading..."
                          [:> styled-spinner {:animate {:rotate [0, 360]}
                                              :transition {:duration 1
                                                           :loop js/Infinity}}]])
