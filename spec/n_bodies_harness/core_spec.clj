(ns n-bodies-harness.core-spec
  (:require [speclj.core :refer :all]
            [n-bodies-harness.core :refer :all] 
            [n-bodies-harness.simulation-rules :refer :all]))

(defn vector-contains? [v search-item]
  (< 
    0 
    (count (filter #(= search-item %) v))))

(describe "time-step-universe"
  (it "increments the y value of the position"
    (let [state-of-world [{:mass 2 :position {:x 0 :y 3 :z 0}}]]
    (should= {:mass 2 :position {:x 0 :y 8 :z 0}} (first (time-step-universe state-of-world))))))

(describe "initialize-universe!"
  (it "stores an infinite lazy seq in an atom"
    (let [step-fn inc
          initial-state 1]
    (initialize-universe! initial-state step-fn)
    (should= 2 (second @universe-timeline)))))

(describe "random-color"
  (it "picks a color from a random set"
    (let [fake-colors [[:foo] [:bar] [:baz]]]
      (with-redefs [colors (fn [& _] fake-colors)]
        (should (vector-contains? fake-colors (random-color)))))))

(describe "scale-to-size"
  (it "scales masses by the standard size"
    (should= (* STANDARD_BODY_SIZE 3) (scale-to-size 3))))

(describe "add-color"
  (it "adds a color association to a data structure"
    (with-redefs [random-color (fn [& _] [12 11 10])]
      (should= {:color [12 11 10] :foo "bar"} (add-color {:foo "bar"})))))

(describe "draw-all-bodies"
  (xit "applies the draw-body fn to all bodies in the state"
    (let [state [
                 {:mass 3 :position {:x 0 :y 0 :z 0}} 
                 {:mass 4 :position {:x 1 :y 1 :z 1}}
                ]]
      (with-redefs [draw-body (fn [body] (assoc body :greeting :foo))]
        (should= :foo (:greeting (second (draw-all-bodies state))))))))
