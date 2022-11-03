package kosamidas.hackathon.domain.user.service;

import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.facade.UserFacade;
import kosamidas.hackathon.domain.user.presentation.dto.req.SignupUserRequestDto;
import kosamidas.hackathon.domain.user.verifier.CreateUserVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserFacade userFacade;

    @Transactional
    public void createUser(SignupUserRequestDto req) {
        CreateUserVerifier.checkMatchedPassword(req.getPassword(), req.getCheckPassword());
        userFacade.save(req.toEntity());
    }
}
