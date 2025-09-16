package com.kuklin.interviewservice.entities;

import com.kuklin.sharedlibrary.InterviewDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "interviews")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTitle;
    private String result;
    @Column(name = "conversation_id")
    private Long conversationId;
    @Column(name = "user_id")
    private Long userId;
    private String properties;
    @UpdateTimestamp
    private LocalDateTime updated;
    @CreationTimestamp
    private LocalDateTime created;

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
