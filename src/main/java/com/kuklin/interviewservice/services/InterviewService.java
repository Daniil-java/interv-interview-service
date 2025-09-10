package com.kuklin.interviewservice.services;

import com.kuklin.interviewservice.entities.Interview;
import com.kuklin.interviewservice.integrations.AiConversationFeignClient;
import com.kuklin.interviewservice.integrations.UserServiceFeignClient;
import com.kuklin.interviewservice.models.InterviewDto;
import com.kuklin.interviewservice.repositories.InterviewRepository;
import com.kuklin.interviewservice.sharedlibrary.ConversationDto;
import com.kuklin.interviewservice.sharedlibrary.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final AiConversationFeignClient aiConversationFC;
    private final UserServiceFeignClient userServiceFeignClient;

    public InterviewDto createInterview(Long conversationId, Long userId) {
        UserDto userDto = userServiceFeignClient.getUserById(userId);
        ConversationDto conversationDto =
                aiConversationFC.getConversationDtoByIdOrGetNull(conversationId);

        if (conversationDto == null) {
            log.error("Failed to save conversation!");
            return null;
        }
        Interview interview = new Interview()
                .setConversationId(conversationId)
                .setJobTitle(userDto.getJobTitle())
                .setProperties(userDto.getProperties())
                .setUserId(userDto.getId());

        return InterviewDto.convertToDto(interviewRepository.save(interview));
    }

    public InterviewDto setResultOrNull(Long userId, Long conversationId, String response) {
        Interview interview = interviewRepository
                .findInterviewByConversationId(conversationId).orElse(null);

        if (interview == null) {
            log.error("Failed to save conversation!");
            return null;
        }

        return InterviewDto.convertToDto(interviewRepository.save(
                interview
                        .setResult(response)
                        .setUserId(userId)
        ));
    }

    public List<InterviewDto> getLatestResultList(Long userId) {
        return interviewRepository
                .findAllByUserIdOrderByCreatedDesc(userId)
                .stream()
                .map(InterviewDto::convertToDto)
                .collect(Collectors.toList());
    }
}
