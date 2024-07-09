package model.question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    @Test
    void givenValidData_whenCreatingAnswer_thenPropertiesAreSetCorrectly() {
        // Given
        String content = "Sample Answer";
        boolean correct = true;

        // When
        Answer answer = new Answer(content, correct);

        // Then
        assertEquals(content, answer.getContent());
        assertTrue(answer.isCorrect());
    }
}
