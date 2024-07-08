package model.player;

import lombok.*;

import java.math.BigDecimal;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
public class Player {
    private final String name;
    @Setter
    private BigDecimal totalWinnings = BigDecimal.ZERO;
}
