(ns n-bodies.core
  (:require [quil.core :refer [background ellipse 
                               fill frame-rate 
                               sketch smooth 
                               stroke 
                               stroke-weight]]
            [n-bodies-harness.simulation-rules :refer [time-step-universe]]))

(def STANDARD_BODY_SIZE 10)

(def red [255 51 51])

(def blue [51 153 255])

(def purple [178 102 255])

(def green [0 255 0])

(def yellow [255 255 51])

(def pink [255 20 147])

(def grey [119 136 153])

(def crimson [220 20 60])

(def orange [255 102 0])

(def orange-yellow [255 204 0])

(defn colors []
  [red blue purple yellow green orange crimson grey pink orange-yellow])

(defn random-color []
  (let [colors (colors)
        color (nth colors (rand-int (count colors)))]
    color))

(defn add-color [h-map]
  (assoc h-map :color (random-color)))

(defn initialize-universe! [initial-state step-fn] 
  (def universe-timeline 
    (atom 
      (iterate 
        step-fn
        initial-state))))

(defn scale-to-size [to-scale]
  (* STANDARD_BODY_SIZE to-scale))

(defn increment-universe! []
  (swap! universe-timeline next))

(defn draw-body [body]
  (let [position (:position body)
        x (:x position)
        y (:y position)
        height (scale-to-size (:mass body))
        width (scale-to-size (:mass body))
        [r g b] (:color body)]
    (fill r g b)
    (stroke r g b)
    (ellipse x y width height)))

(defn draw-all-bodies [state]
  (dorun 
    (map draw-body state)))

(defn setup [initial-state step-fn]
  (initialize-universe! (map add-color initial-state) step-fn)
  (smooth)
  (frame-rate 10)
  (stroke-weight 4)
  (background 200))

(defn draw []
  (let [universe-snapshot (first @universe-timeline)] 
    (background 200)
    (draw-all-bodies universe-snapshot)
    (increment-universe!)))

(defn random-negatives [bound]
  ( -
    (rand bound)))

(defn random-body-num []
  (let [body-num (rand-int 4)]
  (if (< 2 body-num)
     body-num
     (random-body-num)))) 

(defn random-body-size []
  (let [size (rand 20)]
    (if (< 5 size)
      size
      (random-body-size))))

(defn random-body []
  {:mass (random-body-size)
   :position {:x (rand 500) :y (rand 600)}
   :velocity {:x (rand-nth [(rand 2) (random-negatives 2)])  :y (rand-nth [(rand 2) (random-negatives 2)])}
  })

(defn random-bodies []
  (let [num-bodies 25]
    (take num-bodies (repeatedly random-body))))
  
(defn -main []
  (sketch
  :title "The Universe"
  :setup (partial setup (random-bodies) 
                        time-step-universe)
  :draw draw
  :size [1000 1000]))

