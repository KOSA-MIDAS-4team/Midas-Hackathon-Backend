package kosamidas.hackathon.domain.user.service;

import kosamidas.hackathon.domain.user.facade.UserFacade;
import kosamidas.hackathon.domain.user.presentation.dto.req.SignupUserRequestDto;
import kosamidas.hackathon.domain.user.verifier.CreateUserVerifier;
import kosamidas.hackathon.global.annotation.ServiceWithTransactionReadOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@ServiceWithTransactionReadOnly
public class UserService {

    private final UserFacade userFacade;

    @Transactional
    public void createUser(SignupUserRequestDto req) {
        CreateUserVerifier.checkMatchedPassword(req.getPassword(), req.getCheckPassword());
        userFacade.save(req.toEntity());
    }
}
