(ns n-bodies.core
  (:require [quil.core :refer [background ellipse 
                               fill frame-rate 
                               sketch smooth 
                               stroke 
                               stroke-weight]]
            [n-bodies.simulation-rules :refer [time-step-universe]]
            [n-bodies.forces :refer [force-on]]))

(def STANDARD_BODY_SIZE 1)

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
  (frame-rate 100)
  (stroke-weight 4)
  (background 200))

(defn draw []
  (let [universe-snapshot (first @universe-timeline)] 
    (background 200)
    (draw-all-bodies universe-snapshot)
    (increment-universe!)))

(def sun 
  {:mass 150 
   :position {:x -100 :y 350} 
   :velocity {:x 0 :y -6}})

(def moon
  {:mass 0.001 
   :position {:x -50 :y 0} 
   :velocity {:x 0 :y 12}})

(def earth 
  {:mass 120 
   :position {:x 100 :y 100} 
   :velocity {:x 0 :y 5}})

(defn -main []
  (sketch
  :title "The Universe"
  :setup (partial setup 
                  [sun earth] 
                  time-step-universe)
  :draw draw
  :size [1000 1000]))
