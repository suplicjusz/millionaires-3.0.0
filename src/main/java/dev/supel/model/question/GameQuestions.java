package dev.supel.model.question;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import dev.supel.util.Director;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Representation of map of questions of all levels
 */

@EqualsAndHashCode
@ToString
@Getter
public class GameQuestions {
    private final Map<Integer, Question> questionsByLevel;

    private GameQuestions(Map<Integer, Question> questionsByLevel) {
        this.questionsByLevel = questionsByLevel;
    }

    public static GameQuestions of(List<Question> allQuestionsFromSource) {

        if (allQuestionsFromSource == null) {
            throw new NullPointerException("List is null!");
        }

        // Grouping questions by their levels
        Map<Integer, List<Question>> questionsByLevel = allQuestionsFromSource.stream()
                .collect(Collectors.groupingBy(Question::getLevel));

        // Creating a map to hold the final questions for the game
        Map<Integer, Question> questionsForGame = new HashMap<>();

        for (int level = 1; level <= Director.QUANTITY_OF_LEVELS; level++) {
            List<Question> levelQuestions = questionsByLevel.get(level);
            if (levelQuestions == null || levelQuestions.isEmpty()) {
                throw new IllegalArgumentException("Missing questions for level " + level);
            }
            // Randomly select one question from each level
            Question selectedQuestion = levelQuestions.get(new Random().nextInt(levelQuestions.size()));
            questionsForGame.put(level, selectedQuestion);
        }

        // Validate the final map of questions
        validateQuestionsMap(questionsForGame);

        return new GameQuestions(questionsForGame);
    }

    // Validation method to ensure the map has the correct number of levels and questions
    private static void validateQuestionsMap(Map<Integer, Question> questionsByLevel) {
        if (questionsByLevel.size() != Director.QUANTITY_OF_LEVELS) {
            throw new IllegalArgumentException(
                    String.format("Map should contain exactly %d questions!", Director.QUANTITY_OF_LEVELS)
            );
        }
        for (int i = 1; i <= Director.QUANTITY_OF_LEVELS; i++) {
            if (!questionsByLevel.containsKey(i)) {
                throw new IllegalArgumentException(String.format("Missing question for level %d!", i));
            }
        }
    }
}
