package github.thewinner958.game;

import java.util.Collections;
import java.util.List;
public class MCTSBestMoveFinder {
    private GameSetup setup;
    private GameSimulator simulator;
    private Node rootNode;
    private Node bestMove;

    public MCTSBestMoveFinder() { //if machine starts, default setup
        simulator = new GameSimulator();
        setup = simulator.getSetup();
        String[][] state = new String[setup.getSize()][setup.getSize()];
        for (int i = 0; i < setup.getSize(); i++) {
            for (int j = 0; j < setup.getSize(); j++) {
                state[i][j] = setup.getCharEmpty();
            }
        }
        rootNode = new Node(setup, false, null, state, null);
    }

    public MCTSBestMoveFinder(GameSetup setup) { //if machine starts, custom setup
        this.setup = setup;
        simulator = new GameSimulator(this.setup);
        String[][] state = new String[this.setup.getSize()][this.setup.getSize()];
        for (int i = 0; i < this.setup.getSize(); i++) {
            for (int j = 0; j < this.setup.getSize(); j++) {
                state[i][j] = this.setup.getCharEmpty();
            }
        }
        rootNode = new Node(this.setup, false, null, state, null);
    }

    public MCTSBestMoveFinder(Node rootNode) { //if human starts, default setup
        simulator = new GameSimulator();
        setup = simulator.getSetup();
        this.rootNode = rootNode;
    }

    public MCTSBestMoveFinder(Node rootNode, GameSetup setup) { //if human starts, custom setup
        this.setup = setup;
        simulator = new GameSimulator(this.setup);
        this.rootNode = rootNode;
    }

    //Select
    public Node selectNodeRollout() {
        Node currentNode = rootNode;

        while (true) {
            if (currentNode.getWinner() != GameSimulator.GAME_CONTINUES) return currentNode;
            if (currentNode.getChildren().isEmpty()) {
                generateChildren(currentNode);
                return currentNode.getChildren().get(0);
            } else {
                for (Node child : currentNode.getChildren()) {
                    child.setUCTValue();
                }
                Collections.sort(currentNode.getChildren());
                currentNode = currentNode.getChildren().get(0);
                if (currentNode.getNumVisits() == 0) {
                    return currentNode;
                }
            }
        }
    }

    //Expand
    public void generateChildren(Node node) {
        List<Move> moves = simulator.getAllPossibleMoves(node.getState(), node.isPlayerX());
        for (Move move : moves) {
            String[][] gameState = node.getState().clone();
            gameState[move.row()][move.column()] = node.isPlayerX() ? node.getSetup().getCharX() : node.getSetup().getCharO();
            Node child = new Node(node.getSetup(), node.isPlayerX(), node, gameState, move);
            child.setWinner(simulator.checkWinOrDraw(child.getState(), child.isPlayerX()));
            node.getChildren().add(child);
        }
    }

    //Backpropagate
    public void backpropagateRolloutResult(Node node, int won) {
        Node current = node;
        while (current != null) {
            current.setNumVisits(current.getNumVisits() + 1);
            if (won == GameSimulator.DRAW) current.setDraws(current.getDraws() + 1);
            else if (current.getWinner() == won) {
                current.setVictories(current.getVictories() + 1);
            } else current.setLosses(current.getLosses() + 1);
            current = current.getParent();
        }
    }

    public Node findBestMove(int iterations) {
        for (int i = 0; i < iterations; i++) {
            Node leafToRollout = selectNodeRollout();
            int won = simulator.simulateGameFromLeaveNode(leafToRollout);
            backpropagateRolloutResult(leafToRollout, won);
        }

        double numVisits = 0;
        for (Node child : rootNode.getChildren()) {
            if (child.getNumVisits() > numVisits) {
                bestMove = child;
                numVisits = child.getNumVisits();
            }
        }

        return bestMove;
    }
}
