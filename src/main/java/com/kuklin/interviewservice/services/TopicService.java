package com.kuklin.interviewservice.services;

import com.kuklin.interviewservice.entities.Skill;
import com.kuklin.interviewservice.entities.Topic;
import com.kuklin.interviewservice.models.TopicDto;
import com.kuklin.interviewservice.repositories.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return TopicDto.convertToDto(topicRepository.findById(topicId).orElse(null));
    }

    public List<TopicDto> findTopicsBySkillId(Long skillId, Integer page, Integer rowCount) {
        if (page != null && rowCount != null) {
            var paging = PageRequest.of(page, rowCount, Sort.by("id"));
            return TopicDto.convertToDtoList(
                    topicRepository.findAllBySkill_Id(skillId, paging)
            );
        }
        return TopicDto.convertToDtoList(
                topicRepository.findAllBySkill_Id(skillId)
        );
    }

    public List<Topic> findTopicsBySkill(Skill skill) {
        return topicRepository.findAllBySkill(skill);
    }

    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }
}
