package com.kuklin.interviewservice.controllers;

import com.kuklin.interviewservice.services.VacancyService;
import com.kuklin.sharedlibrary.VacancyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacancy")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @PostMapping
    public VacancyDto createVacancyName(@RequestBody VacancyDto vacancyDto) {
        return vacancyService.createVacancyName(vacancyDto);
    }

    @GetMapping
    public List<VacancyDto> getVacanciesByUser(@RequestParam Long userId,
                                               @RequestParam(required = false) Integer page,
                                               @RequestParam(required = false) Integer rowCount) {
        return vacancyService.getVacanciesByUser(userId, page, rowCount);
    }

    @GetMapping("/{vacancyId}")
    public VacancyDto getVacancyById(@PathVariable Long vacancyId) {
        return vacancyService.getVacancyById(vacancyId);
    }
}
