package com.kuklin.interviewservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuklin.interviewservice.entities.Skill;
import com.kuklin.interviewservice.entities.Vacancy;
import com.kuklin.interviewservice.integrations.AiConversationFeignClient;
import com.kuklin.interviewservice.repositories.VacancyRepository;
import com.kuklin.sharedlibrary.ChatModel;
import com.kuklin.sharedlibrary.MessageRequestDto;
import com.kuklin.sharedlibrary.SkillDto;
import com.kuklin.sharedlibrary.VacancyDto;
import com.kuklin.sharedlibrary.exceptions.ErrorResponseException;
import com.kuklin.sharedlibrary.exceptions.ErrorStatus;
import com.kuklin.sharedlibrary.exceptions.ServiceOrigin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final SkillService skillService;
    private final AiConversationFeignClient aiConversationFeignClient;
    private static final String REQUEST = """
            Я отправлю тебе название должности или вакансии.
            Твоя задача разложить вакансию на навыки.
            А навыки на темы, изучив которые можно научиться навыку.
            Пиши только hardskills, не надо общих слов.
            Не пиши ничего лишнего, никаких объяснений. 
            В качестве ответа, ты должен использовать ТОЛЬКО JSON. 
            Отправь только чистый JSON, без форматирования или обрамляющих блоков. 
            Cообщение следующего формата
            [\n
            {\n
            \"name\": \"Java\", \n
            \"category\":\"Programming Language\", \n
            \"topics\":[{\n\"name\":\"Spring Framework\"}, {\n\"name\":\"Java Core\"}]
            },\n
            ...\n
            ]\n
            Название вакансии или должности: %s
            """;

    public VacancyDto createVacancyName(VacancyDto vacancyDto) {
        //Формирование сообщения для сервиса общения с ИИ
        MessageRequestDto messageRequestDto = new MessageRequestDto()
                .setContent(String.format(REQUEST, vacancyDto.getTitle()))
                .setModel(ChatModel.GPT4O)
                .setUserId(vacancyDto.getUserId())
                ;
        String jsonAnswer =
                aiConversationFeignClient.sendServiceMessage(messageRequestDto);

        //Десериализация объекта
        Set<SkillDto> skillDtos = null;
        try {
            skillDtos = new ObjectMapper().readValue(
                    jsonAnswer,
                    new TypeReference<Set<SkillDto>>() {}
            );
        } catch (JsonProcessingException e) {
            log.error("Ошибка десериализации: ", e);
            throw new ErrorResponseException(
                    ErrorStatus.AI_RESPONSE_DESERIALIZATION,
                    ServiceOrigin.INTERVIEW_SERVICE
            );
        }

        Vacancy vacancy = new Vacancy()
                .setTitle(vacancyDto.getTitle())
                .setUserId(vacancyDto.getUserId());
        vacancy = vacancyRepository.save(vacancy);

        skillService.createNewSkillsOrGetExists(skillDtos, vacancy, vacancy.getUserId());

        return Vacancy.convertToDto(vacancy);
    }

    public List<VacancyDto> getVacanciesByUser(Long userId, Integer page, Integer rowCount) {
        List<Vacancy> result = new ArrayList<>();

        if (page != null && rowCount != null) {
            var paging = PageRequest.of(page, rowCount, Sort.by("id"));
            result.addAll(vacancyRepository.findAllByUserId(userId, paging));
        } else {
            result.addAll(vacancyRepository.findAllByUserId(userId));
        }

        return Vacancy.convertToDtoList(result);
    }

    public VacancyDto getVacancyById(Long vacancyId) {
        return Vacancy.convertToDto(vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new ErrorResponseException(
                        ErrorStatus.VACANCY_NOT_FOUND,
                        ServiceOrigin.INTERVIEW_SERVICE))
        );
    }
}
