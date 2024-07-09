package dev.supel.service.lifebuoy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import dev.supel.model.question.Answer;

import java.util.*;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public final class CallToFriendLifebuoy implements Lifebuoy {

    private boolean isAvailable;

    @Override
    public Map<String, Integer> help(Map<String, Answer> answerMap, String correctAnswerKey) {
        Map<String, Integer> confidenceMap = new LinkedHashMap<>();
        Random random = new Random();

        // Create a list of answer keys
        List<String> keys = new ArrayList<>(answerMap.keySet());

        // Determine if the correct answer should be chosen (5 out of 6 times)
        String selectedAnswerKey = random.nextInt(6) < 5 ? correctAnswerKey : keys.get(random.nextInt(keys.size()));

        // Assign confidence values
        confidenceMap.put(selectedAnswerKey, 100);
        keys.forEach(key -> confidenceMap.putIfAbsent(key, 0));

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
