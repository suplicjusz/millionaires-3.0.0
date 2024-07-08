package file;

import model.question.Question;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public interface FileDataReader {

    static List<Question> getData(String filepath) {
        try (var lines = Files.lines(Paths.get(filepath))) {
            return lines
                    .map(Question::of).toList();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

}


