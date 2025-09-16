package com.kuklin.interviewservice.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InterviewUserInfoDto {
    private Long id;

    private Long userId;
    private String jobTitle;
    private String properties;
}
