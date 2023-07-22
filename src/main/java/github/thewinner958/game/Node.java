package github.thewinner958.game;

import github.thewinner958.game.player.bot.GameSimulator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Node implements Comparable<Node> {
    private final GameSetup setup;
    private final boolean isPlayerX; // true - Player X, false - Player O
    private final Node parent;
    private final String[][] state;
    private final Move move;
    private final List<Node> children;
    private double numVisits, victories, draws, losses = 0;
    @Setter(AccessLevel.NONE) //Made it specifically to not be able to set the variable manually
    private double UCTValue = 0;
    private int winner = GameSimulator.GAME_CONTINUES;

    public Node(GameSetup setup, boolean isPlayerX, Node parent, String[][] state, Move move) {
        this.setup = setup;
        this.isPlayerX = isPlayerX;
        this.parent = parent;
        this.state = state;
        this.move = move;
        children = new ArrayList<>();
    }

    public Node(GameSetup setup, boolean isPlayerX) {
        this.setup = setup;
        this.isPlayerX = isPlayerX;
        parent = null;
        state = new String[setup.getSize()][setup.getSize()];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                state[i][j] = setup.getCharEmpty();
            }
        }
        move = null;
        children = new ArrayList<>();
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(o.UCTValue, UCTValue);
    }

    public void setUCTValue() {
        if (numVisits == 0) UCTValue = Double.MAX_VALUE;
        else UCTValue = ((victories + draws / 2) / numVisits) + Math.sqrt(2) * Math.sqrt(Math.log(parent.numVisits) / numVisits);
    }

    public Node makeMove(Move move) {
        String[][] state = this.state;
        if (!state[move.row()][move.column()].equals(setup.getCharEmpty())) throw new RuntimeException("Invalid move");
        state[move.row()][move.column()] = move.isPlayerX() ? setup.getCharX() : setup.getCharO();
        return new Node(setup, move.isPlayerX(), this, state, move);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node node)) return false;
        return isPlayerX() == node.isPlayerX() && Objects.equals(getSetup(), node.getSetup()) && Objects.equals(getParent(), node.getParent()) && Arrays.deepEquals(getState(), node.getState()) && Objects.equals(getMove(), node.getMove());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getSetup(), isPlayerX(), getParent(), getMove());
        result = 31 * result + Arrays.deepHashCode(getState());
        return result;
    }
}
