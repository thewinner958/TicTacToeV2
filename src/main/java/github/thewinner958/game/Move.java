package github.thewinner958.game;

import java.util.Objects;

public record Move(int row, int column, boolean isPlayerX) {
    @Override
    public String toString() {
        return String.format("%s moves to [%d, %d]", isPlayerX ? "Player X" : "Player O", row, column);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move move)) return false;
        return row == move.row && column == move.column && isPlayerX() == move.isPlayerX();
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column, isPlayerX());
    }
}
