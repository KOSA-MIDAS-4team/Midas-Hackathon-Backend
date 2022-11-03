package kosamidas.hackathon.domain.commute.presentation;

import kosamidas.hackathon.domain.commute.presentation.dto.res.OnEightHourBasisResponseDto;
import kosamidas.hackathon.domain.commute.service.CommuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/commute")
@RestController
public class CommuteController {

    private final CommuteService commuteService;

    @PostMapping
    public void startOfficeGo() {
        commuteService.startOfficeGo();
    }

    @PutMapping
    public OnEightHourBasisResponseDto doQuitedTime() {
        return commuteService.doQuitedTime();
    }
}
