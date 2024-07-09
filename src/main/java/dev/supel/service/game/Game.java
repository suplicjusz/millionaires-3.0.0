package dev.supel.service.game;

import dev.supel.file.FileDataReader;
import dev.supel.service.award.AwardService;
import dev.supel.service.award.AwardServiceImpl;
import dev.supel.service.lifebuoy.AskAudienceLifebuoy;
import dev.supel.service.lifebuoy.CallToFriendLifebuoy;
import dev.supel.service.lifebuoy.FiftyFiftyLifebuoy;
import dev.supel.service.lifebuoy.Lifebuoy;
import dev.supel.service.question.QuestionService;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import dev.supel.model.player.Player;
import dev.supel.model.question.GameQuestions;
import dev.supel.model.question.Question;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static dev.supel.util.Director.*;
import static dev.supel.util.InputUtil.getUserInput;

@EqualsAndHashCode
@ToString
public final class Game {
    final Player player;
    final Map<String, LocalDateTime> times;
    final Map<Integer, Question> questions;
    final Map<Integer, Lifebuoy> lifebuoys;
    final AwardService awardService;

    boolean canContinue;
    int currentLevel;

    Game(Player player,
                 Map<String, LocalDateTime> times,
                 Map<Integer, Question> questions,
                 Map<Integer, Lifebuoy> lifebuoys,
                 AwardService awardService,
                 boolean canContinue,
                 int currentLevel) {
        this.player = player;
        this.times = times;
        this.questions = questions;
        this.lifebuoys = lifebuoys;
        this.awardService = awardService;
        this.canContinue = canContinue;
        this.currentLevel = currentLevel;
    }

    public static Game of(String playerName, String path) {
        Player player = Player.of(playerName);
        Map<String, LocalDateTime> times = new HashMap<>();
        times.put("START", LocalDateTime.now());
        var questions = GameQuestions.of(FileDataReader.getData(path));

        return new Game(
                player,
                times,
                questions.getQuestionsByLevel(),
                Map.of(
                        2, new FiftyFiftyLifebuoy(true),
                        3, new AskAudienceLifebuoy(true),
                        4, new CallToFriendLifebuoy(true)),
                new AwardServiceImpl(),
                true,
                1
        );
    }

    public void play() {
        System.out.printf(WELCOME + "%n", player.getName());
        BigDecimal totalWinnings = BigDecimal.ZERO;
        BigDecimal guaranteedWinnings = BigDecimal.ZERO;

        while (canContinue && currentLevel <= QUANTITY_OF_LEVELS) {
            Question question = questions.get(currentLevel);
            QuestionService questionService = QuestionService.of(question);

            System.out.println("Question for level " + currentLevel + ":");
            questionService.show();

            boolean answered = false;
            while (!answered) {
                answered = chooseOption(questionService);
            }

            if (checkAnswer(questionService)) {
                System.out.println(CONGRATS.get((int) (Math.random() * CONGRATS.size())));
                totalWinnings = awardService.getAward(currentLevel, true);
                System.out.printf("You have won: %s%n", totalWinnings);

                if (currentLevel == 2 || currentLevel == 7) {
                    guaranteedWinnings = totalWinnings;
                }

                currentLevel++;
            } else {
                System.out.println(LOOSE);
                canContinue = false;
                totalWinnings = guaranteedWinnings;
            }
        }

        if (currentLevel > QUANTITY_OF_LEVELS) {
            System.out.println(WIN);
            totalWinnings = awardService.getAward(QUANTITY_OF_LEVELS, true);
        }

        player.setTotalWinnings(totalWinnings);
        System.out.printf(WINNINGS + "%n", player.getName(), totalWinnings);
        times.put("END", LocalDateTime.now());
    }

    private boolean chooseOption(QuestionService questionService) {
        int option = getUserInput("Choose an option:\n1. Answer\n2. Fifty-Fifty\n3. Ask the Audience\n4. Call to a Friend", 1, 4);

        return switch (option) {
            case 1 -> true;
            case 2, 3, 4 -> {
                useLifebuoy(questionService, option);
                yield false;
            }
            default -> false;
        };
    }

    private void useLifebuoy(QuestionService questionService, int lifebuoyNumber) {
        Lifebuoy lifebuoy = lifebuoys.get(lifebuoyNumber);
        if (lifebuoy != null && lifebuoy.isAvailable()) {
            Map<String, Integer> result = lifebuoy.help(questionService.getAnswers(), questionService.getCorrectAnswerLabel());

            questionService.getAnswers().forEach((label, answer) -> {
                int confidence = result.getOrDefault(label, 0);
                System.out.printf("%s. %s (confidence: %d%%)%n", label, answer.getContent(), confidence);
            });

            lifebuoy.setAvailable(false);
        }
    }

    private boolean checkAnswer(QuestionService questionService) {
        String userAnswer = getUserInput("Your answer (A, B, C, D): ", new String[]{"A", "B", "C", "D"});
        return questionService.checkAnswer(userAnswer);
    }
}
