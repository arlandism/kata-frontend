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
  
(defn -main []
  (sketch
  :title "The Universe"
  :setup (partial setup [{:mass 3 :position {:x 100 :y 20 :z 0} :velocity {:x 0 :y 0}}
                         {:mass 10 :position {:x 200 :y 250 :z 0} :velocity {:x 1.2 :y 0}}
                         {:mass 20 :position {:x 400 :y 700 :z 0} :velocity {:x 1.2 :y -0.1}}] 
                        time-step-universe)
  :draw draw
  :size [1000 1000]))

