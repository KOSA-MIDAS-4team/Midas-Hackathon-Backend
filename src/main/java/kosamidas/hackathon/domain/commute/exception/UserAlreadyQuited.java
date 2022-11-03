package kosamidas.hackathon.domain.commute.exception;

import kosamidas.hackathon.global.security.error.exception.ErrorCode;
import kosamidas.hackathon.global.security.error.exception.MidasException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyQuited extends MidasException {

    public static final MidasException EXCEPTION = new UserAlreadyQuited();

    public UserAlreadyQuited() {
        super(ErrorCode.ALREADY_QUITED);
    }
}
