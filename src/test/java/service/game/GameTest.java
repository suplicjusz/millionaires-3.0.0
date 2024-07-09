package service.game;

import file.FileDataReader;
import model.player.Player;
import model.question.GameQuestions;
import model.question.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.award.AwardServiceImpl;
import service.lifebuoy.AskAudienceLifebuoy;
import service.lifebuoy.CallToFriendLifebuoy;
import service.lifebuoy.FiftyFiftyLifebuoy;
import service.lifebuoy.Lifebuoy;

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
