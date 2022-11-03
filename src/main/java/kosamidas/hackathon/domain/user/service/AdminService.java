package kosamidas.hackathon.domain.user.service;

import kosamidas.hackathon.domain.user.facade.UserFacade;
import kosamidas.hackathon.global.annotation.ServiceWithTransactionReadOnly;
import lombok.RequiredArgsConstructor;

@ServiceWithTransactionReadOnly
@RequiredArgsConstructor
public class AdminService {

    private final UserFacade userFacade;


}
