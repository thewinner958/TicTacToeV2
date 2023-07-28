package github.thewinner958.game;

import github.thewinner958.game.player.Bot;
import github.thewinner958.game.player.PlayerInterface;
import github.thewinner958.game.player.bot.GameSimulator;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

import static github.thewinner958.game.player.bot.GameSimulator.*;

@Getter
@Setter
public class Game {
    private Node state;
    private final GameSetup setup;
    private PlayerInterface playerX;
    private PlayerInterface playerO;
    private int winner = GAME_CONTINUES;

    public Game(GameSetup setup, boolean isPlayerX) {
        this.setup = setup;
        state = new Node(this.setup, isPlayerX);
    }

    public void setPlayerX(PlayerInterface playerX) {
        if (playerX.getClass().equals(Bot.class)) {
            throw new RuntimeException("Player X can't be a bot");
        }
        this.playerX = playerX;
    }

    public int checkWinOrDraw(Node state) {
        String[][] stateArr = state.getState();
        String searchSymbol = state.isPlayerX() ? setup.getCharX() : setup.getCharO();
        int countSame = 0;
        int emptyCount = 0;
        int winner = GAME_CONTINUES;

        for (String[] row : state.getState()) {
            for (String column : row) {
                if (column.equals(setup.getCharEmpty())) emptyCount++;
            }
        }

        //Row win
        for (int i = 0; i < stateArr.length; i++) {
            for (int j = 0; j < stateArr.length; j++) {
                if (stateArr[i][j].equals(searchSymbol)) countSame++;
                if (countSame == stateArr.length) winner = state.isPlayerX() ? X : O;
            }
            countSame = 0;
        }

        //Column win
        for (int i = 0; i < stateArr.length; i++) {
            for (int j = 0; j < stateArr.length; j++) {
                if (stateArr[j][i].equals(searchSymbol)) countSame++;
                if (countSame == stateArr.length) winner = state.isPlayerX() ? X : O;
            }
            countSame = 0;
        }

        //Main diagonal win
        for (int i = 0; i < stateArr.length; i++) {
            if (stateArr[i][i].equals(searchSymbol)) countSame++;
            if (countSame == stateArr.length) winner = state.isPlayerX() ? X : O;
        }
        countSame = 0;

        //Opposing diagonal win
        for (int i = 0; i < stateArr.length; i++) {
            if (stateArr[stateArr.length - i - 1][i].equals(searchSymbol)) countSame++;
            if (countSame == stateArr.length) winner = state.isPlayerX() ? X : O;
        }

        //Draw
        if (emptyCount == 0 && winner == GAME_CONTINUES) {
            winner = DRAW;
        }

        return winner;
    }

    // FIXME: 28/07/2023 after the first move, the bot sends the final move
    public void play() { //Temporary. probably gonna remove it when i start implementing spring
        int winner = checkWinOrDraw(state);
        Scanner scanner = new Scanner(System.in);
        String[] moveStr;

        if (playerX == null || playerO == null) {
            throw new RuntimeException("Both players must be present");
        }

        while (winner == GAME_CONTINUES) {
            winner = checkWinOrDraw(state);
            System.out.println(state);
            if (state.isPlayerX()) {
                moveStr = scanner.nextLine().split(" ");
                Move move = new Move(Integer.parseInt(moveStr[0]), Integer.parseInt(moveStr[1]), state.isPlayerX());
                try {
                    state = playerX.getMove(state.makeMove(move));
                    System.out.println(state.getMove());
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                if (playerO.getClass().equals(Bot.class)) {
                    state = playerO.getMove(state);
                    System.out.println(state.getMove());
                } else {
                    moveStr = scanner.nextLine().split(" ");
                    Move move = new Move(Integer.parseInt(moveStr[0]), Integer.parseInt(moveStr[1]), state.isPlayerX());
                    try {
                        state = playerO.getMove(state.makeMove(move));
                        System.out.println(state.getMove());
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }

    }
}
