(ns templater.views
  (:require
    [re-frame.core :as re-frame]
    [templater.subs :as subs]
    [templater.events :as events]
    ))

(defn extract-template-option [{title :title}]
  (into [] [:option {:value title} title]))

(defn template-selector []
  (let [templates (re-frame/subscribe [::subs/templates])
        options (map extract-template-option @templates)]
    [:select {:on-change #(re-frame/dispatch [::events/change-template (-> % .-target .-value)])} options]))

(defn template-parameters-components []
  (let [template-parameters (re-frame/subscribe [::subs/template-parameters])]
    [:div template-parameters]))

(defn results-box []
  (let [results (re-frame/subscribe [::subs/results])]
    [:div @results]))

(defn main-panel []
  (let [raw-templates (re-frame/subscribe [::subs/raw-templates])]
    [:div
     [:h1 "Templater"]
     [:div#raw-templates-area
      [:label "Raw Templates"
       [:textarea#raw-templates
        {:on-change #(re-frame/dispatch [::events/edit-raw-templates (-> % .-target .-value)])}
        @raw-templates]]]
     [:div#select-template-area
      [:label "Select a template"
       (template-selector)]]
     [:div#parameters-area
      [:div "Parameters"]
      (template-parameters-components)]
     [:div#results-area
      [:div "Results" (results-box)]]]))
