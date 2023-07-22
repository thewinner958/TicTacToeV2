package github.thewinner958.game.player;

import github.thewinner958.game.GameSetup;
import github.thewinner958.game.Move;
import github.thewinner958.game.Node;
import github.thewinner958.game.player.bot.GameSimulator;
import github.thewinner958.game.player.bot.MCTSBestMoveFinder;

public class Bot implements PlayerInterface{
    private final GameSetup setup;
    private MCTSBestMoveFinder moveFinder;

    public Bot(GameSetup setup, boolean isItFirst, Node state) {
        this.setup = setup;
        if (isItFirst) {
            moveFinder = new MCTSBestMoveFinder(this.setup);
        } else {
            if (state == null) throw new RuntimeException("No state was given!");
            moveFinder = new MCTSBestMoveFinder(state, this.setup);
        }
    }

    @Override
    public String name() {
        return "KAY/O";
    }

    @Override
    public Move getMove(Node state) {
        return moveFinder.findBestMove(1000).getMove();
    }
}
