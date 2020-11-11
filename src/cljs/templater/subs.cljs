(ns templater.subs
  (:require
    [re-frame.core :as re-frame]))

(re-frame/reg-sub
  ::raw-templates
  (fn [db]
    (:raw-templates db)))

(defn build-template [raw-template]
  (let [template-lines (clojure.string/split-lines raw-template)
        title (take 1 template-lines)
        body (drop 1 template-lines)]
    {:title title
     :body  body}))

(defn split-and-clean-raw-templates [raw-templates]
  (filter
    (comp not clojure.string/blank?)
    (clojure.string/split raw-templates #"(^|\n)# ")))

(defn templates [raw-templates]
  (let [clean-raw-templates (split-and-clean-raw-templates raw-templates)]
    (into [] (map build-template clean-raw-templates))))

(defn template-parameters [raw-templates chosen-template]
  (let [templates (templates raw-templates)
        chosen-3 (take 1 templates)
        chosen-2 (filter #(= (:title %) chosen-template) templates)]
    chosen-3))

(re-frame/reg-sub
  ::templates
  (fn [db]
    (templates (:raw-templates db))))

(re-frame/reg-sub
  ::template-parameters
  (fn [db]
    (template-parameters (:raw-templates db) (:chosen-template db))))

(re-frame/reg-sub
  ::results
  (fn [db]
    (clojure.string/join (take 10 (:raw-templates db)))))
