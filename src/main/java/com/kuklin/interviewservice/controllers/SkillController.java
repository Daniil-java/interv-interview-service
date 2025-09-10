package com.kuklin.interviewservice.controllers;

import com.kuklin.interviewservice.models.SkillDto;
import com.kuklin.interviewservice.services.SkillService;
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
