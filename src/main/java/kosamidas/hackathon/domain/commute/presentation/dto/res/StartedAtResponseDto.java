package kosamidas.hackathon.domain.commute.presentation.dto.res;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class StartedAtResponseDto {

    private final LocalDateTime startedAt;

    public StartedAtResponseDto(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
}
