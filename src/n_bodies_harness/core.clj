(ns n-bodies-harness.core
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

(defn colors []
  [red blue purple yellow green])

(defn random-color []
  (let [colors (colors)
        color (nth colors (rand-int (count colors)))]
    color))

(defn initialize-universe! [initial-state time-step] 
  (def universe-timeline 
    (atom 
      (iterate 
        time-step
        initial-state))))

(defn scale-to-size [mass-of-body]
  (* STANDARD_BODY_SIZE mass-of-body))

(defn increment-universe! []
  (swap! universe-timeline next))

(defn draw-body [body]
  (let [position (:position body)
        x (:x position)
        y (:y position)
        height (scale-to-size (:mass body))
        width (scale-to-size (:mass body))
        [r g b] (random-color)]
    (fill r g b)
    (stroke r g b)
    (ellipse x y width height)))

(defn draw-all-bodies [state]
  (dorun 
    (map draw-body state)))

(defn setup []
    (initialize-universe! [{:mass 3 :position {:x 100 :y 20 :z 0}}] time-step-universe)
    (smooth)
    (frame-rate 10)
    (stroke-weight 4)
    (background 200))

(defn draw []
  (draw-all-bodies (first @universe-timeline))
  (increment-universe!))
  
(defn -main []
  (sketch
  :title "The Universe"
  :setup setup
  :draw draw
  :size [1000 1000]))

