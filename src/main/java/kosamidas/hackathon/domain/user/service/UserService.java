package kosamidas.hackathon.domain.user.service;

import kosamidas.hackathon.domain.commute.domain.Commute;
import kosamidas.hackathon.domain.commute.domain.type.WalkWhether;
import kosamidas.hackathon.domain.commute.facade.CommuteFacade;
import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.domain.type.SignupStatus;
import kosamidas.hackathon.domain.user.facade.UserFacade;
import kosamidas.hackathon.domain.user.presentation.dto.req.SignupUserRequestDto;
import kosamidas.hackathon.domain.user.presentation.dto.req.UpdateUserRequestDto;
import kosamidas.hackathon.domain.user.presentation.dto.res.UserCommuteDateResponseDto;
import kosamidas.hackathon.domain.user.presentation.dto.res.UserResponseDto;
import kosamidas.hackathon.domain.user.verifier.CreateUserVerifier;
import kosamidas.hackathon.global.annotation.ServiceWithTransactionReadOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ServiceWithTransactionReadOnly
public class UserService {

    private final UserFacade userFacade;
    private final CommuteFacade commuteFacade;

    @Transactional
    public void createUser(SignupUserRequestDto req) {
        CreateUserVerifier.checkMatchedPassword(req.getPassword(), req.getCheckPassword());
        userFacade.save(req.toEntity());
    }

    public UserResponseDto getCurrentUser() {
        return new UserResponseDto(userFacade.getCurrentUser());
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

    public List<UserResponseDto> getUserByWalking() {
        // 오늘 일 하고있는 유저의 출퇴근 테이블
        List<Commute> commutes = commuteFacade.findAll()
                .stream()
                .filter(commute -> commute.getOfficeWentDate().isEqual(LocalDate.now()))
                .filter(commute -> commute.getWalkWhether().equals(WalkWhether.WALKING))
                .collect(Collectors.toList());

        List<User> users = new ArrayList<>();
        commutes.forEach(commute -> users.add(commute.getUser()));

        return users.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUserInfo(String authId, UpdateUserRequestDto req) {
        User user = userFacade.getUserByAuthId(authId);
        if (req.getAuthority() != null) {
            user.updateAuthority(req.getAuthority());
        }
        if (req.getName() != null) {
            user.updateName(req.getName());
        }
    }

    public UserCommuteDateResponseDto getDateInfo(LocalDate date) {
        User user = userFacade.getCurrentUser();
        List<Commute> commutes = commuteFacade.findAll()
                .stream()
                .filter(commute -> {
                    System.out.println("commute.getUser().getId() = " + commute.getUser().getId());
                    System.out.println("currentUser = " + userFacade.getCurrentUser().getId());
                    return commute.getUser().getId().equals(user.getId());
                })
                .filter(commute -> {
                    System.out.println("date.getYear() = " + date.getYear());
                    System.out.println("commute = " + commute.getOfficeWentDate().getYear());
                    return date.getYear() == commute.getOfficeWentDate().getYear();
                })
                .filter(commute -> {
                    System.out.println("date.getMonthValue() = " + date.getMonthValue());
                    System.out.println("commute.getOfficeWentDate().getMonthValue() = " + commute.getOfficeWentDate().getMonthValue());
                    return date.getMonthValue() == commute.getOfficeWentDate().getMonthValue();
                })
                .filter(commute -> {
                    System.out.println("date.getDayOfMonth() = " + date.getDayOfMonth());
                    System.out.println("commute = " + commute.getOfficeWentDate().getDayOfMonth());
                    return date.getDayOfMonth() == commute.getOfficeWentDate().getDayOfMonth();
                })
                .collect(Collectors.toList());
        if (!commutes.isEmpty()) {
            return new UserCommuteDateResponseDto(commutes.get(0));
        } else {
            return null;
        }
    }
}
