package com.kuklin.interview_service.models;

import com.kuklin.interview_service.entities.Interview;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InterviewDto {
    private Long id;
    private String jobTitle;
    private String result;
    private Long conversationId;
    private Long userId;
    private String properties;

    public static InterviewDto convertToDto(Interview interview) {
        return new InterviewDto()
                .setId(interview.getId())
                .setUserId(interview.getUserId())
                .setJobTitle(interview.getJobTitle())
                .setProperties(interview.getProperties())
                .setResult(interview.getResult())
                .setConversationId(interview.getConversationId())
                ;
    }
}
