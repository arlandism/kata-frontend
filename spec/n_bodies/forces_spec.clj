(ns n-bodies.forces-spec
  (:require [speclj.core :refer :all]
            [n-bodies.forces :refer :all]))


(describe "scale-vector"
  (it "comptues the scale of a vector"
    (should= {:x 2} (scale-vector 2 {:x 1}))))

(describe "unit-vector"
  (it "computes vector with same direction as given, but with magnitude of one"
    (should= {:x 1.0} (unit-vector {:x 2}))
    (should= {:x -1.0} (unit-vector {:x -2}))))

(describe "magnitude-of"
  (it "computes the magnitude of a vector"
    (should= 2.0 (magnitude-of {:x 2}))
    (should= (Math/sqrt 13) (magnitude-of {:x 2 :y 3}))))

(let [body-one {:mass 2 :position {:x 0 :y 0}}
      body-two {:mass 4 :position {:x 0 :y 2}}
      distance-squared 4
      weight (* GRAVITY 2 4)]

  (describe "newtonian-force"
    (it "computes force according to Newton's Laws"
      (should=
       (/ weight distance-squared)
       (newtonian-force body-one body-two))))

  (describe "force-on"
    (it "computes force on first body from second"
      (should=
          {:x 0.0 :y (newtonian-force body-one body-two)}
          (force-on body-one body-two))
      
      (should=
          {:x 0.0 :y (- (newtonian-force body-two body-one))}
          (force-on body-two body-one)))))
