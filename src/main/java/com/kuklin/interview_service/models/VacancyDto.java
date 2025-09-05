package com.kuklin.interview_service.models;

import com.kuklin.interview_service.entities.Vacancy;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class VacancyDto {
    private Long id;
    private String title;
    private Long userId;

    public static VacancyDto convertToDto(Vacancy vacancy) {
        return new VacancyDto()
                .setId(vacancy.getId())
                .setUserId(vacancy.getUserId())
                .setTitle(vacancy.getTitle());
    }

    public static List<VacancyDto> convertToDtoList(List<Vacancy> vacancyList) {
        return vacancyList.stream()
                .map(VacancyDto::convertToDto)
                .toList();
    }
}
