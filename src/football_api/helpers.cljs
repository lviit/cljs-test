(ns football-api.helpers
  (:require [goog.object]
            ["styled-components" :default styled]))

(defn Styled [element styles] ((goog.object/get styled element) (clj->js styles)))
