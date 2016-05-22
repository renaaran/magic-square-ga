# MagicSquareGA

This is a small framework that I wrote with the intention to play with [Genetic Algorithms](https://en.wikipedia.org/wiki/Genetic_algorithm). To test this small framework I wrote a GA that try to find out a [Magic Square](https://en.wikipedia.org/wiki/Magic_square) of a given grid size. 

A Magic Square is a grid where the sum of the numbers in every row, column and both diagonals sum up to the same number M, given by the equation M = [n(n^2 + 1)] / 2. This number M is called the magic constant. For a square of side n=3 the constant M is equal to 15, as shown on the picture bellow.

![ MagicSquare ](https://upload.wikimedia.org/wikipedia/commons/e/e4/Magicsquareexample.svg)

An algorithm to solve a magic square of side equal to n is basically a combinatorial algorithm: There are (n^2)! possible combinations of the n^2 numbers in the grid. For example, a magic square of side n = 4, have 16 numbers labeled from 1 to 16 and 16! = 20922789888000 ways of combining these numbers. For a magic square of side equal to 3 we have only 362880 possibilities. So as the number n increases more difficult is to find a solution.

This is a situation where a GA can fits well, but remember that a GA is a [stocastic algorithm](https://en.wikipedia.org/wiki/Stochastic_optimization) and at every run it can return a complete different result!

