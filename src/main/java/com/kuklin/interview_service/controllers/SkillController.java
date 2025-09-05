package com.kuklin.interview_service.controllers;

import com.kuklin.interview_service.models.SkillDto;
import com.kuklin.interview_service.services.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping("/skill/{skillId}")
    public SkillDto getSkillByIdOrNull(@PathVariable Long skillId) {
        return skillService.getSkillByIdOrNull(skillId);
    }

    @GetMapping("/skill")
    public List<SkillDto> getPagingSkillsByVacancyId(
            @RequestParam Long vacancyId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rowCount
    ) {
        return skillService.getSkillsByVacancy(vacancyId, page, rowCount);
    }

}
