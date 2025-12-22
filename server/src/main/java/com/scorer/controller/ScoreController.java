package com.scorer.controller;

import com.scorer.controller.request.SubmitRequest;
import com.scorer.controller.response.SubmitResponse;
import com.scorer.entity.Performance;
import com.scorer.service.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping("/score/submit")
    public SubmitResponse submit(@Valid @RequestBody SubmitRequest request) {
        var performance = new Performance(request.toSport(), request.result());
        var scoreResult = scoreService.submitScore(performance);
        return SubmitResponse.from(scoreResult);
    }

}
