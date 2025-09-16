package com.kuklin.interviewservice.services;

import com.kuklin.interviewservice.entities.Interview;
import com.kuklin.interviewservice.integrations.AiConversationFeignClient;
import com.kuklin.interviewservice.integrations.UserServiceFeignClient;
import com.kuklin.interviewservice.repositories.InterviewRepository;
import com.kuklin.sharedlibrary.ConversationDto;
import com.kuklin.sharedlibrary.InterviewDto;
import com.kuklin.sharedlibrary.InterviewRequest;
import com.kuklin.sharedlibrary.UserDto;
import com.kuklin.sharedlibrary.exceptions.ErrorStatus;
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

    public InterviewDto createInterview(InterviewRequest interviewRequest) {
        UserDto userDto = userServiceFeignClient.getUserById(interviewRequest.getUserId());
        ConversationDto conversationDto =
                aiConversationFC.getConversationDtoByIdOrGetNull(interviewRequest.getConversationId());

        if (conversationDto == null) {
            log.error("Conversation not found!", ErrorStatus.CONVERSATION_NOT_FOUND);
            return null;
        }
        Interview interview = new Interview()
                .setConversationId(interviewRequest.getConversationId())
                .setJobTitle(userDto.getJobTitle())
                .setProperties(userDto.getProperties())
                .setUserId(userDto.getId());

        return Interview.convertToDto(interviewRepository.save(interview));
    }

    public InterviewDto setResultOrNull(InterviewRequest interviewRequest) {
        Interview interview = interviewRepository
                .findInterviewByConversationId(interviewRequest.getConversationId())
                .orElse(null);

        if (interview == null) {
            log.error("Interview not found!", ErrorStatus.INTERVIEW_NOT_FOUND);
            return null;
        }

        return Interview.convertToDto(interviewRepository.save(
                interview
                        .setResult(interviewRequest.getResult())
                        .setUserId(interviewRequest.getUserId())
        ));
    }

    public List<InterviewDto> getLatestResultList(Long userId) {
        return interviewRepository
                .findAllByUserIdOrderByCreatedDesc(userId)
                .stream()
                .map(Interview::convertToDto)
                .collect(Collectors.toList());
    }
}
