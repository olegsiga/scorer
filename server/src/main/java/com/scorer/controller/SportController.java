package com.scorer.controller;

import com.scorer.controller.response.SportListResponse;
import com.scorer.service.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SportController {

    private final SportService sportService;

    @PostMapping("/sport/list")
    public SportListResponse list() {
        return SportListResponse.from(sportService.getAllSports());
    }
}
