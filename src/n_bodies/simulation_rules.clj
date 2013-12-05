(ns n-bodies.simulation-rules)

(def GRAVITY 6.67384E-11)

(defn sqrt [x]
  (Math/sqrt x))

(defn expt [x pow]
  (Math/pow x pow))

(defn scale-vector [scale vector]
  (into {} (for [[dimension value] vector] [dimension (* scale value)])))

(defn square-hash-values [h]
  (map
    #(* % %)
      (vals h)))

(defn square-hash-differences [pos-one pos-two]
  (square-hash-values
        (merge-with - pos-one pos-two)))

(defn distance-formula [pos-one pos-two]
  (sqrt 
    (reduce 
      +
        (square-hash-differences pos-one pos-two))))

(defn weight [body-one body-two]
  (* 
    (:mass body-one) 
    (:mass body-two) 
    GRAVITY))

(defn calculate-constant [body-one body-two]
  (let [distance (distance-formula 
                   (body-one :position) 
                   (body-two :position))
        distance-cubed (expt distance 3)
        weight-of-bodies (weight body-one body-two)
        constant (/ weight-of-bodies distance-cubed)]
    constant))

(defn force-on-one-body-from-another [body-one body-two]
  (let [distance-as-vector (merge-with - (:position body-one) (:position body-two))]
  (scale-vector 
    (calculate-constant body-one body-two) 
    distance-as-vector)))

  
(defn other-bodies [body-to-exclude all-bodies]
  (filter #(not (= body-to-exclude %)) all-bodies))

(defn sum-of-forces-on-one-body [body-one rest-of-bodies]
  (reduce 
    (fn [forces-so-far current-body]
            (merge-with +
              forces-so-far
              (force-on-one-body-from-another 
                body-one 
                current-body)))
          {:x 0 :y 0 :z 0}
          rest-of-bodies))
  
(defn compute-forces [bodies]
  (map 
    (fn sum-force-partial [body]
      (let [the-rest (other-bodies body bodies)]
        (sum-of-forces-on-one-body 
          body 
          the-rest))
            bodies)))
  
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

(defn step-for-body [body other-bodies]
  (let [forces (sum-of-forces-on-one-body 
                 body 
                 other-bodies) 
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
    #(step-for-body 
       % 
       (other-bodies % universe))
    universe))
