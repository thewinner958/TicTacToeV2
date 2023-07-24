package github.thewinner958.game;

import lombok.Getter;

@Getter
public class GameSetup {
    private final String charX;
    private final String charO;
    private final String charEmpty;
    private final Integer size;

    public GameSetup() {
        charX = "X";
        charO = "O";
        charEmpty = "_";
        size = 3;
    }

    public GameSetup(String charX, String charO, String charEmpty, Integer size) {
        this.charX = charX;
        this.charO = charO;
        this.charEmpty = charEmpty;
        if (size < 3) throw new RuntimeException("You can't have a board less than 3x3");
        this.size = size;
    }

    public GameSetup(GameSetup another) { // Copy constructor
        this.charX = another.charX;
        this.charO = another.charO;
        this.charEmpty = another.charEmpty;
        this.size = another.size;
    }
}
