package com.kuklin.interviewservice.entities;

import com.kuklin.sharedlibrary.SkillDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private OffsetDateTime updated;

    private OffsetDateTime created;

    @ManyToMany
    @JoinTable(
            name = "vacancy_skill",
            joinColumns = @JoinColumn(name = "skill_id"),
            inverseJoinColumns = @JoinColumn(name = "vacancy_id")
    )
    private Set<Vacancy> vacancies;

    public static Skill dtoToEntity(SkillDto skillDto) {
        return new Skill()
                .setCategory(skillDto.getCategory())
                .setName(skillDto.getName());
    }

    public static List<Skill> listToEntity(List<SkillDto> skillDtoList) {
        return skillDtoList.stream()
                .map(Skill::dtoToEntity)
                .collect(Collectors.toList());
    }

    public static SkillDto convertToDto(Skill skill) {
        return new SkillDto()
                .setId(skill.getId())
                .setCategory(skill.getCategory())
                .setName(skill.getName())
                ;
    }

    public static List<SkillDto> convertToDtoList(List<Skill> skillList) {
        return skillList.stream()
                .map(Skill::convertToDto)
                .toList();
    }
}
