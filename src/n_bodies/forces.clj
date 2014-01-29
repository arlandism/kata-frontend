(ns n-bodies.forces
  (:require [n-bodies.vector :refer [magnitude-of unit-vector scale-vector]]))

(def GRAVITY 6.67384E-11)

(defn- positional-difference [pos-one pos-two]
  (merge-with
    -
    (:position pos-one)
    (:position pos-two)))

(defn force-on [body-one body-two]
  (let [weight (* GRAVITY (:mass body-one) (:mass body-two))
        pos-diff (positional-difference
                     body-two
                     body-one)
        distance (magnitude-of pos-diff)
        distance-squared (Math/pow distance 2)
        unit-vector (unit-vector pos-diff)
        the-force (/ weight distance-squared)] 
    (scale-vector the-force unit-vector)))

(defn sum-forces-on-body [target-body other-bodies]
  (reduce
    (fn [forces-so-far next-body]
      (merge-with
        +
        forces-so-far
        (force-on target-body next-body)))
    {:x 0 :y 0}
    other-bodies))
