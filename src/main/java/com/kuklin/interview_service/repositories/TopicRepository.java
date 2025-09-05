package com.kuklin.interview_service.repositories;

import com.kuklin.interview_service.entities.Skill;
import com.kuklin.interview_service.entities.Topic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findAllBySkill(Skill skill, Pageable pageable);
    List<Topic> findAllBySkill_Id(Long skillId, Pageable pageable);
    List<Topic> findAllBySkill(Skill skill);
    List<Topic> findAllBySkill_Id(Long skillId);
}
