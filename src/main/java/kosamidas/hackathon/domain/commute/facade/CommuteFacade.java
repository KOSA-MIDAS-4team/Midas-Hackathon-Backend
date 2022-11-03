package kosamidas.hackathon.domain.commute.facade;

import kosamidas.hackathon.domain.commute.domain.Commute;
import kosamidas.hackathon.domain.commute.domain.repository.CommuteRepository;
import kosamidas.hackathon.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommuteFacade {

    private final CommuteRepository commuteRepository;

    public void save(Commute commute) {
        commuteRepository.save(commute);
    }

    public Commute getCommuteByUserId(Long userId) {
        return commuteRepository.findByUserId(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public List<Commute> findAll() {
        return commuteRepository.findAll();
    }
}
