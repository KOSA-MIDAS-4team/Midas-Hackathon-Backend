package kosamidas.hackathon.domain.commute.service;

import kosamidas.hackathon.domain.commute.domain.Commute;
import kosamidas.hackathon.domain.commute.domain.type.WalkWhether;
import kosamidas.hackathon.domain.commute.exception.UserAlreadyQuited;
import kosamidas.hackathon.domain.commute.facade.CommuteFacade;
import kosamidas.hackathon.domain.commute.presentation.dto.res.OnEightHourBasisResponseDto;
import kosamidas.hackathon.domain.commute.presentation.dto.res.RemainingMinutesOfWorkResponseDto;
import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.facade.UserFacade;
import kosamidas.hackathon.global.annotation.ServiceWithTransactionReadOnly;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ServiceWithTransactionReadOnly
@RequiredArgsConstructor
public class CommuteService {

    private final UserFacade userFacade;
    private final CommuteFacade commuteFacade;

    @Transactional
    public void startOfficeGo() {
        isAlreadyQuited();
        if (!isAlreadyExistsCommuteToday()) {
            User user = userFacade.getCurrentUser();
            Commute commute = Commute.builder()
                    .officeWentDate(LocalDate.now())
                    .officeWentAt(LocalDateTime.now())
                    .quitedTime(LocalDateTime.now())
                    .week(getWeek())
                    .build();
            commute.confirmUser(user);
            commute.updateWalkingWhether();
            commuteFacade.save(commute);
        }
    }

    private void isAlreadyQuited() {
        Optional<Commute> commuteOptional = commuteFacade.findAll()
                .stream()
                .findFirst()
                .filter(commute -> commute.getOfficeWentDate().isEqual(LocalDate.now()));

        if (commuteOptional.isPresent()) {
            boolean isAlreadyQuited = commuteOptional.get().getWalkWhether().equals(WalkWhether.QUITED);
            if (isAlreadyQuited) {
                throw UserAlreadyQuited.EXCEPTION;
            }
        }
    }

    private boolean isAlreadyExistsCommuteToday() {
        return commuteFacade.findAll()
                .stream()
                .anyMatch(commute -> commute.getOfficeWentDate().isEqual(LocalDate.now()));
    }

    private int getWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    @Transactional
    public OnEightHourBasisResponseDto doQuitedTime() {
        Long userId = userFacade.getCurrentUser().getId();
        Commute commute = commuteFacade.getCommuteByUserId(userId);
        commute.updateQuitedTime(LocalDateTime.now());
        commute.updateQuitedWhether();
        long between = ChronoUnit.MINUTES.between(commute.getOfficeWentAt(), commute.getQuitedTime());
        return new OnEightHourBasisResponseDto(480 - between);
    }

    public RemainingMinutesOfWorkResponseDto getRemainingHoursOfWorkThisWeek() {
        List<Commute> commutes = commuteFacade.findAll()
                .stream()
                .filter(commute -> commute.getUser().getAuthId().equals(userFacade.getCurrentUser().getAuthId()))
                .filter(commute -> commute.getWeek() == getWeek())
                .collect(Collectors.toList());

        long result = 0;
        for (Commute commute : commutes) {
            result += ChronoUnit.MINUTES.between(commute.getOfficeWentAt(), commute.getQuitedTime());
        }

        return new RemainingMinutesOfWorkResponseDto(2400 - result);
    }
}
