package dev.supel.service.award;

import dev.supel.service.award.exception.AwardServiceException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public final class AwardServiceImpl implements AwardService {
    public static final Map<Integer, BigDecimal> AWARDS = new HashMap<>();

    static {
        AWARDS.put(1, BigDecimal.valueOf(500.0).setScale(2, RoundingMode.HALF_UP));
        AWARDS.put(2, BigDecimal.valueOf(1000.0).setScale(2, RoundingMode.HALF_UP));
        AWARDS.put(3, BigDecimal.valueOf(2000.0).setScale(2, RoundingMode.HALF_UP));
        AWARDS.put(4, BigDecimal.valueOf(5000.0).setScale(2, RoundingMode.HALF_UP));
        AWARDS.put(5, BigDecimal.valueOf(10000.0).setScale(2, RoundingMode.HALF_UP));
        AWARDS.put(6, BigDecimal.valueOf(20000.0).setScale(2, RoundingMode.HALF_UP));
        AWARDS.put(7, BigDecimal.valueOf(40000.0).setScale(2, RoundingMode.HALF_UP));
        AWARDS.put(8, BigDecimal.valueOf(75000.0).setScale(2, RoundingMode.HALF_UP));
        AWARDS.put(9, BigDecimal.valueOf(125000.0).setScale(2, RoundingMode.HALF_UP));
        AWARDS.put(10, BigDecimal.valueOf(250000.0).setScale(2, RoundingMode.HALF_UP));
        AWARDS.put(11, BigDecimal.valueOf(500000.0).setScale(2, RoundingMode.HALF_UP));
        AWARDS.put(12, BigDecimal.valueOf(1000000.0).setScale(2, RoundingMode.HALF_UP));
    }


    @Override
    public BigDecimal getAward(int level, boolean correctAnswer) {
        if (!AWARDS.containsKey(level)) {
            throw new AwardServiceException("Level not found");
        }
        return AWARDS.get(level);
    }
}
