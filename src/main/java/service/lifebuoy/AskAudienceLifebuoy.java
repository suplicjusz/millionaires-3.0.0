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
public final class AskAudienceLifebuoy implements Lifebuoy {

    private boolean isAvailable;

    @Override
    public Map<String, Integer> help(Map<String, Answer> answerMap, String correctAnswerKey) {
        Map<String, Integer> confidenceMap = new LinkedHashMap<>();
        Random random = new Random();

        // Define the minimum confidence for each answer
        int minimumConfidence = 5;
        int remainingConfidence = 55; // 75% - minimumConfidence * 4 (for 4 answers)
        List<String> keys = new ArrayList<>(answerMap.keySet());
        Collections.shuffle(keys);

        // Assign confidence to each answer
        for (int i = 0; i < keys.size() - 1; i++) {
            int confidence = random.nextInt(remainingConfidence + 1);
            confidenceMap.put(keys.get(i), confidence + minimumConfidence);
            remainingConfidence -= (confidence);
        }

        // Add remaining confidence to the last answer
        confidenceMap.put(keys.getLast(), minimumConfidence + remainingConfidence);

        // Add additional 25% confidence to the correct answer
        confidenceMap.put(correctAnswerKey, confidenceMap.get(correctAnswerKey) + 25);

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
