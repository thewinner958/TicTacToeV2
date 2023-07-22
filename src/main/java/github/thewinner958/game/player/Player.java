package github.thewinner958.game.player;

import github.thewinner958.game.Move;
import github.thewinner958.game.Node;

public class Player implements PlayerInterface{
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Move getMove(Node state) {
        return state.getMove();
    }
}
