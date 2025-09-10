package com.kuklin.interviewservice.controllers;

import com.kuklin.interviewservice.models.TopicProgressDto;
import com.kuklin.interviewservice.services.TopicProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class TopicProgressController {
    private final TopicProgressService topicProgressService;

    @PutMapping("/progress")
    public TopicProgressDto updateProgress(@RequestBody TopicProgressDto dto) {
        return topicProgressService.updateProgress(
                dto.getUserId(),
                dto.getTopicId(),
                dto.getConfidenceLevel());
    }

    @GetMapping("/progress")
    public List<TopicProgressDto> getTopicProgressByUserId(@RequestParam Long userId) {
        return topicProgressService.getTopicProgressByUserId(userId);
    }

}
