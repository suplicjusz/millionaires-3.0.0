package util;

import java.util.List;

public final class Director {
    public static final List<String> CONGRATS = List.of("Excellent!", "Great!", "Splendidly", "Perfectly", "Very good", "Great answer!");
    public static final String WELCOME = "%s is with us, starting the game for a million!";
    public static final String LOOSE = "Unfortunately, that's the wrong answer...";
    public static final String WIN = "Congratulation! You are a MILLIONAIRE!";
    public static final String WINNINGS = "%s, you won $%s.";
    public static final int QUANTITY_OF_LEVELS = 12;

    private Director() {
        throw new UnsupportedOperationException("That class is an util class");
    }


}
