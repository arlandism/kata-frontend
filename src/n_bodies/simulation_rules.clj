(ns n-bodies.simulation-rules
  (:require [n-bodies.forces :refer [scale-vector sum-forces-on-body other-elems]]))
  
(defn momentum [time-step forces]
  (scale-vector time-step forces))

(defn velocity [momentum mass]
  (scale-vector (/ 1 mass) momentum))

(defn delta-v [forces time-step body]
  (let [momentum (momentum time-step forces)
        velocity (velocity momentum (:mass body))]
  (merge-with +
    velocity
    (:velocity body))))

(defn new-position [body velocity]
  (let [current-position (:position body)]
    (merge-with + current-position velocity)))

(defn step-for-body [body other-elems]
  (let [forces (sum-forces-on-body 
                 body 
                 other-elems) 
        time-step 1000000000
        new-velocity (delta-v forces time-step body)
        new-position (new-position body new-velocity)
        displaced-body (assoc 
                   (assoc
                     body :velocity new-velocity)
                    :position new-position)]
    displaced-body))

(defn time-step-universe [universe]
  (map
    #(step-for-body % (other-elems % universe))
    universe))
