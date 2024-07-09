package dev.supel.service.award;

import java.math.BigDecimal;

public sealed interface AwardService permits AwardServiceImpl {
    BigDecimal getAward(int level, boolean correctAnswer);
}
