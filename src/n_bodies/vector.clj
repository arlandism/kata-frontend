(ns n-bodies.vector)

(defn scale-vector [scale v]
  (into {} 
    (for [[dimension value] v]
      [dimension (* scale value)])))

(defn magnitude-of [v]
  (Math/sqrt
    (reduce
      +
      (map 
       (fn square [x] (* x x))
        (vals v)))))

(defn unit-vector [v]
  (scale-vector (/ 1 (magnitude-of v)) v))
