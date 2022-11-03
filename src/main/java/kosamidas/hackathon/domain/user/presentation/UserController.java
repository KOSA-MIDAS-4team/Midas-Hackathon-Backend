package kosamidas.hackathon.domain.user.presentation;

import kosamidas.hackathon.domain.user.presentation.dto.req.SignupUserRequestDto;
import kosamidas.hackathon.domain.user.presentation.dto.res.UserResponseDto;
import kosamidas.hackathon.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public void signupUser(@RequestBody SignupUserRequestDto req) {
        userService.createUser(req);
    }

    @GetMapping("/{authId}")
    public UserResponseDto getUser(@PathVariable String authId) {
        return userService.getUserByAuthId(authId);
    }
}
