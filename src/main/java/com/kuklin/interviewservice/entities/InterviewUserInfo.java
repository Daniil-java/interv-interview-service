package com.kuklin.interviewservice.entities;

import com.kuklin.interviewservice.model.InterviewUserInfoDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity
@Table(name = "interview_user_info")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class InterviewUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String jobTitle;
    private String properties;

    public static InterviewUserInfo dtoToEntity(InterviewUserInfoDto dto) {
        return new InterviewUserInfo()
                .setProperties(dto.getProperties())
                .setJobTitle(dto.getJobTitle())
                .setUserId(dto.getUserId())
                .setId(dto.getId())
                ;
    }

    public static InterviewUserInfoDto convertToDto(InterviewUserInfo entity) {
        return new InterviewUserInfoDto()
                .setProperties(entity.getProperties())
                .setJobTitle(entity.getJobTitle())
                .setUserId(entity.userId)
                .setId(entity.getId())
                ;
    }
}
