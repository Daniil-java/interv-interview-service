package com.kuklin.interview_service.models;

import com.kuklin.interview_service.entities.Skill;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class SkillDto {
    private Long id;
    private String name;
    private String category;
    private Set<TopicDto> topics;

    public static SkillDto convertToDto(Skill skill) {
        return new SkillDto()
                .setId(skill.getId())
                .setCategory(skill.getCategory())
                .setName(skill.getName())
                ;
    }

    public static List<SkillDto> convertToDtoList(List<Skill> skillList) {
        return skillList.stream()
                .map(SkillDto::convertToDto)
                .toList();
    }
}
