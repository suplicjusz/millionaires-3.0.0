package model.question;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelQuestionsTest {

    @Test
    void givenValidQuestions_whenCreatingLevelQuestions_thenPropertiesAreSetCorrectly() {
        // Given
        List<Question> questions = List.of(
                Question.of("1;Question 1;Correct 1;Incorrect 1;Incorrect 2;Incorrect 3"),
                Question.of("1;Question 2;Correct 2;Incorrect 1;Incorrect 2;Incorrect 3")
        );

        // When
        LevelQuestions levelQuestions = LevelQuestions.of(questions, 1);

        // Then
        assertEquals(2, levelQuestions.toList().size());
    }

    @Test
    void givenLevelQuestions_whenDrawingQuestion_thenQuestionIsReturned() {
        // Given
        List<Question> questions = List.of(
                Question.of("1;Question 1;Correct 1;Incorrect 1;Incorrect 2;Incorrect 3"),
                Question.of("1;Question 2;Correct 2;Incorrect 1;Incorrect 2;Incorrect 3")
        );
        LevelQuestions levelQuestions = LevelQuestions.of(questions, 1);

        // When
        Question drawnQuestion = levelQuestions.draw();

        // Then
        assertNotNull(drawnQuestion);
    }
}
