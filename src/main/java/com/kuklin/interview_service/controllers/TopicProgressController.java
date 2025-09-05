package com.kuklin.interview_service.controllers;

import com.kuklin.interview_service.models.TopicProgressDto;
import com.kuklin.interview_service.services.TopicProgressService;
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
