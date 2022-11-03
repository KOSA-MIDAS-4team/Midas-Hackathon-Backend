package kosamidas.hackathon.domain.user.service;

import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.facade.UserFacade;
import kosamidas.hackathon.global.annotation.ServiceWithTransactionReadOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@ServiceWithTransactionReadOnly
@RequiredArgsConstructor
public class AdminService {

    private final UserFacade userFacade;

    @Transactional
    public void updateUserSignupStatus(String authId, String signupStatus) {
        User user = userFacade.getUserByAuthId(authId);
        user.updateSignupStatus(signupStatus);
        if (isSignupStatusAccept(signupStatus)) {
            user.updateAuthority("ROLE_USER");
        }
    }

    private boolean isSignupStatusAccept(String signupStatus) {
        return signupStatus.equals("ACCEPT");
    }

}
