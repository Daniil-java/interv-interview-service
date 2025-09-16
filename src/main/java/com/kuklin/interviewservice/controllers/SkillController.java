package com.kuklin.interviewservice.controllers;

import com.kuklin.interviewservice.services.SkillService;
import com.kuklin.sharedlibrary.SkillDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
@RequiredArgsConstructor
public class SkillController {
    private final SkillService skillService;

    @GetMapping("/{skillId}")
    public SkillDto getSkillByIdOrNull(@PathVariable Long skillId) {
        return skillService.getSkillByIdOrNull(skillId);
    }

    @GetMapping
    public List<SkillDto> getPagingSkillsByVacancyId(
            @RequestParam Long vacancyId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rowCount
    ) {
        return skillService.getSkillsByVacancy(vacancyId, page, rowCount);
    }

}
