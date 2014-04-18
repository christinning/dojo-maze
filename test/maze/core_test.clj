(ns maze.core-test
  (:require [clojure.test :refer :all]
            [maze.core :refer :all]))


(def sample
  (str "#I###\n"
       "#...O\n"
       "#..##\n"
       "#####\n"))

(deftest can-get-path
  (testing "Simple maze"
    (is (= (path sample)
           [:S :E :E :E]))))
