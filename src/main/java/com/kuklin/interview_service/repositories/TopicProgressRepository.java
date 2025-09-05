package com.kuklin.interview_service.repositories;

import com.kuklin.interview_service.entities.TopicProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicProgressRepository extends JpaRepository<TopicProgress, Long> {

    Optional<TopicProgress> findByUserIdAndTopicId(Long userId, Long topicId);
    List<TopicProgress> findAllByUserId(Long userId);
}
