package com.kuklin.interview_service.models;

import com.kuklin.interview_service.entities.TopicProgress;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class TopicProgressDto {
    private Long id;
    private Long userId;
    private Long topicId;
    private Integer confidenceLevel;
    private OffsetDateTime lastReviewed;
    private Boolean isWeakArea;

    public static TopicProgressDto convertToDto(TopicProgress topicProgress) {
        return new TopicProgressDto()
                .setId(topicProgress.getId())
                .setUserId(topicProgress.getUserId())
                .setTopicId(topicProgress.getTopicId())
                .setConfidenceLevel(topicProgress.getConfidenceLevel())
                .setLastReviewed(topicProgress.getLastReviewed())
                .setIsWeakArea(topicProgress.getIsWeakArea());
    }

    public static List<TopicProgressDto> convertToDtoList(List<TopicProgress> topicProgressList) {
        return topicProgressList.stream()
                .map(TopicProgressDto::convertToDto)
                .toList();
    }

}
