package kosamidas.hackathon.domain.commute.domain.repository;

import kosamidas.hackathon.domain.commute.domain.Commute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommuteRepository extends JpaRepository<Commute, Long> {

    Optional<Commute> findByUserId(Long userId);
}