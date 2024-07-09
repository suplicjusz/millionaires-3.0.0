package service.lifebuoy;

import model.question.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FiftyFiftyLifebuoyTest {
    private FiftyFiftyLifebuoy lifebuoy;
    private Map<String, Answer> answerMap;
    private String correctAnswerKey;

    @BeforeEach
    void setUp() {
        lifebuoy = new FiftyFiftyLifebuoy(true);
        answerMap = Map.of(
                "A", new Answer("Answer A", false),
                "B", new Answer("Answer B", true),
                "C", new Answer("Answer C", false),
                "D", new Answer("Answer D", false)
        );
        correctAnswerKey = "B";
    }

    @Test
    void testHelp() {
        Map<String, Integer> confidenceMap = lifebuoy.help(answerMap, correctAnswerKey);
        assertEquals(4, confidenceMap.size());
        assertEquals(50, confidenceMap.get(correctAnswerKey));
        assertEquals(2, confidenceMap.values().stream().filter(v -> v == 50).count());
    }

    @Test
    void testIsAvailable() {
        assertTrue(lifebuoy.isAvailable());
    }

    @Test
    void testSetAvailable() {
        lifebuoy.setAvailable(false);
        assertFalse(lifebuoy.isAvailable());
    }
}
