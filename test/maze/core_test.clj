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

(def lots-of-solutions
  (str "I..O\n"
       "....\n"
       "....\n"
       "....\n"
       "....\n"
       "....\n"
       "....\n"
       "....\n"
       "....\n"
       "....\n"
       "....\n"
       "....\n"))


(deftest can-get-path
  (testing "Simple maze"
    (is (= (-> simple lazy-solve first)
           [:S :W :W :W])))
    (is (= (-> simple lazy-solve second)
           [:S :S :W :N :W :W]))
    (is (= (-> simple lazy-solve count)
           2))
  (testing "Complex maze"
    (is (= (-> complex lazy-solve first)
           [:S :E :E :S :S :S :S :E :S :S :E :E :N :N :N :N :W :N :N :E])))
  (testing "Lots of options"
    (is (= (-> lots-of-solutions lazy-solve first)
           [:E :E :E]))))
