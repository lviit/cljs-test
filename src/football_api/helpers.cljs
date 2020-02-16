(ns football-api.helpers
  (:require [goog.object :as obj]
            ["styled-components" :default styled]))

(defn Styled [element styles] ((obj/get styled element) (clj->js styles)))
