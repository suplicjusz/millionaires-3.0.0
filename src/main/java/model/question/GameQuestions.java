package model.question;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import util.Director;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
        if (allQuestionsFromSource.size() != Director.QUANTITY_OF_LEVELS) {
            throw new IllegalArgumentException(
                    String.format("File with questions should contain exactly %d questions!", Director.QUANTITY_OF_LEVELS)
            );
        }

        var questionsForGame = new HashMap<Integer, Question>();

        AtomicInteger ai = new AtomicInteger(1);
        allQuestionsFromSource.stream().limit(Director.QUANTITY_OF_LEVELS).forEach(question -> questionsForGame.put(
                ai.get(), LevelQuestions.of(allQuestionsFromSource, ai.getAndIncrement()).draw()));

        validateQuestionsMap(questionsForGame);

        return new GameQuestions(questionsForGame);
    }

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
