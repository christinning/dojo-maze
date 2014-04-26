(ns maze.core
  (require [clojure.string :refer [split]]))

(def exit-square \O)
(def start-square \I)
(def non-wall \.)
(def wall \#)
(def visitable-square? #{start-square non-wall exit-square})

(def moves
  {[0 -1] :N
   [1  0] :E
   [0  1] :S
   [-1 0] :W})

(defn to-maze
  "Constructs a maze data structure from a \\\n deliminated String.
  Returns a vector of columns to allow easy (get-in maze [x y]) access"
  [s]
  (vec ( apply
         map vector
         (split s #"\n"))))

(defn start-location
  [maze]
  (loop
    [x 0]
    (let
      [y (.indexOf (nth maze x) start-square)]
      (if (not= y  -1)
        [x y]
        (recur (inc x))))))

(defn path-to-directions
  "Converts a path of locations into directions. [[1 1] [1 2] [2 2]] becomes [:S :E]"
  [path]
  (map moves
       (map
        #(vec (apply map - (reverse %)) )
        (partition 2 1 path))))

(defn neighbours
  [loc]
  (map #(vec (map + loc %))
       (keys moves)))

(defn extend-path
  "Returns all the paths possible as the next step for the path provided.
  Only goes to valid squares that have not already been visited by path"
  [maze path]
  (let [valid-location? (fn [loc] (visitable-square? (get-in maze loc)))
        not-visited? (fn [loc]
                         (not-any? #(= % loc) path))]
    (map #(conj path %)
         (filter (every-pred valid-location? not-visited?)
                               (neighbours (last path))))))

(defn walk
  [maze path]
  (let [loc (last path)]
    (if (= (get-in maze loc) exit-square)
      [path]
      (mapcat
        (partial walk maze)
        (extend-path maze path)))))

(defn lazy-walk
  [maze paths]
  (if (empty? paths)
    []
    (let [not-at-exit? (fn [path] (not= exit-square (get-in maze (last path))))
          [checked [at-exit & unchecked]] (split-with not-at-exit? paths)
          new-tail (concat unchecked (mapcat (partial extend-path maze) checked))]
      (if at-exit
        (cons at-exit (lazy-seq (lazy-walk maze new-tail)))
        (recur maze new-tail)))))

(defn solve
  [str-maze]
  (let [maze (to-maze str-maze)
        start (start-location maze)]
    (map path-to-directions (walk maze [start]))))

(defn lazy-solve
  [str-maze]
  (let [maze (to-maze str-maze)
        start (start-location maze)]
    (map path-to-directions (lazy-walk maze [[start]]))))

