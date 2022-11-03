package kosamidas.hackathon.domain.user.domain.verifier;

import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.domain.exception.AlreadyExistsUserException;
import kosamidas.hackathon.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CreateUserVerifier {

    private final UserRepository userRepository;

    public void alreadyExistsUserVerifier(User user) {
        userRepository.findAll()
                .forEach(alreadyExistsUser -> {
                    if (Objects.equals(alreadyExistsUser, user)) {
                        throw AlreadyExistsUserException.EXCEPTION;
                    }
                });
    }

}
