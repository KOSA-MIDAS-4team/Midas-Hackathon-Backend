package kosamidas.hackathon.domain.user.service;

import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.domain.type.SignupStatus;
import kosamidas.hackathon.domain.user.facade.UserFacade;
import kosamidas.hackathon.domain.user.presentation.dto.req.SignupUserRequestDto;
import kosamidas.hackathon.domain.user.presentation.dto.res.UserResponseDto;
import kosamidas.hackathon.domain.user.verifier.CreateUserVerifier;
import kosamidas.hackathon.global.annotation.ServiceWithTransactionReadOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ServiceWithTransactionReadOnly
public class UserService {

    private final UserFacade userFacade;

    @Transactional
    public void createUser(SignupUserRequestDto req) {
        CreateUserVerifier.checkMatchedPassword(req.getPassword(), req.getCheckPassword());
        userFacade.save(req.toEntity());
    }

    public UserResponseDto getUserByAuthId(String authId) {
        return new UserResponseDto(userFacade.getUserByAuthId(authId));
    }

    // 회원가입 대기중인 유저
    public List<UserResponseDto> getUserBySignupStatusIsWaiting() {
        return userFacade.getAllUsers().stream()
                .filter(user -> user.getSignupStatus() == SignupStatus.WAITING)
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }
}
