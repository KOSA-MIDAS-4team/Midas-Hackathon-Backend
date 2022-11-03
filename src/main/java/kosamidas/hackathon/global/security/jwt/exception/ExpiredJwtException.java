package kosamidas.hackathon.global.security.jwt.exception;

import kosamidas.hackathon.global.security.error.exception.ErrorCode;
import kosamidas.hackathon.global.security.error.exception.MidasException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExpiredJwtException extends MidasException {

    public static final MidasException EXCEPTION =
            new ExpiredJwtException();

    private ExpiredJwtException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }

}
