package kosamidas.hackathon.domain.user.exception;

import kosamidas.hackathon.global.security.error.exception.ErrorCode;
import kosamidas.hackathon.global.security.error.exception.MidasException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST)
public class NotMatchedPassword extends MidasException {

    public static final MidasException EXCEPTION = new AlreadyExistsUserException();

    public NotMatchedPassword() {
        super(ErrorCode.NOT_MATCH_PASSWORD);
    }
}
