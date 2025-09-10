package com.kuklin.interviewservice.controllers;

import com.kuklin.interviewservice.models.TopicDto;
import com.kuklin.interviewservice.services.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @GetMapping("/topic/{topicId}")
    public TopicDto getTopicByIdOrNull(@PathVariable Long topicId) {
        return topicService.getTopicByIdOrNull(topicId);
    }

    @GetMapping("/topic")
    public List<TopicDto> findTopicsBySkill(@RequestParam Long skillId,
                                            @RequestParam(required = false) Integer page,
                                            @RequestParam(required = false) Integer rowCount) {
        return topicService.findTopicsBySkillId(skillId, page, rowCount);
    }

}
