package github.thewinner958.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSimulator {
    static final int EMPTY = -1;
    static final int O = 0;
    static final int X = 1;

    static final int DRAW = 2;
    static final int GAME_CONTINUES = -2;

    private final GameSetup setup;
    private final Random random;

    public GameSimulator() {
        setup = new GameSetup();
        random = new Random();
        random.setSeed(1);
    }

    public GameSimulator(GameSetup setup) {
        this.setup = setup;
        random = new Random();
        random.setSeed(1);
    }

    public int simulateGameFromLeaveNode(Node node) {
        if (node.getWinner() != GAME_CONTINUES) return node.getWinner();
        boolean player = node.isPlayerX();
        String[][] currentState = node.getState().clone();

        while (true) {
            List<Move> possibleMoves = getAllPossibleMoves(currentState, player);
            int randomMoveIndex = random.nextInt(possibleMoves.size());
            Move moveToMake = possibleMoves.get(randomMoveIndex);
            currentState[moveToMake.row()][moveToMake.column()] = moveToMake.isPlayerX() ? setup.getCharX() : setup.getCharO();
            int won = checkWinOrDraw(currentState, player);
            if (won != GAME_CONTINUES) return won;
            player = !player;
        }
    }

    public List<Move> getAllPossibleMoves(String[][] state, boolean isPlayerX) {
        List<Move> result = new ArrayList<>();
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (state[i][j].equals(setup.getCharEmpty())) {
                    result.add(new Move(i, j, isPlayerX));
                }
            }
        }
        return result;
    }

    public int checkWinOrDraw(String[][] state, boolean isPlayerX) {
        String searchSymbol = isPlayerX ? setup.getCharX() : setup.getCharO();
        int countSame = 0;
        int emptyCount = 0;
        int winner = GAME_CONTINUES;

        for (String[] row : state) {
            for (String column : row) {
                if (column.equals(setup.getCharEmpty())) emptyCount++;
            }
        }

        //Row win
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (state[i][j].equals(searchSymbol)) countSame++;
                if (countSame == state.length) winner = isPlayerX ? X : O;
            }
            countSame = 0;
        }

        //Column win
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (state[j][i].equals(searchSymbol)) countSame++;
                if (countSame == state.length) winner = isPlayerX ? X : O;
            }
            countSame = 0;
        }

        //Main diagonal win
        for (int i = 0; i < state.length; i++) {
            if (state[i][i].equals(searchSymbol)) countSame++;
            if (countSame == state.length) winner = isPlayerX ? X : O;
        }
        countSame = 0;

        //Opposing diagonal win
        for (int i = 0; i < state.length; i++) {
            if (state[state.length - i - 1][i].equals(searchSymbol)) countSame++;
            if (countSame == state.length) winner = isPlayerX ? X : O;
        }

        //Draw
        if (emptyCount == 0 && winner == GAME_CONTINUES) {
            winner = DRAW;
        }

        return winner;
    }
}
