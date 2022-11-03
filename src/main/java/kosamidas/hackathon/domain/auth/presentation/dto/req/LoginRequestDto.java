package kosamidas.hackathon.domain.auth.presentation.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "아이디는 Null, 공백, 띄어쓰기를 허용하지 않습니다.")
    private String authId;

    @NotBlank(message = "비밀번호는 Null, 공백, 띄어쓰기를 허용하지 않습니다.")
    private String password;

}
