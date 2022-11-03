package kosamidas.hackathon.domain.user.presentation.dto.req;

import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.domain.type.Department;
import kosamidas.hackathon.domain.user.domain.type.HomeStatus;
import kosamidas.hackathon.domain.user.domain.type.SignupStatus;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class SignupUserRequestDto {

    @NotBlank(message = "아이디 필수 입력 값입니다.")
    private final String authId;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private final String name;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private final String password;
    @NotBlank(message = "비밀번호 확인 값은 필수 입력 값입니다.")
    private final String checkPassword;
    @NotBlank(message = "부서는 필수 입력 값입니다.")
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
