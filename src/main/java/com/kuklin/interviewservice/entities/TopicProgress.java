package com.kuklin.interviewservice.entities;

import com.kuklin.sharedlibrary.TopicProgressDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "topic_progress")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TopicProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(name = "topic_id", nullable = false)
    private Long topicId;

    private Integer confidenceLevel;

    private OffsetDateTime lastReviewed;

    private Boolean isWeakArea;

    private OffsetDateTime updated;

    private OffsetDateTime created;

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
                .map(TopicProgress::convertToDto)
                .toList();
    }
}
