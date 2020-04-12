(ns football-api.events
  (:require
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   [football-api.db :refer [initial-db]]
   [football-api.helpers :refer [currentTime]]
   [re-frame.core :refer [reg-event-fx reg-event-db reg-fx dispatch]]))

;; read from env variable FOOTBALL_API_AUTH_TOKEN
(goog-define api-auth-token "123456789")

(def default-competition "PL")
(def plan "TIER_ONE")
(def api-base-url "https://api.football-data.org/v2/")
(def default-req-options {:method          :get
                          :headers         {"X-Auth-Token" api-auth-token}
                          :format          (ajax/json-request-format)
                          :response-format (ajax/json-response-format {:keywords? true})})

(reg-event-fx
 :init
 (fn [_ _]
   {:db initial-db
    :dispatch [:get-competitions]}))

(reg-event-db
 :get-matches-failure
 (fn [db [_ response]]
   (-> db
       (assoc :matches-loading false))))

(reg-event-db
 :get-matches-success
 (fn [db [_ response]]
   (-> db
       (assoc :matches-loading false)
       (assoc :matches-last-updated (currentTime))
       (assoc :matches ((js->clj response) :matches)))))

(reg-event-fx
 :get-matches
 (fn [{:keys [db]} [_ competition]]
   {:db  (assoc db :matches-loading true)
    :http-xhrio (merge default-req-options {:uri        (str api-base-url "competitions/" competition "/matches")
                                            :on-success [:get-matches-success]
                                            :on-failure [:get-matches-failure]})}))

(reg-event-db
 :get-competitions-failure
 (fn [db [_ response]]
   (-> db
       (assoc :competitions-loading false))))

(reg-event-fx
 :get-competitions-success
 (fn [{:keys [db]} [_ response]]
   {:db (-> db
            (assoc :competitions-loading false)
            (assoc :competitions ((js->clj response) :competitions)))
    :dispatch [:set-active-competition default-competition]}))

(reg-event-fx
 :get-competitions
 (fn [{:keys [db]} _]
   {:db  (assoc db :competitions-loading true)
    :http-xhrio (merge default-req-options {:uri        (str api-base-url "competitions")
                                            :url-params {:plan plan}
                                            :on-success [:get-competitions-success]
                                            :on-failure [:get-competitions-failure]})}))

(reg-event-fx
 :set-active-competition
 (fn [{:keys [db]} [_ active-competition]]
   {:db (assoc db :active-competition active-competition)
    :dispatch-n (list [:get-matches active-competition]
                      [:set-active-matchday (as-> (db :competitions) comp
                                              (filter #(= (:code %) active-competition) comp)
                                              (first comp)
                                              (get-in comp [:currentSeason :currentMatchday]))])
    :poll-matches  {:updater (db :matches-updater)
                    :active-competition active-competition}}))

(reg-fx
 :poll-matches
 (fn [{:keys [updater active-competition]}]
   (js/clearInterval updater)
   (dispatch [:set-matches-updater (js/setInterval #(dispatch [:get-matches active-competition]) 60000)])))

(reg-event-db
 :set-matches-updater
 (fn [db [_ id]]
   (assoc db :matches-updater id)))

(reg-event-db
 :set-active-matchday
 (fn [db [_ matchday]]
   (assoc db :active-matchday matchday)))

(reg-event-db
 :set-active-match-details
 (fn [db [_ id]]
   (assoc db :active-match-details id)))
