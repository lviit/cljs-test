(ns football-api.components.loading-spinner
  (:require
   [football-api.helpers :refer [animated styled]]))

(def styled-spinner (styled (animated :div) {:margin "50px 0"
                                             :font-size "10px"
                                             :position "absolute"
                                             :top "calc(50% - 5rem)"
                                             :left "calc(50% - 5rem)"
                                             :text-indent "-9999"
                                             :border-top "2.5em solid rgba(0, 0, 0, 0.1)"
                                             :border-bottom "2.5em solid rgba(0, 0, 0, 0.1)"
                                             :border-left "2.5em solid rgba(0, 0, 0, 0.1)"
                                             :border-right "2.5em solid rgba(0, 0, 0, 0.2)"
                                             :border-radius "50%"
                                             :width "5rem"
                                             :height "5rem"}))

(defn loading-spinner [] [:> styled-spinner {:animate {:rotate [0, 360]}
                                             :transition {:duration 1
                                                          :loop js/Infinity}}])

