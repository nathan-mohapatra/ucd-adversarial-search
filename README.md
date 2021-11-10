# UCD Adversarial Search
For this assignment, I used Java to write a computer program that plays (and usually wins) Connect Four under limited time conditions. I began by devising an evaluation function that identifies favorable game states. Then, I implemented the minimax algorithm, choosing the best move by estimating the Nash equilibrium. Lastly, I implemented the alpha-beta pruning algorithm, allowing evaluation of nodes deeper in the game tree and improving performance.

`html` directory contains various HTML files, all of which provide a brief overview of instructions and starter code for this assignment.  
`report2.pdf` is a written report of this assignment.

---

Connect Four is a tic-tac-toe variant played on a grid. Players alternate turns dropping coins into one of the seven different columns. Unlike tic-tac-toe, Connect Four is affected by gravity, and coins may only be placed at the lowest possible positions in each column. As the name implies, the goal of Connect Four is to get four of your coins in a row, either horizontally, vertically, or diagonally. The first player to do so wins. If all locations are filled without a player winning, the game is a draw. Connect Four is known to be biased in favor of the first player; thus, AI is tested as both the first and the second player. A decent AI will never lose as the first player, and a good AI will be able to win as the second player.

I began by creating an instance of the **AIModule** class that plays Connect Four (by familiarizing myself with the workings of the provided **GameState** and **AIModule** classes). Since even a simple minimax player can play perfectly given unlimited time, the goal is to create a player that can select an optimal move under limited time conditions.

The provided Connect Four framework allows you to mix and match human and computer opponents using the command line. By default, the two players are human-controlled. You can choose which AI modules to use by using the `-p1` and `-p2` switches for the first player and the second player, respectively. For example, to pit the **RandomAI** player against the **MonteCarloAI** player, you could use:

> `java Main -p1 RandomAI -p2 MonteCarloAI`

You can also customize how much time is available to the AI players. By default, each AI player has 500ms to select a move. You can use the `-t` switch to change this.

## Part 1
I described my evaluation function with
- A detailed motivation on why I believe my evaluation function is reasonable
- My evaluation function as a numerical expression with definitions of all variables
- One worked example showing a board state and my evaluation function score

## Part 2
I implemented the minimax algorithm and my evaluation function following the interface defined above.

## Part 3
I played my algorithm five times against the given AI players: **StupidAI** and **RandomAI**. My algorithm always beats these two AI players. Then, I played my algorithm ten times against **MonteCarloAI**. My algorithm beat this AI player the majority of times.

> Use the `-text` option and the `-seed` options (seeds 1 through 10) to produce the entire output from stdout. You can use output redirection to save this to a file.

I clearly reported how many of the twenty games my algorithm won/lost/drew.

## Part 4
I implemented the alpha-beta pruning algorithm. I described my successor function and how I ordered moves to ensure that the best case situation occurs.

## Part 5
I implemented the alpha-beta pruning algorithm and my evaluation function. I played my algorithm twenty times against **MonteCarloAI**. My algorithm beat this AI player the majority of times.

> Use the `-text` option and the `-seed` options (seeds 1 through 20) to produce the entire output from stdout. You can use output redirection to save this to a file.

I clearly reported how many of the twenty games my algorithm won/lost/drew.

### Submission
- **report2.pdf**: A description of my evaluation function and my successor function; the entire output from stdout from each of the forty games my algorithm played against various AI players
- **minimax_914862981.java**
- **alphabeta_914862981.java**
