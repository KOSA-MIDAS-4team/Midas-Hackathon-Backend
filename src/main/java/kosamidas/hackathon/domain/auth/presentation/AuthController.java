package kosamidas.hackathon.domain.auth.presentation;

import kosamidas.hackathon.domain.auth.presentation.dto.req.LoginRequestDto;
import kosamidas.hackathon.domain.auth.presentation.dto.res.TokenResponseDto;
import kosamidas.hackathon.domain.auth.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody @Valid LoginRequestDto loginReq) {
        return loginService.login(loginReq);
    }

}
