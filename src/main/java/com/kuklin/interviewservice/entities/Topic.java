package com.kuklin.interviewservice.entities;

import com.kuklin.sharedlibrary.TopicDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "topics")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private OffsetDateTime updated;

    private OffsetDateTime created;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    public static TopicDto convertToDto(Topic topic) {
        return new TopicDto()
                .setId(topic.getId())
                .setName(topic.getName())
                ;
    }

    public static List<TopicDto> convertToDtoList(List<Topic> topicList) {
        return topicList.stream()
                .map(Topic::convertToDto)
                .toList();
    }
}
