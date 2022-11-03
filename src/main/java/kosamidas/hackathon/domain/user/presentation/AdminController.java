package kosamidas.hackathon.domain.user.presentation;

import kosamidas.hackathon.domain.user.presentation.dto.req.UpdateUserRequestDto;
import kosamidas.hackathon.domain.user.presentation.dto.res.UserResponseDto;
import kosamidas.hackathon.domain.user.service.AdminService;
import kosamidas.hackathon.domain.user.service.UserService;
import kosamidas.hackathon.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;

    @GetMapping
    public Result<List<UserResponseDto>> getUserBySignupStatusIsWaiting() {
        List<UserResponseDto> users = userService.getUserBySignupStatusIsWaiting();
        return new Result<>(users.size(), users);
    }

    @GetMapping("/list") // 출근 중인 사람 리스트
    public Result<List<UserResponseDto>> getUserByWalking() {
        List<UserResponseDto> users = userService.getUserByWalking();
        return new Result<>(users.size(), users);
    }

    @PutMapping("/{authId}")
    public void updateUserSignupStatus(
            @PathVariable String authId,
            @RequestParam("signupStatus") String signupStatus
    ) {
        adminService.updateUserSignupStatus(authId, signupStatus);
    }

    @PutMapping("/update/{authId}")
    public void updateUserInfo(
            @PathVariable String authId,
            @RequestBody UpdateUserRequestDto req
    ) {
        userService.updateUserInfo(authId, req);
    }
}
