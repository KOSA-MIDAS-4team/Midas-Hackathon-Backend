package kosamidas.hackathon.domain.user.domain.exception;

import kosamidas.hackathon.global.security.error.exception.ErrorCode;
import kosamidas.hackathon.global.security.error.exception.MidasException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST)
public class AlreadyExistsUserException extends MidasException {

    public static final MidasException EXCEPTION = new AlreadyExistsUserException();

    public AlreadyExistsUserException() {
        super(ErrorCode.ALREADY_EXISTS_USER);
    }
}
