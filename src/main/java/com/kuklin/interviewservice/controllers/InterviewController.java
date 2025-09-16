package com.kuklin.interviewservice.controllers;

import com.kuklin.interviewservice.services.InterviewService;
import com.kuklin.sharedlibrary.InterviewDto;
import com.kuklin.sharedlibrary.InterviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {
    private final InterviewService interviewService;

    @PostMapping
    public InterviewDto createInterview(
            @RequestBody InterviewRequest interviewRequest) {

        return interviewService.createInterview(interviewRequest);
    }

    @PutMapping
    public InterviewDto setInterviewResult(@RequestBody InterviewRequest interviewRequest) {
        return interviewService.setResultOrNull(interviewRequest);
    }

    @GetMapping
    public List<InterviewDto> getLatestResultList(@RequestParam Long userId) {
        return interviewService.getLatestResultList(userId);
    }
}
