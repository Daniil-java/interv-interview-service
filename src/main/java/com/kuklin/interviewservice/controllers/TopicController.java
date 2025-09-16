package com.kuklin.interviewservice.controllers;

import com.kuklin.interviewservice.services.TopicService;
import com.kuklin.sharedlibrary.TopicDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @GetMapping("/{topicId}")
    public TopicDto getTopicByIdOrNull(@PathVariable Long topicId) {
        return topicService.getTopicByIdOrNull(topicId);
    }

    @GetMapping
    public List<TopicDto> findTopicsBySkill(@RequestParam Long skillId,
                                            @RequestParam(required = false) Integer page,
                                            @RequestParam(required = false) Integer rowCount) {
        return topicService.findTopicsBySkillId(skillId, page, rowCount);
    }

}
