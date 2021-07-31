public class minimax_914862981 extends AIModule {
    int cutoffDepth = 5; // Adjust depth of cutoff, compromise between depth searched and time taken
    int bestMoveSeen;
    int playerID;

    int[] arrSuccessor = {3, 2, 4, 1, 5, 0, 6};

    public void getNextMove(GameStateModule gameState) {
        playerID = gameState.getActivePlayer();

        // Recurse through game tree, choose best move
        while (!terminate) {
            MaxValue(gameState, 0);
            if (!terminate)
                chosenMove = bestMoveSeen;
        }
        // Make chosen move
        if (gameState.canMakeMove(chosenMove))
            gameState.makeMove(chosenMove);
    }

    // MAX player
    private int MaxValue(GameStateModule gameState, int currDepth) {
        if (terminate) {
            return 0;

        // Calculate evaluation when depth of cutoff is reached
        } else if (currDepth == cutoffDepth) {
            return getEval(gameState);

        // Search deeper
        } else {
            currDepth++;
            int v = Integer.MIN_VALUE;
            int utility = Integer.MIN_VALUE; // temporary utility

            // Iterate through columns
            for (int i : arrSuccessor) {
                // If column is not full
                if (gameState.canMakeMove(i)) {
                    gameState.makeMove(i);

                    // If move results in game over
                    if (gameState.isGameOver()) {
                        int winner = gameState.getWinner();
                        if (winner == playerID) // minimax wins
                            v = Integer.MAX_VALUE - currDepth;
                        else                    // opponent wins
                            v = Integer.MIN_VALUE + currDepth;
                    // Continue recursion
                    } else {
                        v = Math.max(v, MinValue(gameState, currDepth));
                    }

                    gameState.unMakeMove();

                    // MAX represents root of game tree, choose best utility
                    if (v > utility) {
                        utility = v;
                        // Choose best move
                        if (currDepth == 1)
                            bestMoveSeen = i;
                    }
                }
            }
            return v;
        }
    }

    // MIN player
    private int MinValue(GameStateModule gameState, int currDepth) {
        if (terminate) {
            return 0;

        // Calculate evaluation when depth of cutoff is reached
        } else if (currDepth == cutoffDepth) {
            return getEval(gameState);

        // Search deeper
        } else {
            currDepth++;
            int v = Integer.MAX_VALUE;

            // Iterate through columns
            for (int i : arrSuccessor) {
                // If column is not full
                if (gameState.canMakeMove(i)) {
                    gameState.makeMove(i);

                    // If move results in game over
                    if (gameState.isGameOver()) {
                        int winner = gameState.getWinner();
                        // Evaluated with value outside bounds of getEval()
                        if (winner == playerID) // minimax wins
                            v = Integer.MAX_VALUE - currDepth;
                        else                    // opponent wins
                            v = Integer.MIN_VALUE + currDepth;
                    // Continue recursion
                    } else {
                        v = Math.min(v, MaxValue(gameState, currDepth));
                    }

                    gameState.unMakeMove();
                }
            }
            return v;
        }
    }

    private int getEval(GameStateModule gameState) {
        // Player1 features, best -> worst
        int red1, orange1, yellow1, green1;
        red1 = orange1 = yellow1 = green1 = 0;
        // Player2 features, best -> worst
        int red2, orange2, yellow2, green2;
        red2 = orange2 = yellow2 = green2 = 0;
        // Four sets of features
        int[] arr1, arr2, arr3, arr4;
        arr1 = arr2 = arr3 = arr4 = new int[2];

        // Iterate through features
        for (int col = 0; col < gameState.getWidth(); col++) {
            for (int row = 0; row < gameState.getHeight(); row++) {
                // "Red" features
                if (col == 3 && (row == 2 || row == 3))
                    arr1 = countFeatures(gameState.getAt(col, row), red1, red2);
                // "Orange" features
                else if ((col >= 2 && col <= 4) && (row >= 1 && row <= 4))
                    arr2 = countFeatures(gameState.getAt(col, row), orange1, orange2);
                // "Yellow" features
                else if (col >= 1 && col <= 5)
                    arr3 = countFeatures(gameState.getAt(col, row), yellow1, yellow2);
                // "Green" features
                else // if (col == 0 || col == 6)
                    arr4 = countFeatures(gameState.getAt(col, row), green1, green2);
            }
        }

        // Calculate sum of weighted features
        int eval = 7 * (arr1[0] - arr1[1]) + 5 * (arr2[0] - arr2[1]);
        eval += 3 * (arr3[0] - arr3[1]) + (arr4[0] - arr4[1]);

        if (playerID == 1)
            return eval;
        else // If opponent, negate evaluation
            return -eval;
    }

    private int[] countFeatures(int featureID, int feature1, int feature2) {
        if (featureID == 1)
            feature1++;
        else if (featureID == 2)
            feature2++;

        return new int[] {feature1, feature2};
    }
}
