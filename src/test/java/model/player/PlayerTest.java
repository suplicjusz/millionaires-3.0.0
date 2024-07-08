package model.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class PlayerTest {

    @Test
    public void givenName_whenCreatePlayer_thenPlayerIsCreated() {
        // given
        String playerName = "John Doe";

        // when
        Player player = Player.of(playerName);

        // then
        assertNotNull(player);
        assertEquals(playerName, player.getName());
        assertEquals(BigDecimal.ZERO, player.getTotalWinnings());
    }

    @Test
    public void givenNullName_whenCreatePlayer_thenThrowsException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> Player.of(null));

        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    public void givenEmptyName_whenCreatePlayer_thenThrowsException() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> Player.of(""));

        assertEquals("Name cannot be null or empty", exception.getMessage());
    }

    @Test
    public void givenNegativeWinnings_whenSetTotalWinnings_thenThrowsException() {
        // given
        Player player = Player.of("John Doe");
        BigDecimal negativeWinnings = new BigDecimal("-100");

        // when & then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> player.setTotalWinnings(negativeWinnings));

        assertEquals("Total winnings cannot be negative", exception.getMessage());
    }

    @Test
    public void givenPositiveWinnings_whenSetTotalWinnings_thenWinningsAreSet() {
        // given
        Player player = Player.of("John Doe");
        BigDecimal positiveWinnings = new BigDecimal("100");

        // when
        player.setTotalWinnings(positiveWinnings);

        // then
        assertEquals(positiveWinnings, player.getTotalWinnings());
    }
}
