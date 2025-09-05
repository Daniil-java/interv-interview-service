package com.kuklin.interview_service.models;

import com.kuklin.interview_service.entities.Topic;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TopicDto {
    private Long id;
    private String name;

    public static TopicDto convertToDto(Topic topic) {
        return new TopicDto()
                .setId(topic.getId())
                .setName(topic.getName())
                ;
    }

    public static List<TopicDto> convertToDtoList(List<Topic> topicList) {
        return topicList.stream()
                .map(TopicDto::convertToDto)
                .toList();
    }
}
