(ns football-api.components.chevron
  (:require
   [football-api.helpers :refer [styled]]))

(def styled-chevron (styled :svg {:transform #(case ((js->clj %) "direction")
                                                "right" "none"
                                                "left" "rotate(180deg)"
                                                "down" "rotate(90deg)"
                                                "none")}))

(defn chevron [{:keys [direction]}]  [:> styled-chevron  {:direction direction
                                                          :xmlns "http://www.w3.org/2000/svg"
                                                          :height "24"
                                                          :fill "white"
                                                          :view-box "0 0 24 24"
                                                          :width "24"}
                                      [:path {:d "M10 6L8.59 7.41 13.17 12l-4.58 4.59L10 18l6-6z"}]
                                      [:path {:d "M0 0h24v24H0z"
                                              :fill "none"}]])
