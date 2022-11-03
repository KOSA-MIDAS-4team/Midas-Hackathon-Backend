package kosamidas.hackathon.domain.user.presentation.dto.res;

import kosamidas.hackathon.domain.commute.domain.Commute;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
public class UserCommuteDateResponseDto {

    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;
    private final long totalMinutes;

    public UserCommuteDateResponseDto(Commute commute) {
        this.startedAt = commute.getOfficeWentAt();
        this.endedAt = commute.getQuitedTime();
        this.totalMinutes = ChronoUnit.MINUTES.between(commute.getOfficeWentAt(), commute.getQuitedTime());;
    }
}
