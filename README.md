# UCD Adversarial Search
For this assignment, I used Java to write a computer program that plays (and usually wins) Connect Four under limited time conditions. I began by devising an evaluation function that identifies favorable game states. Then, I implemented the minimax algorithm, choosing the best move by estimating the Nash equilibrium. Lastly, I implemented the alpha-beta pruning algorithm, allowing evaluation of nodes deeper in the game tree and improving performance.

`html` directory contains various HTML files, all of which provide a brief overview of instructions and starter code for this assignment.  
`report2.pdf` is a written report of this assignment.

---

Connect Four is a tic-tac-toe variant played on a grid. Players alternate turns dropping coins into one of the seven different columns. Unlike tic-tac-toe, Connect Four is affected by gravity, and coins may only be placed at the lowest possible positions in each column. As the name implies, the goal of Connect Four is to get four of your coins in a row, either horizontally, vertically, or diagonally. The first player to do so wins. If all locations are filled without a player winning, the game is a draw. Connect Four is known to be biased in favor of the first player; thus, AI is tested as both the first and the second player. A decent AI will never lose as the first player, and a good AI will be able to win as the second player.

I began by creating an instance of the `AIModule` class that plays Connect Four (by familiarizing myself with the workings of the provided `GameState` and `AIModule` classes). Since even a simple minimax player can play perfectly given unlimited time, the goal is to create a player that can select an optimal move under limited time conditions.

The provided Connect Four framework allows you to mix and match human and computer opponents using the command line. By default, the two players are human-controlled. You can choose which AI modules to use by using the `-p1` and `-p2` switches for the first player and the second player, respectively. For example, to pit the `RandomAI` player against the `MonteCarloAI` player, you could use: `java Main -p1 RandomAI -p2 MonteCarloAI`

Any unspecified players will be filled in with human players.

You can also customize how much time is available to the AI players. By default, each AI player has 500ms to select a move. You can use the `-t` switch to change this.  
You can also use the `--help` switch to learn more about the options available to you.

## Part 1: Creating Evaluation Function
In the context of game theory, a game tree is a graph representing all possible game states within such a game; it is a directed graph whose nodes are states (e.g. the arrangement of the pieces in a board game) and whose edges are actions (e.g. to move a piece from one position on the board to another). Following this analogy, the root node of the tree is the starting state of a game, and the leaf nodes are the termial states of a game.

An evaluation function quantifies the utility (value or goodness) of a node in a game tree, allowing the advantageousness of different game states to be compared. Here is a visual demonstration of how I evaluated a state of Connect Four:

<p align="center">
  <img src="https://i.postimg.cc/GmLbJjYc/eval-func.png" />
</p>

Typically, an evaluation function either attaches values to positions or estimates the number of ways to win or lose. My evaluation function does the former: It separates the positions on the Connect Four board into four distinct features (red, orange, yellow, green) according to proximity to the center of the board, and it assigns every position a value (7, 5, 3, 1) according to the feature it belongs to. The closer a position is to the center of the board, the greater the assigned value (e.g. red, the center of the board, is assigned the greatest value). Intuitively, central positions on the Connect Four board seem more valuable, because there is more "space" around them to form connections; my evaluation function has captured this intuition in a simple and uniform manner.

For more information about this (i.e. the evaluation function as a numerical expression, with definitions of variables and an example of a particular game state), see the report.

## Part 2: Implementing Evaluation Function and Minimax Algorithm
For an adversarial, two-player, zero-sum (if a player wins, then the other player loses) game such as Connect Four, the Nash equilibrium defines the solution of the game, or the leaf node that the players arrive at when they play rationally and make intelligent decisions.

Due to the exponentially large game trees of complex games, non-deterministic algorithms will use partial game trees to estimate the Nash equilibrium, making computation feasible on modern computers. The minimax algorithm is one such non-deterministic algorithm that evaluates the utility of the board several turns down the road. It defines player rationality as follows: The AI assumes that the opponent always selects the best move, minimizing the utility for the AI. With this assumption, the AI attempts to maximize the minimum utility. Thus, the two players in this scenario can be referred to as the maximizing player and the minimizing player, or `Max` and `Min`.

