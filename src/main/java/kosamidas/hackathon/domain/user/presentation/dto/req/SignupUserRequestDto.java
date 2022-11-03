package kosamidas.hackathon.domain.user.presentation.dto.req;

import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.domain.type.Department;
import kosamidas.hackathon.domain.user.domain.type.SignupStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupUserRequestDto {

    private final String authId;
    private final String name;
    private final String password;
    private final String checkPassword;
    private final String department;

    @Builder
    public SignupUserRequestDto(String authId, String name, String password, String checkPassword, String department, String authority) {
        this.authId = authId;
        this.name = name;
        this.password = password;
        this.checkPassword = checkPassword;
        this.department = department;
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
                .build();
    }

}
