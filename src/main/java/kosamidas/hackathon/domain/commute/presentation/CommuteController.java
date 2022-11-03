package kosamidas.hackathon.domain.commute.presentation;

import kosamidas.hackathon.domain.commute.presentation.dto.res.OnEightHourBasisResponseDto;
import kosamidas.hackathon.domain.commute.service.CommuteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/commute")
@RestController
public class CommuteController {

    private final CommuteService commuteService;

    @PostMapping
    public void startOfficeGo(@RequestParam("where") String where) {
        commuteService.startOfficeGo(where);
    }

    @PutMapping
    public OnEightHourBasisResponseDto doQuitedTime() {
        return commuteService.doQuitedTime();
    }

}