package kosamidas.hackathon.domain.user.presentation;

import kosamidas.hackathon.domain.commute.presentation.dto.res.RemainingMinutesOfWorkResponseDto;
import kosamidas.hackathon.domain.commute.service.CommuteService;
import kosamidas.hackathon.domain.user.presentation.dto.req.SignupUserRequestDto;
import kosamidas.hackathon.domain.user.presentation.dto.res.UserCommuteDateResponseDto;
import kosamidas.hackathon.domain.user.presentation.dto.res.UserResponseDto;
import kosamidas.hackathon.domain.user.service.UserService;
import kosamidas.hackathon.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;
    private final CommuteService commuteService;

    @PostMapping
    public void signupUser(@RequestBody SignupUserRequestDto req) {
        userService.createUser(req);
    }

    @GetMapping
    public UserResponseDto getMyInfo() {
        return userService.getCurrentUser();
    }

    @GetMapping("/{authId}")
    public UserResponseDto getUser(@PathVariable String authId) {
        return userService.getUserByAuthId(authId);
    }

    @GetMapping("/remain")
    public RemainingMinutesOfWorkResponseDto getMinutes() {
        return commuteService.getRemainingHoursOfWorkThisWeek();
    }

    @GetMapping("/all")
    public Result<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return new Result<>(users.size(), users);
    }

    @GetMapping("/list") // 출근 중인 사람 리스트
    public Result<List<UserResponseDto>> getUserByWorking() {
        List<UserResponseDto> users = userService.getUserByWorking();
        return new Result<>(users.size(), users);
    }

    @GetMapping("/date")
    public UserCommuteDateResponseDto getDate(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return userService.getDateInfo(date);
    }

    @PutMapping("/update/img")
    public void uploadImg(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        userService.updateImg(multipartFile);
    }

}
