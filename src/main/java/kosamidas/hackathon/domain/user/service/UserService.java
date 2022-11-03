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
import kosamidas.hackathon.infrastructure.file.FileResponseDto;
import kosamidas.hackathon.infrastructure.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final S3Uploader s3Uploader;

    @Transactional
    public void createUser(SignupUserRequestDto req) {
        CreateUserVerifier.checkMatchedPassword(req.getPassword(), req.getCheckPassword());
        userFacade.saveAndEncodePassword(req.toEntity());
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
        Optional<Commute> commuteOptional = commuteFacade.findAll()
                .stream()
                .filter(commute -> commute.getUser().getId().equals(user.getId()))
                .filter(commute -> date.getYear() == commute.getOfficeWentDate().getYear())
                .filter(commute -> date.getMonthValue() == commute.getOfficeWentDate().getMonthValue())
                .filter(commute -> date.getDayOfMonth() == commute.getOfficeWentDate().getDayOfMonth())
                .findFirst();

        return commuteOptional.map(UserCommuteDateResponseDto::new).orElse(null);
    }

    @Transactional
    public void updateImg(MultipartFile multipartFile) throws IOException {
        User user = userFacade.getCurrentUser();
        FileResponseDto fileResponseDto = s3Uploader.saveFile(multipartFile);
        user.updateFile(fileResponseDto.getImgPath(), fileResponseDto.getImgUrl());
        userFacade.save(user);
    }
}
