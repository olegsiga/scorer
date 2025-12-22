package com.scorer.service;

import com.scorer.entity.Sport;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SportService {

    public List<Sport> getAllSports() {
        return Arrays.asList(Sport.values());
    }
}
