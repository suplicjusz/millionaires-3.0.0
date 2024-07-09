package model.question;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import util.Director;

import java.util.List;
import java.util.Random;

/**
 * Representation of a list of all questions of a given level
 */

@EqualsAndHashCode
@ToString
public class LevelQuestions {

    private final List<Question> questions;
    private static final Random rnd = new Random();

    private LevelQuestions(List<Question> questionsOnTheGivenLevel) {
        this.questions = questionsOnTheGivenLevel;
    }

    public static LevelQuestions of(List<Question> allQuestionsFromSource, int level) {

        if (allQuestionsFromSource == null) {
            throw new NullPointerException("List is null!");
        }
        if (level > Director.QUANTITY_OF_LEVELS) {
            throw new RuntimeException("Invalid quantity of levels!");
        }

        var levelQuestions = allQuestionsFromSource.stream().filter(q -> q.level.equals(level)).toList();
        if (levelQuestions.isEmpty()) {
            throw new IllegalArgumentException("File with questions does not contain all requirements levels!");
        }
        return new LevelQuestions(levelQuestions);
    }

    public Question draw() {
        return questions.get(rnd.nextInt(questions.size()));
    }

    List<Question> toList() {
        return this.questions;
    }
}