An interesting consequence is that if a player behaves irrationally (i.e. does not minimize the utility for the AI), then the algorithm is disrupted and may not perform as well.

My implementation of the evaluation function and the minimax algorithm is in the file `minimax_914862981.java`. The algorithm is recursive in that the functions simulating player decision-making call each other, with `MaxValue` calling `MinValue` and `MinValue` calling `MaxValue`. The recursive loop is broken when either a terminal state is reached or a predetermined depth limit is reached (hence the partial game tree). After the minimax algorithm moves in depth-first search fashion down the game tree until this depth is reached, `getEval` returns the utility of the board that is "passed upward" to the root node and used to select the best move.

## Part 3: Recording Minimax Algorithm Performance
I played the minimax algorithm ten times against the given AI players: `StupidAI` and `RandomAI`. My algorithm always beats these two AI players. Then, I played the minimax algorithm twenty times against `MonteCarloAI`. My algorithm beat this AI player the majority of times.

> Use the `-text` option and the `-seed` options (seeds 1 through 10) to produce the entire output from `stdout`. You can use output redirection to save this to a file.

I clearly reported how many of the fourty games, as either the first player or the second player, my algorithm won, lost, or tied:
|              | StupidAI | RandomAI | MonteCarlo |
|:------------:|:--------:|:--------:|:----------:|
| Minimax (P1) |    5/5   |    5/5   |    8/10    | 
| Minimax (P2) |    5/5   |    5/5   |   10/10    |

## Part 4: Implementing Alpha-Beta Pruning Algorithm
Alpha-beta pruning is a search algorithm that optimizes the minimax algorithm by decreasing the number of nodes that are evaluated, allowing evaluation of nodes deeper in the game tree and improving performance. It maintains two values, alpha and beta, which respectively represent lower (minimum utility that the maximizing player is guaranteed) and upper (maximum utility that the minimizing player is guaranteed) bounds. Alpha and beta are updated during recursion and, if the utility of a node falls outside of these bounds, then it is "pruned" and no longer considered, as it cannot be the optimal move according to the aforementioned definition of player rationality.

Furthermore, the alpha-beta pruning algorithm will often include a successor function, which orders sibling nodes in the game tree such that the optimal move is encountered earlier (and even more nodes are pruned as a result).

My implementation of the alpha-beta pruning algorithm is in the file `alphabeta_914862981.java`. Alpha and beta are respectively initialized as negative infinity and positive infinity and are recursively updated in `MaxValue` and `MinValue`. The actual pruning of suboptimal nodes also occurs during recursion. Since my evaluation function gives greater weight to central columns, this information can be used to order sibling nodes to ensure that the best-case situation will occur. My successor "function" is a for-loop that uses an array of integers to iterate through the columns in a particular order, as opposed to from left to right: `MaxValue` uses `maxSuccessor` to iterate through the columns outwards from the center, and `MinValue` uses `minSuccessor` to iterate through the columns inwards from the outside.

Together, alpha-beta pruning and the successor "function" doubled the depth limit, or the size of the partial game tree used to estimate the Nash equilibrium. Under limited time conditions, this is an especially important improvement.

## Part 5: Recording Alpha-Beta Pruning Algorithm Performance
I played the alpha-beta pruning algorithm twenty times against `MonteCarloAI`. My algorithm always beats this AI player.

> Use the `-text` option and the `-seed` options (seeds 1 through 20) to produce the entire output from `stdout`. You can use output redirection to save this to a file.

I clearly reported how many of the twenty games, as either the first player or the second player, my algorithm won, lost, or tied:
|                 | MonteCarlo |
|:---------------:|:----------:|
| Alpha-beta (P1) |    10/10   | 
| Alpha-beta (P2) |    10/10   |
