package kosamidas.hackathon.domain.user.presentation.dto.req;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

    private final String authority;
    private final String name;

    public UpdateUserRequestDto(String authority, String name) {
        this.authority = authority;
        this.name = name;
    }
}
