package github.thewinner958.game;

import lombok.Getter;

@Getter
public class GameSetup {
    private final String charX;
    private final String charO;
    private final String charEmpty;
    private final Long columns;
    private final Long rows;

    public GameSetup() {
        charX = "X";
        charO = "O";
        charEmpty = "_";
        columns = 3L;
        rows = 3L;
    }

    public GameSetup(String charX, String charO, String charEmpty, Long columns, Long rows) {
        this.charX = charX;
        this.charO = charO;
        this.charEmpty = charEmpty;
        if (columns < 3L || rows < 3L) throw new RuntimeException("You can't have a board less than 3x3");
        this.columns = columns;
        this.rows = rows;
    }
}
