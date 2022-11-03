package kosamidas.hackathon.domain.user.presentation.dto.req;

import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.domain.type.Authority;
import kosamidas.hackathon.domain.user.domain.type.Department;
import kosamidas.hackathon.domain.user.domain.type.HomeStatus;
import kosamidas.hackathon.domain.user.domain.type.SignupStatus;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SignupUserRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private final String authId;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private final String name;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private final String password;
    private final String checkPassword;
    private final String department;
    private final String Authority;

    @Builder
    public SignupUserRequestDto(String authId, String name, String password, String checkPassword, String department, String authority) {
        this.authId = authId;
        this.name = name;
        this.password = password;
        this.checkPassword = checkPassword;
        this.department = department;
        Authority = authority;
    }

    @Builder
    public User toEntity(){
        return User.builder()
                .authId(authId)
                .name(name)
                .password(password)
                .department(Department.valueOf(department))
                .authority(kosamidas.hackathon.domain.user.domain.type.Authority.ROLE_NONE)
                .signupStatus(SignupStatus.WAITING)
                .homeStatus(HomeStatus.NONE)
                .build();
    }

}
