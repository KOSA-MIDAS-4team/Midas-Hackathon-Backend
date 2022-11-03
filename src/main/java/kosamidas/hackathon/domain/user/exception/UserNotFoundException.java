package kosamidas.hackathon.domain.user.exception;

import kosamidas.hackathon.global.security.error.exception.ErrorCode;
import kosamidas.hackathon.global.security.error.exception.MidasException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST)
public class UserNotFoundException extends MidasException {

    public static final MidasException EXCEPTION = new AlreadyExistsUserException();

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
