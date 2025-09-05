package com.kuklin.interview_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuklin.interview_service.entities.Vacancy;
import com.kuklin.interview_service.integrations.AiConversationFeignClient;
import com.kuklin.interview_service.models.SkillDto;
import com.kuklin.interview_service.models.VacancyDto;
import com.kuklin.interview_service.repositories.VacancyRepository;
import com.kuklin.interview_service.sharedlibrary.ChatModel;
import com.kuklin.interview_service.sharedlibrary.MessageRequestDto;
import com.kuklin.interview_service.sharedlibrary.exceptions.ErrorResponseException;
import com.kuklin.interview_service.sharedlibrary.exceptions.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public VacancyDto createVacancyName(Long userId, String title) {
        //Формирование сообщения для сервиса общения с ИИ
        MessageRequestDto messageRequestDto = new MessageRequestDto()
                .setContent(String.format(REQUEST, title))
                .setModel(ChatModel.GPT4O)
                .setUserId(userId)
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
            throw new ErrorResponseException(ErrorStatus.AI_RESPONSE_DESERIALIZATION);
        }

        Vacancy vacancy = new Vacancy()
                .setTitle(title)
                .setUserId(userId);
        vacancy = vacancyRepository.save(vacancy);

        skillService.createNewSkillsOrGetExists(skillDtos, vacancy, userId);

        return VacancyDto.convertToDto(vacancy);
    }

    public List<VacancyDto> getVacanciesByUser(Long userId, Integer page, Integer rowCount) {
        if (page != null && rowCount != null) {
            var paging = PageRequest.of(page, rowCount, Sort.by("id"));
            return VacancyDto.convertToDtoList(vacancyRepository.findAllByUserId(userId, paging));
        }
        return VacancyDto.convertToDtoList(vacancyRepository.findAllByUserId(userId));
    }

    public VacancyDto getVacancyById(Long vacancyId) {
        return VacancyDto.convertToDto(vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new ErrorResponseException(ErrorStatus.VACANCY_NOT_FOUND))
        );
    }
}
