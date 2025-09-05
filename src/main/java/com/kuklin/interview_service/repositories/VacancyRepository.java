package com.kuklin.interview_service.repositories;

import com.kuklin.interview_service.entities.Vacancy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    List<Vacancy> findAllByUserId(Long userId, Pageable pageable);
    List<Vacancy> findAllByUserId(Long userId);
}
