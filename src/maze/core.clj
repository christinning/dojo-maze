(ns maze.core
  (require [clojure.string :refer [split]]))

(def exit-square \O)
(def start-square \I)
(def non-wall \.)
(def wall \#)
(def visited-square \V)

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

(defn valid-location
  [maze loc]
  (let [current-loc (get-in maze loc)]
    (or (= current-loc exit-square)
        (= current-loc non-wall)
        (= current-loc start-square))))

(defn valid-neighbours
  [maze loc]
  (filter (partial valid-location maze)
              (neighbours loc)))

;;TODO: update to do breadth first and only return first path
(defn walk
  [maze path]
  (let [loc (last path)]
    (if (= (get-in maze loc) exit-square)
      [path]
      (mapcat
        (fn [next-loc]
          (walk (assoc-in maze loc visited-square)
                (conj path next-loc)))
        (valid-neighbours maze loc)))))

(defn solve-maze
  [str-maze]
  (let [maze (to-maze str-maze)
        start (start-location maze)]
    (path-to-directions(first (sort-by count (walk maze [start]))))))




