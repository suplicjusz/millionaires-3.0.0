package util;

import java.util.Scanner;

public final class InputUtil {

    public static final String DEFAULT_FILEPATH = "src/main/resources/QuestionsExample.txt";
    private static Scanner SCANNER = new Scanner(System.in);

    private InputUtil() {
        throw new UnsupportedOperationException("This class is an util class!");
    }

    public static void useScanner(Scanner scanner) {
        InputUtil.SCANNER = scanner;
    }

    public static int getUserInput(String message, int min, int max) {
        int input;
        do {
            System.out.println(message);
            while (!SCANNER.hasNextInt()) {
                System.out.printf("Invalid input. Please enter a number between %d and %d.%n", min, max);
                SCANNER.next();
            }
            input = SCANNER.nextInt();
        } while (input < min || input > max);
        return input;
    }

    public static String getUserInput(String message, String[] validInputs) {
        String input;
        boolean isValid;
        do {
            System.out.println(message);
            input = SCANNER.next().trim().toUpperCase();
            isValid = validInputs.length == 0;
            for (String validInput : validInputs) {
                if (validInput.equals(input)) {
                    isValid = true;
                    break;
                }
            }
            if (!isValid) {
                System.out.println("Invalid input. Please enter one of the following: " + String.join(", ", validInputs));
            }
        } while (!isValid);
        return input;
    }

    public static String getString(String message) {
        System.out.println(message);
        return SCANNER.nextLine().trim();
    }

    public static void closeScanner() {
        SCANNER.close();
    }
}
