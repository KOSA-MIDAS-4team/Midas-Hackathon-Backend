package kosamidas.hackathon.domain.commute.presentation.dto.res;

import lombok.Getter;

@Getter
public class RemainingMinutesOfWorkResponseDto {

    private final long remainingMinutes;

    public RemainingMinutesOfWorkResponseDto(long remainingMinutes) {
        this.remainingMinutes = remainingMinutes;
    }
}
