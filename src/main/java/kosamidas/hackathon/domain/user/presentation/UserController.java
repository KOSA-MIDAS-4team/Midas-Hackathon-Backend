package kosamidas.hackathon.domain.user.presentation;

import kosamidas.hackathon.domain.commute.presentation.dto.res.RemainingMinutesOfWorkResponseDto;
import kosamidas.hackathon.domain.commute.service.CommuteService;
import kosamidas.hackathon.domain.user.presentation.dto.req.SignupUserRequestDto;
import kosamidas.hackathon.domain.user.presentation.dto.res.UserCommuteDateResponseDto;
import kosamidas.hackathon.domain.user.presentation.dto.res.UserResponseDto;
import kosamidas.hackathon.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Validated
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

    @GetMapping("/date")
    public UserCommuteDateResponseDto getDate(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return userService.getDateInfo(date);
    }

    @CrossOrigin("*")
    @PutMapping("/update/img")
    public void uploadImg(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        userService.updateImg(multipartFile);
    }

}
