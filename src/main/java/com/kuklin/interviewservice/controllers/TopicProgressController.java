package com.kuklin.interviewservice.controllers;

import com.kuklin.interviewservice.services.TopicProgressService;
import com.kuklin.sharedlibrary.TopicProgressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progress")
@RequiredArgsConstructor
public class TopicProgressController {
    private final TopicProgressService topicProgressService;

    @PutMapping
    public TopicProgressDto updateProgress(@RequestBody TopicProgressDto topicProgressDto) {
        return topicProgressService.updateProgress(topicProgressDto);
    }

    @GetMapping
    public List<TopicProgressDto> getTopicProgressByUserId(@RequestParam Long userId) {
        return topicProgressService.getTopicProgressByUserId(userId);
    }

}
