(ns football-api.events
  (:require
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   [re-frame.core :refer [reg-event-fx reg-event-db]]))

;; read from env variable FOOTBALL_API_AUTH_TOKEN
(goog-define api-auth-token "123456789")

(def api-base-url "https://api.football-data.org/v2/")

(def default-options {:method          :get
                      :headers         {"X-Auth-Token" "f6298db6c5cc49879444befb31485cf6"}
                      :format          (ajax/json-request-format)
                      :response-format (ajax/json-response-format {:keywords? true})})

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
       (assoc :matches ((js->clj response) :matches)))))

(reg-event-fx
 :get-matches
 (fn [{:keys [db]} [_ competition]]
   {:db  (assoc db :matches-loading true)
    :http-xhrio (merge default-options {:uri        (str api-base-url "competitions/" competition "/matches")
                                        :on-success [:get-matches-success]
                                        :on-failure [:get-matches-failure]})}))

(reg-event-db
 :get-competitions-failure
 (fn [db [_ response]]
   (-> db
       (assoc :competitions-loading false))))


(reg-event-db
 :get-competitions-success
 (fn [db [_ response]]
   (-> db
       (assoc :competitions-loading false)
       (assoc :competitions ((js->clj response) :competitions)))))

(reg-event-fx
 :get-competitions
 (fn [{:keys [db]} _]
   {:db  (assoc db :competitions-loading true)
    :http-xhrio (merge default-options {:uri        (str api-base-url "competitions")
                                        :url-params {:plan "TIER_ONE"}
                                        :on-success [:get-competitions-success]
                                        :on-failure [:get-competitions-failure]})}))

(reg-event-fx
 :set-active-competition
 (fn [{:keys [db]} [_ active-competition]]
   {:db (assoc db :active-competition active-competition)
    :dispatch [:get-matches active-competition]}))

