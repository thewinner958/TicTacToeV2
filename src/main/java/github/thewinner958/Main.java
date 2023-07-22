package github.thewinner958;

import github.thewinner958.game.Game;
import github.thewinner958.game.GameSetup;
import github.thewinner958.game.player.Bot;
import github.thewinner958.game.player.Player;
import github.thewinner958.game.*;

// TODO: 22/07/2023 make it a spring application
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Game game = new Game(new GameSetup(), true);
        game.setPlayerX(new Player("Yoru"));
        game.setPlayerO(new Bot(new GameSetup()));
        game.play();
    }
}