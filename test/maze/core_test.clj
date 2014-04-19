(ns maze.core-test
  (:require [clojure.test :refer :all]
            [maze.core :refer :all]))


(def simple
  (str "###I#\n"
       "O...#\n"
       "##..#\n"
       "#####\n"))

(def complex
  (str "#I#####\n"
       "#...#.O\n"
       "#.#.#.#\n"
       "#.#.#..\n"
       "..#.##.\n"
       ".##..#.\n"
       "...#.#.\n"
       "##.....\n"
       "###.###\n"
       ))

(deftest can-get-path
  (testing "Simple maze"
    (is (= (solve-maze simple)
           [:S :W :W :W])))
  (testing "Complex maze"
    (is (= (solve-maze complex)
           [:S :E :E :S :S :S :S :E :S :S :E :E :N :N :N :N :W :N :N :E]))))
