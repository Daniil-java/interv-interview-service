package com.kuklin.interviewservice.services;

import com.kuklin.interviewservice.entities.TopicProgress;
import com.kuklin.interviewservice.models.TopicProgressDto;
import com.kuklin.interviewservice.repositories.TopicProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicProgressService {
    private final TopicProgressRepository topicProgressRepository;

    //Создание сущности прогресса топика, при создании топика
    public TopicProgress createProgress(Long userId, Long topicId) {
        TopicProgress topicProgress = new TopicProgress()
                .setTopicId(topicId)
                .setUserId(userId)
                .setConfidenceLevel(0)
                .setIsWeakArea(true)
                ;

        return topicProgressRepository.save(topicProgress);
    }

    //Изменение состояния прогресса. Прогресс определяется ИИ.
    public TopicProgressDto updateProgress(Long userId, Long topicId, int level) {
        //Уровень, при котором знание топика считается плохим
        int weakAreaLevel = 60;
        Optional<TopicProgress> optionalTopicProgress =
                topicProgressRepository.findByUserIdAndTopicId(userId, topicId);

        TopicProgress topicProgress;
        //Создание топика в случае его отсутствия.
        if (optionalTopicProgress.isEmpty()) {
            topicProgress = createProgress(userId, topicId);
        } else {
            topicProgress = optionalTopicProgress.get();
        }

        return TopicProgressDto.convertToDto(
                topicProgressRepository.save(topicProgress
                    .setConfidenceLevel(level)
                    .setIsWeakArea(level <= weakAreaLevel)
                )
        );
    }

    public List<TopicProgressDto> getTopicProgressByUserId(Long userId) {
        return TopicProgressDto.convertToDtoList(
                topicProgressRepository.findAllByUserId(userId)
        );
    }
}
