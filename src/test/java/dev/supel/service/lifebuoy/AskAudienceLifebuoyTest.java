package dev.supel.service.lifebuoy;

import dev.supel.model.question.Answer;
import dev.supel.service.lifebuoy.AskAudienceLifebuoy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AskAudienceLifebuoyTest {
    private AskAudienceLifebuoy lifebuoy;
    private Map<String, Answer> answerMap;
    private String correctAnswerKey;

    @BeforeEach
    void setUp() {
        lifebuoy = new AskAudienceLifebuoy(true);
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
        assertTrue(confidenceMap.get(correctAnswerKey) >= 30);
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
