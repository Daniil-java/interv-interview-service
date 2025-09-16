package com.kuklin.interviewservice.controllers;

import com.kuklin.interviewservice.services.InterviewUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userinfo")
@RequiredArgsConstructor
public class InterviewUserInfoController {
    private final InterviewUserInfoService interviewUserInfoService;

    @PutMapping("/{userId}/job-title")
    public Boolean setJobTitle(@PathVariable Long userId,
                                            @RequestBody String jobTitle) {
        return interviewUserInfoService.setJobTitle(userId, jobTitle);
    }
}
