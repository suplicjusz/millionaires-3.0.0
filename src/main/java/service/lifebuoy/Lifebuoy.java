package service.lifebuoy;

import model.question.Answer;

import java.util.LinkedHashMap;
import java.util.Map;

public sealed interface Lifebuoy permits AskAudienceLifebuoy, CallToFriendLifebuoy, FiftyFiftyLifebuoy {
    Map<String, Integer> help(Map<String, Answer> answerMap, String correctAnswerKey);

    boolean isAvailable();

    void setAvailable(boolean available);

    default Map<String, Integer> sortConfidenceMap(Map<String, Integer> confidenceMap, Map<String, Answer> originalAnswerMap) {
        Map<String, Integer> sortedConfidenceMap = new LinkedHashMap<>();
        originalAnswerMap.forEach((label, answer) -> sortedConfidenceMap.put(label, confidenceMap.getOrDefault(label, 0)));
        return sortedConfidenceMap;
    }

}
