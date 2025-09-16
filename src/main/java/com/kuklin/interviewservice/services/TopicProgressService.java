package com.kuklin.interviewservice.services;

import com.kuklin.interviewservice.entities.TopicProgress;
import com.kuklin.interviewservice.repositories.TopicProgressRepository;
import com.kuklin.sharedlibrary.TopicProgressDto;
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
    public TopicProgressDto updateProgress(TopicProgressDto progressDto) {
        //Уровень, при котором знание топика считается плохим
        int weakAreaLevel = 60;
        Optional<TopicProgress> optionalTopicProgress = topicProgressRepository
                .findByUserIdAndTopicId(progressDto.getUserId(), progressDto.getTopicId());

        TopicProgress topicProgress;
        //Создание топика в случае его отсутствия.
        if (optionalTopicProgress.isEmpty()) {
            topicProgress = createProgress(progressDto.getUserId(), progressDto.getTopicId());
        } else {
            topicProgress = optionalTopicProgress.get();
        }

        return TopicProgress.convertToDto(
                topicProgressRepository.save(topicProgress
                    .setConfidenceLevel(progressDto.getConfidenceLevel())
                    .setIsWeakArea(progressDto.getConfidenceLevel() <= weakAreaLevel)
                )
        );
    }

    public List<TopicProgressDto> getTopicProgressByUserId(Long userId) {
        return TopicProgress.convertToDtoList(
                topicProgressRepository.findAllByUserId(userId)
        );
    }
}
