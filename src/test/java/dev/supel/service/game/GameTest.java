package dev.supel.service.game;

import dev.supel.file.FileDataReader;
import dev.supel.model.player.Player;
import dev.supel.model.question.GameQuestions;
import dev.supel.model.question.Question;
import dev.supel.service.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dev.supel.service.award.AwardServiceImpl;
import dev.supel.service.lifebuoy.AskAudienceLifebuoy;
import dev.supel.service.lifebuoy.CallToFriendLifebuoy;
import dev.supel.service.lifebuoy.FiftyFiftyLifebuoy;
import dev.supel.service.lifebuoy.Lifebuoy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        Player player = Player.of("TestPlayer");
        Map<String, LocalDateTime> times = new HashMap<>();
        times.put("START", LocalDateTime.now());

        // Use actual file from resources
        String path = "src/main/resources/QuestionsExample.txt";
        List<Question> questionList = FileDataReader.getData(path);
        var questions = GameQuestions.of(questionList).getQuestionsByLevel();

        Map<Integer, Lifebuoy> lifebuoys = Map.of(
                2, new FiftyFiftyLifebuoy(true),
                3, new AskAudienceLifebuoy(true),
                4, new CallToFriendLifebuoy(true)
        );

        AwardServiceImpl awardService = new AwardServiceImpl();

        game = new Game(player, times, questions, lifebuoys, awardService, true, 1);
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game);
        assertNotNull(game.player);
        assertNotNull(game.times);
        assertNotNull(game.awardService);
        assertNotNull(game.questions);
        assertNotNull(game.lifebuoys);
    }
}
