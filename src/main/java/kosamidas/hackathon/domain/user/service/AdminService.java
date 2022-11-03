package kosamidas.hackathon.domain.user.service;

import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.domain.type.SignupStatus;
import kosamidas.hackathon.domain.user.facade.UserFacade;
import kosamidas.hackathon.domain.user.presentation.dto.res.UserResponseDto;
import kosamidas.hackathon.global.annotation.ServiceWithTransactionReadOnly;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@ServiceWithTransactionReadOnly
@RequiredArgsConstructor
public class AdminService {
    private final UserFacade userFacade;

    public List<UserResponseDto> getUserBySignupStatusIsWaiting() {
        // 회원가입 대기중인 유저
        return userFacade.getAllUsers().stream()
                .filter(user -> user.getSignupStatus() == SignupStatus.WAITING)
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

//    public void updateUserSignupStatus(Long userId) {
//        userFacade.getUserByAuthId(userId)
//    }
}
