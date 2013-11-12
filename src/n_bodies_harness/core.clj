(ns n-bodies-harness.core
  (:require [quil.core :refer [background ellipse 
                               fill frame-rate 
                               sketch smooth 
                               stroke 
                               stroke-weight]]))

(defn increment-y-value [position]
  (merge-with + {:x 0 :y 1 :z 0} position))

(defn time-step-one-body [body]
  (update-in body [:position]
    increment-y-value))

(defn time-step-universe [universe]
  (map 
    time-step-one-body
    universe))

(def universe 
  (atom 
    (iterate 
      time-step-one-body 
      [{:mass 3 :position {:x 15 :y 0 :z 0}}])))

(def STANDARD_SIZE 10)

(def red [255 51 51])

(def blue [51 153 255])

(def purple [178 102 255])

(def green [0 255 0])

(def yellow [255 255 51])

(defn scale-to-size [mass-of-body]
  (* STANDARD_SIZE mass-of-body))

(defn colors []
  [red blue purple yellow green])

(defn random-color []
  (let [colors (colors)
        color (nth colors (rand-int (count colors)))]
    color))

(defn increment-universe! []
  (swap! universe next))

(defn draw-body [body]
  (let [position (:position body)
        x (:x position)
        y (:y position)
        height (scale-to-size (:mass body))
        width (scale-to-size (:mass body))
        [r g b] (random-color)]
    (fill r g b)
    (stroke-weight 4)
    (stroke r g b)
    (ellipse x y width height)))

(defn draw-all-bodies [state]
  (map draw-body state))

(defn setup []
    (smooth)
    (frame-rate 1)
    (background 200))

(defn draw []
    (draw-all-bodies (first @universe))
    (increment-universe!))
  
(defn -main []
  (sketch
  :title "The Universe"
  :setup setup
  :draw draw
  :size [1000 1000]))

