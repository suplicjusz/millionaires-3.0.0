package service.question;

import model.question.Question;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionServiceTest {

    @Test
    void testCheckAnswer() {
        Question question = Question.of("1;Sample Question;CorrectAnswer;WrongAnswer1;WrongAnswer2;WrongAnswer3");
        QuestionService questionService = QuestionService.of(question);

        assertTrue(questionService.checkAnswer("A") ||
                   questionService.checkAnswer("B") ||
                   questionService.checkAnswer("C") ||
                   questionService.checkAnswer("D"));
    }

    @Test
    void testGetCorrectAnswerLabel() {
        Question question = Question.of("1;Sample Question;CorrectAnswer;WrongAnswer1;WrongAnswer2;WrongAnswer3");
        QuestionService questionService = QuestionService.of(question);

        String correctAnswerLabel = questionService.getCorrectAnswerLabel();
        assertNotNull(correctAnswerLabel);
        assertTrue(correctAnswerLabel.matches("[A-D]"));
    }

    @Test
    void testShow() {
        Question question = Question.of("1;Sample Question;CorrectAnswer;WrongAnswer1;WrongAnswer2;WrongAnswer3");
        QuestionService questionService = QuestionService.of(question);

        questionService.show();
    }
}
