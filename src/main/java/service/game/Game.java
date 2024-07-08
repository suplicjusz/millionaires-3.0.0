package service.game;

import file.FileDataReader;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import model.player.Player;
import model.question.GameQuestions;
import model.question.Question;
import service.QuestionService;
import service.award.AwardService;
import service.award.AwardServiceImpl;
import service.lifebuoy.AskAudienceLifebuoy;
import service.lifebuoy.CallToFriendLifebuoy;
import service.lifebuoy.FiftyFiftyLifebuoy;
import service.lifebuoy.Lifebuoy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static util.Director.*;
import static util.InputUtil.getUserInput;

@EqualsAndHashCode
@ToString
public final class Game {
    private final Player player;
    private final Map<String, LocalDateTime> times;
    private final Map<Integer, Question> questions;
    private final Map<Integer, Lifebuoy> lifebuoys;
    private final AwardService awardService;

    private boolean canContinue;
    private int currentLevel;

    private Game(Player player,
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
            QuestionService questionService = new QuestionService(question);

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
        System.out.printf(WINNINGS + "%n", player.getName(), totalWinnings.toString());
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