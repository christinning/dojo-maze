(ns maze.generators
  (:require [clojure.test.check.generators :as gen]
            [maze.core :refer [exit-square start-square non-wall wall]]))

(comment
"From: http://www.astrolog.org/labyrnth/algrithm.htm 

Eller's algorithm: This algorithm is special because it's not only faster than all the others that don't have obvious biases or blemishes, but its creation is also the most memory efficient. It doesn't even require the whole Maze to be in memory, only using storage proportional to the size of a row. It creates the Maze one row at a time, where once a row has been generated, the algorithm no longer looks at it. Each cell in a row is contained in a set, where two cells are in the same set if there's a path between them through the part of the Maze that's been made so far. This information allows passages to be carved in the current row without creating loops or isolations. This is actually quite similar to Kruskal's algorithm, just this completes one row at a time, while Kruskal's looks over the whole Maze. Creating a row consists of two parts: Randomly connecting adjacent cells within a row, i.e. carving horizontal passages, then randomly connecting cells between the current row and the next row, i.e. carving vertical passages. When carving horizontal passages, don't connect cells already in the same set (as that would create a loop), and when carving vertical passages, you must connect a cell if it's a set of size one (as abandoning it would create an isolation). When carving horizontal passages, when connecting cells union the sets they're in (since there's now a path between them), and when carving vertical passages, when not connecting a cell put it in a set by itself (since it's now disconnected from the rest of the Maze). Creation starts with each cell in its own set before connecting cells within the first row, and creation ends after connecting cells within the last row, with a special final rule that every cell must be in the same set by the time we're done to prevent isolations. (The last row is done by connecting each pair of adjacent cells if not already in the same set.) One issue with this algorithm is that it's not balanced with respect to how it treats the different edges of the Maze, where connecting vs. not connecting cells need to be done in the right proportions to prevent texture blemishes."

)



