package com.kuklin.interviewservice.repositories;

import com.kuklin.interviewservice.entities.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    Optional<Interview> findInterviewByConversationId(Long conversationId);

    @Query("SELECT i FROM Interview i WHERE i.userId = :userId ORDER BY i.created DESC")
    List<Interview> findAllByUserIdOrderByCreatedDesc(@Param("userId") Long userId);

}
