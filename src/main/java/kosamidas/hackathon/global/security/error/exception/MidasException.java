package kosamidas.hackathon.global.security.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MidasException extends RuntimeException{
    private final ErrorCode errorCode;
}
