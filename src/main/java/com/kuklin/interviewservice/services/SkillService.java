package com.kuklin.interviewservice.services;

import com.kuklin.interviewservice.entities.Skill;
import com.kuklin.interviewservice.entities.Vacancy;
import com.kuklin.interviewservice.models.SkillDto;
import com.kuklin.interviewservice.repositories.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillService {
    private final SkillRepository skillRepository;
    private final TopicService topicService;

    public List<SkillDto> getSkillsById(Long vacancyId) {
        return SkillDto.convertToDtoList(skillRepository.findAllByVacancyId(vacancyId));
    }

    public SkillDto getSkillByIdOrNull(Long skillId) {
        return SkillDto.convertToDto(skillRepository.findById(skillId).orElse(null));
    }

    public List<SkillDto> getSkillsByVacancy(Long vacancyId, Integer page, Integer rowCount) {
        if (page != null && rowCount != null) {
            var paging = PageRequest.of(page, rowCount, Sort.by("id"));
            return SkillDto.convertToDtoList(
                    skillRepository.findAllByVacancyId(vacancyId, paging).getContent()
            );
        }

        return SkillDto.convertToDtoList(skillRepository.findAllByVacancyId(vacancyId));
    }


    public Set<Skill> createNewSkillsOrGetExists(
            Set<SkillDto> skillDtos, Vacancy vacancy, Long userId) {
        Set<Skill> set = new HashSet<>();
        for (SkillDto dto: skillDtos) {
            //Проверка существувования такого навыка
            Optional<Skill> optionalSkill = skillRepository.findByName(dto.getName());
            if (optionalSkill.isPresent()) {
                //Добавление связи ManyToMany
                Skill skill = optionalSkill.get();

                skill.getVacancies().add(vacancy);
                skillRepository.save(skill);
            } else {
                Skill skill = new Skill()
                        .setName(dto.getName())
                        .setCategory(dto.getCategory())
                        .setVacancies(new HashSet<>(List.of(vacancy)));
                skill = skillRepository.save(skill);

                topicService.createNewTopics(dto.getTopics(), skill, userId);

            }
        }
        return set;
    }
}
