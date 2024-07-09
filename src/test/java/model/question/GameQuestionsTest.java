package model.question;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameQuestionsTest {

    @Test
    void givenValidQuestions_whenCreatingGameQuestions_thenSuccess() {
        List<Question> validQuestions = List.of(
                Question.of("1;Question1;Correct1;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("2;Question2;Correct2;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("3;Question3;Correct3;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("4;Question4;Correct4;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("5;Question5;Correct5;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("6;Question6;Correct6;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("7;Question7;Correct7;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("8;Question8;Correct8;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("9;Question9;Correct9;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("10;Question10;Correct10;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("11;Question11;Correct11;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("12;Question12;Correct12;Incorrect1;Incorrect2;Incorrect3")
        );

        GameQuestions gameQuestions = GameQuestions.of(validQuestions);

        assertNotNull(gameQuestions);
        assertEquals(12, gameQuestions.getQuestionsByLevel().size());
    }

    @Test
    void givenInsufficientQuestions_whenCreatingGameQuestions_thenThrowsException() {
        List<Question> insufficientQuestions = List.of(
                Question.of("1;Question1;Correct1;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("2;Question2;Correct2;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("3;Question3;Correct3;Incorrect1;Incorrect2;Incorrect3")
        );

        assertThrows(IllegalArgumentException.class, () -> GameQuestions.of(insufficientQuestions));
    }

    @Test
    void givenValidQuestionsButMissingLevels_whenCreatingGameQuestions_thenThrowsException() {
        List<Question> validQuestions = List.of(
                Question.of("1;Question1;Correct1;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("2;Question2;Correct2;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("3;Question3;Correct3;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("4;Question4;Correct4;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("5;Question5;Correct5;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("6;Question6;Correct6;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("7;Question7;Correct7;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("8;Question8;Correct8;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("9;Question9;Correct9;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("10;Question10;Correct10;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("11;Question11;Correct11;Incorrect1;Incorrect2;Incorrect3"),
                Question.of("11;Question12;Correct12;Incorrect1;Incorrect2;Incorrect3") // duplicate
        );

        assertThrows(IllegalArgumentException.class, () -> GameQuestions.of(validQuestions));
    }
}
