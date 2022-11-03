package kosamidas.hackathon.global.security.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.*;

@Getter
@AllArgsConstructor(access = PRIVATE)
@Builder
public class ErrorResponse {
    private final int status;
    private final String message;
}