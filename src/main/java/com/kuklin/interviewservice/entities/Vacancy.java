package com.kuklin.interviewservice.entities;

import com.kuklin.sharedlibrary.VacancyDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "vacancy")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String sourceUrl;

    private Long userId;

    private OffsetDateTime updated;

    private OffsetDateTime created;

    public static VacancyDto convertToDto(Vacancy vacancy) {
        return new VacancyDto()
                .setId(vacancy.getId())
                .setUserId(vacancy.getUserId())
                .setTitle(vacancy.getTitle());
    }

    public static List<VacancyDto> convertToDtoList(List<Vacancy> vacancyList) {
        return vacancyList.stream()
                .map(Vacancy::convertToDto)
                .toList();
    }
}
