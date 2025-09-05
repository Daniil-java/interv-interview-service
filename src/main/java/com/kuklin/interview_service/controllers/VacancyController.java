package com.kuklin.interview_service.controllers;

import com.kuklin.interview_service.models.VacancyDto;
import com.kuklin.interview_service.services.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @PostMapping("/vacancy")
    public VacancyDto createVacancyName(@RequestBody VacancyDto vacancyDto) {
        return vacancyService.createVacancyName(vacancyDto.getUserId(), vacancyDto.getTitle());
    }

    @GetMapping("/vacancy")
    public List<VacancyDto> getVacanciesByUser(@RequestParam Long userId,
                                               @RequestParam(required = false) Integer page,
                                               @RequestParam(required = false) Integer rowCount) {
        return vacancyService.getVacanciesByUser(userId, page, rowCount);
    }

    @GetMapping("/vacancy/{vacancyId}")
    public VacancyDto getVacancyById(@PathVariable Long vacancyId) {
        return vacancyService.getVacancyById(vacancyId);
    }
}
