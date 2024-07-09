package dev.supel.service.lifebuoy;

import dev.supel.model.question.Answer;
import dev.supel.service.lifebuoy.AskAudienceLifebuoy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LifebuoyTest {
    // Note: A sealed interface does not allow the creation of an anonymous instance,
    // and an inner class would require adding it to the 'permits' clause in the signature.
    private AskAudienceLifebuoy askAudienceLifebuoy;

    @BeforeEach
    void setUp() {
        askAudienceLifebuoy = new AskAudienceLifebuoy(true);
    }

    @Test
    void testSortConfidenceMap() {
        Map<String, Answer> answerMap = Map.of(
                "A", new Answer("Answer A", false),
                "B", new Answer("Answer B", true),
                "C", new Answer("Answer C", false),
                "D", new Answer("Answer D", false)
        );

        // Example confidence map representing audience confidence
        Map<String, Integer> confidenceMap = new LinkedHashMap<>();
        confidenceMap.put("A", 10);
        confidenceMap.put("B", 50);  // Correct answer with higher confidence
        confidenceMap.put("C", 30);
        confidenceMap.put("D", 10);

        Map<String, Integer> sortedMap = askAudienceLifebuoy.sortConfidenceMap(confidenceMap, answerMap);

        assertEquals(confidenceMap, sortedMap);
    }
}
