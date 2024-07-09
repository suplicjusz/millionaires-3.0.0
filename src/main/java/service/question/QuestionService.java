package service.question;

import lombok.Getter;
import model.question.Answer;
import model.question.Question;

import java.util.*;

public class QuestionService {
    private final String content;
    @Getter
    private final Map<String, Answer> answers;
    private static final Random rnd = new Random();

    QuestionService(String content, Map<String, Answer> answers) {
        this.content = content;
        this.answers = answers;
    }

    public static QuestionService of(Question question) {
        String content = question.getContent();
        Map<String, Answer> answers = new LinkedHashMap<>();
        List<Answer> answerList = new ArrayList<>(question.getAnswers());
        Collections.shuffle(answerList, rnd);

        String[] labels = {"A", "B", "C", "D"};
        for (int i = 0; i < answerList.size(); i++) {
            answers.put(labels[i], answerList.get(i));
        }

        return new QuestionService(content, answers);
    }

    public void show() {
        System.out.println(content);
        answers.forEach((key, value) -> System.out.printf("%s. %s\n", key, value.getContent()));
    }

    public boolean checkAnswer(String userAnswer) {
        Answer selectedAnswer = answers.get(userAnswer.toUpperCase());
        return selectedAnswer != null && selectedAnswer.isCorrect();
    }

    public String getCorrectAnswerLabel() {
        return answers.entrySet().stream()
                .filter(entry -> entry.getValue().isCorrect())
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow();
    }
}
