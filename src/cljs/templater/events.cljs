(ns templater.events
  (:require
    [re-frame.core :as re-frame]
    [templater.db :as db]
    [day8.re-frame.tracing :refer-macros [fn-traced]]
    ))

(re-frame/reg-event-db
  ::initialize-db
  (fn-traced [_ _]
             db/default-db))

(re-frame/reg-event-db
  ::edit-raw-templates
  (fn-traced [db [_ new-raw-templates]]
             (assoc db :raw-templates new-raw-templates)))

(re-frame/reg-event-db
  ::change-template
  (fn-traced [db [_ chosen-template]]
             (assoc db :chosen-template chosen-template)))
