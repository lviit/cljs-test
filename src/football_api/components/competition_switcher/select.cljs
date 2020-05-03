(ns football-api.components.competition-switcher.select
  (:require
   [re-frame.core :as rf]
   [reagent.core :as r]
   [goog.object :as obj]
   ["downshift" :rename {useSelect use-select}]
   ["framer-motion" :rename {AnimatePresence animate-presence}]
   [football-api.components.chevron :refer [chevron]]
   [football-api.components.competition-switcher.list-item :refer [list-item]]
   [football-api.helpers :refer [styled animated styled-props styled-theme-color]]))

(def styled-button (styled :button {:color "inherit"
                                    :background-color #(if (styled-props % :active)
                                                         (styled-theme-color % :gray3)
                                                         (styled-theme-color % :gray1))
                                    :transition "background-color 0.3s ease-in-out"
                                    :text-transform "inherit"
                                    :font-weight "inherit"
                                    :padding "5px 7px"
                                    :border "none"
                                    :cursor "pointer"
                                    :outline "none"
                                    :position "relative"
                                    :z-index "2"
                                    :width "265px"
                                    :display "flex"
                                    :align-items "center"
                                    :justify-content "flex-end"
                                    :svg {:margin-left "5px"}}))

(def styled-ul (styled (animated :ul) {:margin 0
                                       :padding 0
                                       :position "absolute"
                                       :right "20px"
                                       :z-index "1"
                                       :background-color #(styled-theme-color % :gray6)
                                       :list-style-type "none"
                                       :box-shadow "0 0.2rem 2.6rem 0 rgba(12,38,69,0.5)"
                                       :width "265px"
                                       :&:focus {:outline "none"}}))

; make hooks work with reagent: https://clojurians-log.clojureverse.org/reagent/2019-06-14
(defn select-competition []
  (let [competitions @(rf/subscribe [:competitions])
        active-competition @(rf/subscribe [:active-competition])
        {is-open :isOpen
         selected-item :selectedItem
         highlighted-index :highlightedIndex
         get-toggle-button-props :getToggleButtonProps
         get-menu-props :getMenuProps
         get-item-props :getItemProps} (-> {:items competitions
                                            :onSelectedItemChange #(rf/dispatch
                                                                    [:set-active-competition
                                                                     (get-in (js->clj % :keywordize-keys true)
                                                                             [:selectedItem :code])])}
                                           clj->js
                                           use-select
                                           (js->clj :keywordize-keys true))]
    (r/as-element [:div
                   [:> styled-button (merge (js->clj (get-toggle-button-props))
                                            {:active is-open}) (if selected-item
                                                                 (selected-item :name)
                                                                 "select competition")
                    [chevron {:direction :down}]]
                   [:> animate-presence
                    (if is-open
                      [:> styled-ul (merge (js->clj (get-menu-props))
                                           {:initial :hidden
                                            :animate :visible
                                            :exit :hidden
                                            :variants {:hidden {:opacity 0
                                                                :y -300
                                                                :transition {:y {:stiffness 10000}}}
                                                       :visible {:opacity 1
                                                                 :y 0
                                                                 :transition {:y {:stiffness 10000}}}}})
                       (map-indexed (fn [i item]
                                      [list-item {:key i
                                                  :item item
                                                  :highlighted (= highlighted-index i)
                                                  :props (js->clj
                                                          (get-item-props
                                                           (clj->js {:item item
                                                                     :index i})))}]) competitions)])]])))
