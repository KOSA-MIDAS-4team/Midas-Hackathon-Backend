package kosamidas.hackathon.domain.user.presentation;

import kosamidas.hackathon.domain.user.domain.User;
import kosamidas.hackathon.domain.user.presentation.dto.res.UserResponseDto;
import kosamidas.hackathon.domain.user.service.AdminService;
import kosamidas.hackathon.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

    private final AdminService adminService;

    @PutMapping
    public Result<List<UserResponseDto>> updateUserSignupStatus() {
        List<UserResponseDto> users = adminService.getUserBySignupStatusIsWaiting();
        return new Result<>(users, users.size());
    }
}
