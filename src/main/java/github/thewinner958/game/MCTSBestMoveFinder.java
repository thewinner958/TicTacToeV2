package github.thewinner958.game;

// TODO: 06/07/2023 Finish this
public class MCTSBestMoveFinder {
    private GameSetup setup;
    private GameSimulator simulator;
    private Node rootNode;
    private Node bestMove;

    public MCTSBestMoveFinder() {
        simulator = new GameSimulator();
        setup = simulator.getSetup();
    }

    public MCTSBestMoveFinder(GameSetup setup) {
        this.setup = setup;
        simulator = new GameSimulator(this.setup);
    }
}
