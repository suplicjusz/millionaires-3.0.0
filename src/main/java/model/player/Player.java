package model.player;

import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode

public class Player {
    private final String name;
    private BigDecimal totalWinnings = BigDecimal.ZERO;

    private Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
    }

    public static Player of(String name) {
        return new Player(name);
    }

    public void setTotalWinnings(BigDecimal totalWinnings) {
        if (totalWinnings.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total winnings cannot be negative");
        }
        this.totalWinnings = totalWinnings;
    }
}
