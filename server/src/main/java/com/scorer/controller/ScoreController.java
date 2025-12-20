package com.scorer.controller;

import com.scorer.controller.request.SubmitRequest;
import com.scorer.controller.response.SubmitResponse;
import com.scorer.entity.Sport;
import com.scorer.service.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SubmitResponse> submit(@Valid @RequestBody SubmitRequest request) {
        var sport = Sport.fromDisplayName(request.sport());
        if (sport == null) {
            return ResponseEntity.ok(SubmitResponse.error("invalid-sport"));
        }

        if (request.result() <= 0) {
            return ResponseEntity.ok(SubmitResponse.error("invalid-result"));
        }

        var scoreResult = scoreService.submitScore(sport, request.result());

        return ResponseEntity.ok(SubmitResponse.ok(scoreResult));
    }

}
