package com.kuklin.interviewservice.services;

import com.kuklin.interviewservice.entities.Skill;
import com.kuklin.interviewservice.entities.Topic;
import com.kuklin.interviewservice.repositories.TopicRepository;
import com.kuklin.sharedlibrary.TopicDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicService {
    private final TopicRepository topicRepository;
    private final TopicProgressService topicProgressService;

    @Transactional
    public Set<Topic> createNewTopics(Set<TopicDto> topicDtos, Skill skill, Long userId) {
        //Определиться как создать связь ManyToMany, и как находить уже существующеие
        Set<Topic> topicHashSet = topicDtos.stream()
                .map(dto -> new Topic()
                        .setName(dto.getName())
                        .setSkill(skill))
                .collect(Collectors.toSet());

        for (Topic topic: topicHashSet) {
            topic = topicRepository.save(topic);
            topicProgressService.createProgress(userId, topic.getId());
        }
        return topicHashSet;
    }

    public TopicDto getTopicByIdOrNull(Long topicId) {
        return Topic.convertToDto(topicRepository.findById(topicId).orElse(null));
    }

    public List<TopicDto> findTopicsBySkillId(Long skillId, Integer page, Integer rowCount) {
        List<Topic> result = new ArrayList<>();

        if (page != null && rowCount != null) {
            var paging = PageRequest.of(page, rowCount, Sort.by("id"));
            result.addAll(topicRepository.findAllBySkill_Id(skillId, paging));
        } else {
            result.addAll(topicRepository.findAllBySkill_Id(skillId));
        }

        return Topic.convertToDtoList(result);

    }

    public List<Topic> findTopicsBySkill(Skill skill) {
        return topicRepository.findAllBySkill(skill);
    }

    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }
}
