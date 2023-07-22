package github.thewinner958.game.player;

import github.thewinner958.game.Move;
import github.thewinner958.game.Node;

public interface PlayerInterface {
    public String name();
    public Node getMove(Node state);
}
