package model.question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    void givenValidData_whenCreatingQuestion_thenPropertiesAreSetCorrectly() {
        // Given
        String data = "1;What is Java?;A programming language;A coffee;A car;A planet";

        // When
        Question question = Question.of(data);

        // Then
        assertEquals(1, question.getLevel());
        assertEquals("What is Java?", question.getContent());
        assertEquals(4, question.getAnswers().size());
    }

    @Test
    void givenInvalidData_whenCreatingQuestion_thenThrowsException() {
        // Given
        String invalidData = "invalid;data";

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> Question.of(invalidData));
    }
}
