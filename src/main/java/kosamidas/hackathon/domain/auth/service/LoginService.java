package kosamidas.hackathon.domain.auth.service;

import kosamidas.hackathon.domain.auth.presentation.dto.req.LoginRequestDto;
import kosamidas.hackathon.domain.auth.presentation.dto.res.TokenResponseDto;
import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.facade.UserFacade;
import kosamidas.hackathon.global.annotation.ServiceWithTransactionReadOnly;
import kosamidas.hackathon.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@ServiceWithTransactionReadOnly
@RequiredArgsConstructor
public class LoginService {

    private final UserFacade userFacade;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponseDto login(LoginRequestDto loginReq) {
        User user = userFacade.getUserByAuthId(loginReq.getAuthId());
        user.matchedPassword(passwordEncoder, user, loginReq.getPassword());
        return generateToken(user.getAuthId());
    }

    private TokenResponseDto generateToken(String authId) {
        String accessToken = jwtProvider.generateAccessToken(authId);
        String refreshToken = jwtProvider.generateRefreshToken(authId);

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiredAt(jwtProvider.getAccessTokenExpiredTime())
                .refreshTokenExpiredAt(jwtProvider.getRefreshTokenExpiredTime())
                .build();
    }
}
