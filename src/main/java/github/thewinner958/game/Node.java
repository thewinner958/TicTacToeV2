package github.thewinner958.game;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Node implements Comparable<Node> {
    private final GameSetup setup;
    private final boolean isPlayerX; // true - Player X, false - Player O
    private final Node parent;
    private final String[][] state;
    private final Move move;
    private final List<Node> children;
    private double numVisits, UCTValue, victories, draws, losses = 0;
    private int winner = GameSimulator.GAME_CONTINUES;

    public Node(GameSetup setup, boolean isPlayerX, Node parent, String[][] state, Move move) {
        this.setup = setup;
        this.isPlayerX = isPlayerX;
        this.parent = parent;
        this.state = state;
        this.move = move;
        children = new ArrayList<>();
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(o.UCTValue, UCTValue);
    }

    void setUCTValue() {
        if (numVisits == 0) UCTValue = Double.MAX_VALUE;
        else UCTValue = ((victories + draws / 2) / numVisits) + Math.sqrt(2) * Math.sqrt(Math.log(parent.numVisits) / numVisits);
    }

    public Node makeMove(Move move) {
        String[][] state = this.state;
        if (!state[move.row()][move.column()].equals(setup.getCharEmpty())) throw new RuntimeException("Invalid move");
        state[move.row()][move.column()] = move.isPlayerX() ? setup.getCharX() : setup.getCharO();
        return new Node(setup, move.isPlayerX(), this, state, move);
    }
}
