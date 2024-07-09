package service.lifebuoy;

import model.question.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CallToFriendLifebuoyTest {
    private CallToFriendLifebuoy lifebuoy;
    private Map<String, Answer> answerMap;
    private String correctAnswerKey;

    @BeforeEach
    void setUp() {
        lifebuoy = new CallToFriendLifebuoy(true);
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
        assertTrue(confidenceMap.get(correctAnswerKey) == 100 && confidenceMap.containsValue(100));
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
