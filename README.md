# maze

A rough and ready solution to the maze problem set at the London Clojure Easter dojo 14/4/2014

```
Find the exit (O) to a maze given starting point (I)

example

maze.core2=> (print "#I###\n#...O\n#...#\n#####")
 #I###
 #...O
 #...#
 #####nil
maze.core2=> (solve-maze "#I###\n#...O\n#...#\n#####")
[:S :E :E :E]
```

## License

Copyright © 2014 Chris Tinning

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
