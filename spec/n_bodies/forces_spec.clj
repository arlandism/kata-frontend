(ns n-bodies.forces-spec
  (:require [speclj.core :refer :all]
            [n-bodies.forces :refer :all]
            [n-bodies.vector :refer [magnitude-of]]))

(describe "force interaction"
  (with body-one {:mass 2 :position {:x 0 :y 0}})
  (with body-two {:mass 3 :position {:x 0 :y 2}})

  (it "has magnitude equal to weight / distance-squared"
    (let [weight (* GRAVITY 2 3)
          distance-squared 4]
      (should=
        (/ weight distance-squared)
        (magnitude-of
          (force-on @body-one @body-two)))))
  
  (it "applies force in the correct direction"
    (let [weight (* GRAVITY 2 3)
          distance-squared 4
          x-component 0.0
          y-component (/ weight distance-squared)]
      (should=
        {:x x-component :y y-component}
        (force-on @body-one @body-two))))
  
  (it "should return forces equal in magnitude, opposite in direction"
    (let [weight (* GRAVITY 2 3)
          distance-squared 4
          x-component 0.0
          y-component (/ weight distance-squared)
          merged-forces (merge-with
                          +
                          (force-on @body-one @body-two)
                          (force-on @body-two @body-one))]
      (should
        (zero?
        (magnitude-of merged-forces)))))
  
  (it "returns the sum of forces acting on a body"
    (let [body-three {:mass 4 :position {:x 0 :y 8}}
          expected-sum (merge-with
                         +
                         (force-on @body-one @body-two)
                         (force-on @body-one body-three))]
      (should=
        expected-sum
        (sum-forces-on-body @body-one @body-two body-three)
        )
      )
    )
  )
