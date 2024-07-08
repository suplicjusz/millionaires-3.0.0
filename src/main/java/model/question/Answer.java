package model.question;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
public class Answer {
    private final String content;
    private final boolean correct;

    public Answer(String content, boolean correct) {
        this.content = content;
        this.correct = correct;
    }
}
