(ns football-api.events
  (:require
   [ajax.core :as ajax]
   [day8.re-frame.http-fx]
   [re-frame.core :refer [reg-event-fx reg-event-db]]))

;; read from env variable FOOTBALL_API_AUTH_TOKEN
(goog-define api-auth-token "123456789")

(def api-base-url "https://api.football-data.org/v2/")

(def default-options {:method          :get
                      :headers         {"X-Auth-Token" api-auth-token}
                      :format          (ajax/json-request-format)
                      :response-format (ajax/json-response-format {:keywords? true})
                      :on-failure      [:api-error]})

(reg-event-db
 :api-error
 (fn [db [_ response]]
   (-> db
       (assoc :loading false))))

(reg-event-db
 :get-matches-success
 (fn [db [_ response]]
   (-> db
       (assoc :loading false)
       (assoc :matches ((js->clj response) :matches)))))

(reg-event-fx
 :get-matches
 (fn [{:keys [db]} _]
   {:db  (assoc db :loading true)
    :http-xhrio (merge default-options {:uri        (str api-base-url "competitions/PL/matches")
                                        :on-success [:get-matches-success] })}))

(reg-event-db
 :get-competitions-success
 (fn [db [_ response]]
   (-> db
       (assoc :loading false)
       (assoc :competitions ((js->clj response) :competitions)))))

(reg-event-fx
 :get-competitions
 (fn [{:keys [db]} _]
   {:db  (assoc db :loading true)
    :http-xhrio (merge default-options {:uri        (str api-base-url "competitions")
                                        :on-success [:get-competitions-success]})}))
