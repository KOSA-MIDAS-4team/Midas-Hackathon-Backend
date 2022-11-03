package kosamidas.hackathon.domain.commute.domain.repository;

import kosamidas.hackathon.domain.commute.domain.Commute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommuteRepository extends JpaRepository<Commute, Long> {

    List<Commute> findByUserId(Long userId);
}