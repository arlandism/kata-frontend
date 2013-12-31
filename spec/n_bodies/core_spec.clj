(ns n-bodies.core-spec
  (:require [speclj.core :refer :all]
            [n-bodies.core :refer :all] 
            [n-bodies.simulation-rules :refer :all]))

;(defn vector-contains? [v search-item]
;  (< 
;    0 
;    (count (filter #(= search-item %) v))))
;
;
;(describe "initialize-universe!"
;  (it "stores an infinite lazy seq in an atom"
;    (let [step-fn inc
;          initial-state 1]
;    (initialize-universe! initial-state step-fn)
;    (should= 2 (second @universe-timeline)))))
;
;(describe "random-color"
;  (it "picks a color from a random set"
;    (let [fake-colors [[:foo] [:bar] [:baz]]]
;      (with-redefs [colors (fn [& _] fake-colors)]
;        (should (vector-contains? fake-colors (random-color)))))))
;
;(describe "scale-to-size"
;  (it "scales masses by the standard size"
;    (should= (* STANDARD_BODY_SIZE 3) (scale-to-size 3))))
;
;(describe "add-color"
;  (it "adds a color association to a data structure"
;    (with-redefs [random-color (fn [& _] [12 11 10])]
;      (should= {:color [12 11 10] :foo "bar"} (add-color {:foo "bar"})))))
;
