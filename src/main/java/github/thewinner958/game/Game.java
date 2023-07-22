package github.thewinner958.game;

import github.thewinner958.game.player.PlayerInterface;
import lombok.Getter;
import lombok.Setter;

// TODO: 22/07/2023 finish this class
@Getter
@Setter
public class Game {
    private Node state;
    private final GameSetup setup;
    private PlayerInterface playerX;
    private PlayerInterface playerO;

    public Game(GameSetup setup, boolean isPlayerX) {
        this.setup = setup;
        state = new Node(this.setup, isPlayerX);
    }
}
