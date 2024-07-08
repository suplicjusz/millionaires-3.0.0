package file;

import model.question.Question;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileDataReaderTest {

    private static final String VALID_FILEPATH = "src/main/resources/questions_test.txt";

    @BeforeAll
    static void setup() throws IOException {
        String validTxtData = """
                1;q1;a1;a2;a3;a4
                2;q2;a1;a2;a3;a4
                3;q3;a1;a2;a3;a4
                """;
        Files.writeString(Path.of(VALID_FILEPATH), validTxtData);
    }

    @Test
    void givenValidFilePath_whenGetData_thenCorrectDataReturned() {
        List<Question> questions = FileDataReader.getData(VALID_FILEPATH);

        assertEquals(3, questions.size());

        assertEquals(1, questions.get(0).getLevel());
        assertEquals("q1", questions.get(0).getContent());

        assertEquals(2, questions.get(1).getLevel());
        assertEquals("q2", questions.get(1).getContent());

        assertEquals(3, questions.get(2).getLevel());
        assertEquals("q3", questions.get(2).getContent());
    }

    @Test
    void givenInvalidFilePath_whenGetData_thenThrowsException() {
        String invalidFilePath = "src/test/resources/non_existent_file.txt";
        assertThrows(IllegalStateException.class, () -> FileDataReader.getData(invalidFilePath));
    }
}
