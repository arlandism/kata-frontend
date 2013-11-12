(ns n-bodies-harness.simulation-rules)

(defn increment-y-value [position]
  (merge-with + {:x 0 :y 5 :z 0} position))

(defn time-step-one-body [body]
  (update-in body [:position]
    increment-y-value))

(defn time-step-universe [universe]
  (map 
    time-step-one-body
    universe))
