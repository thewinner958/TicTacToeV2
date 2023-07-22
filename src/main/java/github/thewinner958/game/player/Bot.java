package github.thewinner958.game.player;

import github.thewinner958.game.GameSetup;
import github.thewinner958.game.Move;
import github.thewinner958.game.Node;
import github.thewinner958.game.player.bot.GameSimulator;
import github.thewinner958.game.player.bot.MCTSBestMoveFinder;

public class Bot implements PlayerInterface{
    private final GameSetup setup;
    private MCTSBestMoveFinder moveFinder;

    public Bot(GameSetup setup) {
        this.setup = setup;
    }

    @Override
    public String name() {
        return "KAY/O";
    }

    @Override
    public Node getMove(Node state) {
        moveFinder = new MCTSBestMoveFinder(state, setup);
        return moveFinder.findBestMove(1000);
    }
}
