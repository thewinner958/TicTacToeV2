package github.thewinner958.game;

import java.util.List;

public class Node implements Comparable<Node> {
    private final GameSetup setup;
    private final boolean isPlayerO; // false - Player X, true - Player O
    private final String[][] state;
    private final Move move;
    private final List<Node> children;
    private double numVisits, UCTValue, victories, draws, losses = 0;

}
