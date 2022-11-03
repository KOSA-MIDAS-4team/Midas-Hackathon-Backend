package kosamidas.hackathon.domain.commute.presentation.dto.res;

import lombok.Getter;

@Getter
public class OnEightHourBasisResponseDto {

    public final long time;

    public OnEightHourBasisResponseDto(long time) {
        this.time = time;
    }
}
