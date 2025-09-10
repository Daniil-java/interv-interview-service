package com.kuklin.interviewservice.controllers;

import com.kuklin.interviewservice.models.InterviewRequest;
import com.kuklin.interviewservice.models.InterviewDto;
import com.kuklin.interviewservice.services.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class InterviewController {
    private final InterviewService interviewService;

    @PostMapping("/interview")
    public InterviewDto createInterview(
            @RequestBody InterviewRequest interviewRequest) {

        return interviewService.createInterview(
                interviewRequest.getConversationId(),
                interviewRequest.getUserId());
    }

    @PutMapping("/interview")
    public InterviewDto setInterviewResult(@RequestBody InterviewRequest interviewRequest) {
        return interviewService.setResultOrNull(
                interviewRequest.getUserId(),
                interviewRequest.getConversationId(),
                interviewRequest.getResult()
        );
    }

    @GetMapping("/interview")
    public List<InterviewDto> getLatestResultList(@RequestParam Long userId) {
        return interviewService.getLatestResultList(userId);
    }
}
