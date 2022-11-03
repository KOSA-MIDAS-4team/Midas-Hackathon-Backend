package kosamidas.hackathon.domain.auth.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
@RedisHash
public class RefreshToken {

    @Id
    private String email;

    @Indexed // 토큰으로 조회하기 위해 indexed 선언
    private String token;

    @TimeToLive
    private Long timeToLive;

    public void updateToken(String token, Long timeToLive) {
        this.token = token;
        this.timeToLive = timeToLive;
    }

}
