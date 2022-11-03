package kosamidas.hackathon.domain.commute.service;

import kosamidas.hackathon.domain.commute.domain.Commute;
import kosamidas.hackathon.domain.commute.facade.CommuteFacade;
import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.facade.UserFacade;
import kosamidas.hackathon.global.annotation.ServiceWithTransactionReadOnly;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ServiceWithTransactionReadOnly
@RequiredArgsConstructor
public class CommuteService {

    private final UserFacade userFacade;
    private final CommuteFacade commuteFacade;

    @Transactional
    public void startOfficeGo() {
        if (!isAlreadyExistsCommuteToday()) {
            User user = userFacade.getCurrentUser();
            Commute commute = Commute.builder()
                    .officeWentDate(LocalDate.now())
                    .officeWentAt(LocalDateTime.now())
                    .build();
            commute.confirmUser(user);
            commute.updateWalkingWhether();
            commuteFacade.save(commute);
        }
    }

    private boolean isAlreadyExistsCommuteToday() {
        return commuteFacade.findAll()
                .stream()
                .anyMatch(commute -> commute.getOfficeWentDate().isEqual(LocalDate.now()));
    }

    @Transactional
    public void doQuitedTime() {
        Long userId = userFacade.getCurrentUser().getId();
        Commute commute = commuteFacade.getCommuteByUserId(userId);
        commute.updateQuitedTime(LocalDateTime.now());
        commute.updateQuitedWhether();
    }
}
