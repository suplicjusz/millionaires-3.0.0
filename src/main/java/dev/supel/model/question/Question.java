package dev.supel.model.question;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import dev.supel.util.Director;

import java.util.Set;

@EqualsAndHashCode
@ToString
@Getter

public class Question {
    final Integer level;
    final String content;
    /**
     * answers.get(0).isCorrect should be always true
     */
    final Set<Answer> answers;
    private static final String QUESTION_REGEX = "\\d\\d?;.+;.+;.+;.+;.+";

    private Question(int level, String content, Set<Answer> answers) {
        this.level = level;
        this.content = content;
        this.answers = answers;
    }

    public static Question of(String expression) {
        if (expression == null || !expression.matches(QUESTION_REGEX)) {
            throw new IllegalArgumentException("Question expression is not correct!");
        }
        var questionElements = expression.split(";");
        if (!questionElements[0].matches("\\d\\d?") || Integer.parseInt(questionElements[0]) > Director.QUANTITY_OF_LEVELS) {
            throw new IllegalArgumentException("Number of level is not correct!");
        }
        return new Question(
                Integer.parseInt(questionElements[0]),
                questionElements[1],
                Set.of(
                        new Answer(questionElements[2], true),
                        new Answer(questionElements[3], false),
                        new Answer(questionElements[4], false),
                        new Answer(questionElements[5], false)
                )
        );
    }

}
