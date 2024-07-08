package service.lifebuoy;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import model.question.Answer;

import java.util.*;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public final class FiftyFiftyLifebuoy implements Lifebuoy {

    private boolean isAvailable;

    @Override
    public Map<String, Integer> help(Map<String, Answer> answerMap, String correctAnswerKey) {
        Map<String, Integer> confidenceMap = new LinkedHashMap<>();

        // Create a new map excluding the correct answer
        Map<String, Answer> filteredAnswers = new LinkedHashMap<>(answerMap);
        filteredAnswers.remove(correctAnswerKey);

        // Add the correct answer with 50% confidence
        confidenceMap.put(correctAnswerKey, 50);

        // Shuffle and select one incorrect answer to assign 50% confidence
        List<String> keys = new ArrayList<>(filteredAnswers.keySet());
        Collections.shuffle(keys);
        String selectedIncorrectAnswerLabel = keys.getFirst();
        confidenceMap.put(selectedIncorrectAnswerLabel, 50);

        // Add the remaining answers with 0% confidence
        for (String key : answerMap.keySet()) {
            confidenceMap.putIfAbsent(key, 0);
        }

        // Sort the confidence map to ensure the order of answers is the same as before
        return sortConfidenceMap(confidenceMap, answerMap);
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
