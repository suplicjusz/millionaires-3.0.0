package app;

import service.game.Game;
import util.InputUtil;

import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        try {
            String filepath;
            boolean validPath = false;

            do {
                filepath = InputUtil.getString("Enter the path to the questions file (or press Enter to use the default):");
                if (filepath.isEmpty()) {
                    filepath = InputUtil.DEFAULT_FILEPATH;
                }

                if (Files.exists(Paths.get(filepath))) {
                    validPath = true;
                } else {
                    System.out.println("The provided file path is invalid. Please try again.");
                }
            } while (!validPath);

            String playerName = InputUtil.getString("What's your name?");
            Game game = Game.of(playerName, filepath);
            game.play();
        } finally {
            InputUtil.closeScanner();
        }
    }
}
