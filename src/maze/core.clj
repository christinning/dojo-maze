(ns maze.core
  (require [clojure.string :refer [split]]))

(def sample
  (str "#I###\n"
       "#...O\n"
       "#..##\n"
       "#####\n"))

(declare to-maze)
(def sample-maze (to-maze sample))

(defn path
  [maze]
  [:S :E :E :E])

(defn mv
  [[dx dy]]
  (fn [[x y]] [(+ dx x) (+ dy y)]))

(def moves
  {[0 -1] :N
   [1 0] :E
   [0 1] :S
   [-1 0] :W })


(defn apply-move
  [coord move]
  (vec (map + coord move)))



(defn neighbours
  [coord]
  (map (partial apply-move coord)
       (keys moves)))




(map + [1 1] [2 2])

(def target \O)
(defn valid-location
  [maze coord]
  (contains? #{\. \I \O} (get-in maze coord)))

(defn walk
  [maze path]
  (let [loc (last path)
        next-maze (assoc-in maze loc \#)]
    ;;(println path loc )
    ;;(println (get-in maze loc))
    (if (= (get-in maze loc) target)
      path
      (map
       (fn [next] (walk next-maze (conj path next)))
       (filter (partial valid-location maze)
              (neighbours loc))))))




(defn to-maze
  "Constructs a maze data structure from a \\\n deliminated String.
  Returns a vector of columns to allow easy (get-in maze [x y]) access"
  [^String s]
  (vec ( apply
         map vector
         (split s #"\n"))))



(defn start-location
  [maze]
  (loop
    [x 0]
    (let
      [y (.indexOf (nth maze x) \I)]
      (if (not= y  -1)
        [x y]
        (recur (inc x))))))
(start-location sample-maze)

(walk sample-maze [(start-location sample-maze)])


