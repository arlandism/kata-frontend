(ns n-bodies.forces)

(def GRAVITY 6.67384E-11)

(defn scale-vector [scale v]
  (into
    {}
    (for [dimension (keys v)]
      [dimension (* scale (dimension v))])))

(defn other-elems [the-elem all-elems]
  (filter #(not (= the-elem %)) all-elems))

(defn magnitude-of [v]
  (Math/sqrt
    (reduce 
      +
      (map 
        #(* % %)
        (vals v)))))

(defn unit-vector [v]
  (let [mag (magnitude-of v)]
    (scale-vector (/ 1 mag) v)))

(defn- pos-diff [body-one body-two] 
  (merge-with
    -
    (:position body-one)
    (:position body-two)))

(defn newtonian-force [body-one body-two]
  (let [weight (* GRAVITY (:mass body-one) (:mass body-two))
        distance (magnitude-of (pos-diff body-one body-two))]
    (/ weight (Math/pow distance 2))))

(defn force-on [body-one body-two]
  (let [the-force (newtonian-force body-one body-two)
        unit-vector (unit-vector 
                      (pos-diff body-two body-one))] 
    (scale-vector the-force unit-vector)))

(defn sum-forces-on-body [body other-bodies]
  (reduce
    (fn [forces-so-far next-body]
      (merge-with
        +
        forces-so-far
        (force-on body next-body)))
    {:x 0 :y 0}
    other-bodies))

(defn compute-all-forces [bodies]
  (map
    (fn [body]
      (sum-forces-on-body 
        body 
        (other-elems body bodies)))
    bodies))
