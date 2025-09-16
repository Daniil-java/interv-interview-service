package com.kuklin.interviewservice.services;

import com.kuklin.interviewservice.entities.InterviewUserInfo;
import com.kuklin.interviewservice.integrations.AiConversationFeignClient;
import com.kuklin.interviewservice.integrations.UserServiceFeignClient;
import com.kuklin.interviewservice.repositories.InterviewUserInfoRepository;
import com.kuklin.sharedlibrary.MessageRequestDto;
import com.kuklin.sharedlibrary.UserDto;
import com.kuklin.sharedlibrary.exceptions.ErrorResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewUserInfoService {
    private final InterviewUserInfoRepository userInfoRepository;
    private final UserServiceFeignClient userServiceFC;
    private final AiConversationFeignClient aiConversationFC;

    private static final String AI_REQUEST_MESSAGE = "Ты должен ответить на вопрос " +
            "является ли следующая строка должна являться должностью, " +
            "на которую можно провести собеседование. Ответ должен быть однозначным," +
            " не пиши ничего лишнего, только 1 - если является, и 0 - если нет;\n" +
            "Строка: %s";

    public Boolean setJobTitle(Long userId, String jobTitle) {
        InterviewUserInfo userInfo = getOrCreateUserInfo(userId);

        try {
            MessageRequestDto messageRequestDto =
                    MessageRequestDto.getServiceMessage(
                            String.format(AI_REQUEST_MESSAGE, jobTitle));
            messageRequestDto.setUserId(userId);

            String response = aiConversationFC.sendServiceMessage(messageRequestDto);

            if (response.equals("1")) {
                userInfo = userInfoRepository.save(userInfo.setJobTitle(jobTitle));
                return true;
            }
        } catch (ErrorResponseException e) {
            log.error("Ошибка при попытке отправить сервисное сообщение в ИИ-чат!", e);
        }
        return false;
    }

    //Получить юзера или создать нового
    private InterviewUserInfo getOrCreateUserInfo(Long userId) {
        return userInfoRepository
                .findByUserId(userId)
                .orElseGet(() -> createUserInfo(userId));
    }

    private InterviewUserInfo createUserInfo(Long userId) {
        //Проверка существования юзера,
        //user service прокинет ошибку, если не существует
        UserDto userDto = userServiceFC.getUserById(userId);
        return userInfoRepository.save(new InterviewUserInfo().setUserId(userDto.getId()));
    }

}
