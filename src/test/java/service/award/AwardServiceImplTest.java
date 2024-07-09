package service.award;

import org.junit.jupiter.api.Test;
import service.award.exception.AwardServiceException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AwardServiceImplTest {

    private final AwardServiceImpl awardService = new AwardServiceImpl();

    @Test
    public void givenValidLevel_whenGetAward_thenReturnCorrectAward() {
        // given
        int level = 1;
        BigDecimal expectedAward = BigDecimal.valueOf(500.0).setScale(2, RoundingMode.HALF_UP);

        // when
        BigDecimal actualAward = awardService.getAward(level, true);

        // then
        assertEquals(expectedAward, actualAward);
    }

    @Test
    public void givenInvalidLevel_whenGetAward_thenThrowAwardServiceException() {
        // given
        int invalidLevel = 13;

        // when & then
        assertThrows(AwardServiceException.class, () -> awardService.getAward(invalidLevel, true));
    }
}
