package com.kuklin.interviewservice.repositories;

import com.kuklin.interviewservice.entities.InterviewUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterviewUserInfoRepository extends JpaRepository<InterviewUserInfo, Long> {
    Optional<InterviewUserInfo> findByUserId(Long userId);
}
