package dev.supel.util;

import dev.supel.util.InputUtil;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class InputUtilTest {

    private final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        InputUtil.closeScanner();
    }

    @Test
    void testGetUserInputString() {
        String[] validInputs = {"YES", "NO"};
        String message = "Please enter YES or NO:";
        String data = "YES\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        scanner = new Scanner(System.in);
        InputUtil.useScanner(scanner);

        String input = InputUtil.getUserInput(message, validInputs);
        assertEquals("YES", input);
    }

    @Test
    void testGetString() {
        String message = "Please enter a string:";
        String data = "SOME_STRING\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        scanner = new Scanner(System.in);
        InputUtil.useScanner(scanner);

        String input = InputUtil.getString(message);
        assertEquals("SOME_STRING", input);
    }

    @Test
    void testGetUserInputOutOfRange() {
        String data = "15\n5\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        scanner = new Scanner(System.in);
        InputUtil.useScanner(scanner);

        String message = "Please enter a number between 1 and 10:";
        int input = InputUtil.getUserInput(message, 1, 10);
        assertEquals(5, input);  // Expecting the second input "5" after incorrect "15"
    }

    @Test
    void testInvalidInput() {
        String[] validInputs = {"YES", "NO"};
        String message = "Please enter YES or NO:";
        String data = "MAYBE\nYES\n";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        scanner = new Scanner(System.in);
        InputUtil.useScanner(scanner);

        String input = InputUtil.getUserInput(message, validInputs);
        assertEquals("YES", input);

        String expectedOutput = "Invalid input. Please enter one of the following: YES, NO";
        assertTrue(testOut.toString().contains(expectedOutput));
    }
}
