(ns n-bodies.forces
  (:require [n-bodies.vector :refer [scale-vector magnitude-of unit-vector]]))

(def GRAVITY 6.67384E-11)

(defn- positional-difference [pos-one pos-two]
  (merge-with
    -
    (:position pos-one)
    (:position pos-two)))

(defn- newtonian-force [body-one body-two]
  (let [weight (* GRAVITY (:mass body-one) (:mass body-two))
        distance (magnitude-of
                   (positional-difference
                     body-two
                     body-one))
        distance-squared (Math/pow distance 2)]
    (/ weight distance-squared)))

(defn force-on [body-one body-two]
  (let [unit-vector (unit-vector 
                      (positional-difference
                        body-two
                        body-one))]
    (scale-vector (newtonian-force body-one body-two) unit-vector)))

(defn sum-forces-on-body [target-body other-bodies]
  (reduce
    (fn [forces next-body]
      (merge-with
        +
        forces
        (force-on target-body next-body)))
    {:x 0 :y 0}
    other-bodies))
