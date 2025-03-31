package com.jambit.application.command.handler;

import com.jambit.application.command.CreateFeedbackTargetCommand;
import com.jambit.application.mapper.FeedbackTargetMapper;
import com.jambit.application.service.validation.ModelValidator;
import com.jambit.application.util.NullCheckUtils;
import com.jambit.domain.common.exception.RecordPersistenceException;
import com.jambit.domain.feedback.FeedbackTarget;
import com.jambit.domain.repository.feedback.target.FeedbackTargetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tigran Melkonyan
 * Date: 2/20/25
 * Time: 12:24 PM
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class FeedbackTargetCommandHandler {

    private final FeedbackTargetRepository repository;
    private final FeedbackTargetMapper mapper;

    @Transactional
    public FeedbackTarget handle(final CreateFeedbackTargetCommand command) {
        log.info("Creating feedback target with target type- {} ", command.getTargetType());
        ModelValidator.validate(command);
        if (repository.existsByName(command.getName())) {
            throw new RecordPersistenceException(String
                    .format("Feedback target already exists with name - %s", command.getName()));
        }
        FeedbackTarget feedbackTarget = mapper.createFeedbackTargetCommandToEntity(command);
        FeedbackTarget result = repository.save(feedbackTarget);
        log.info("Successfully created feedback target, result - {}", result);
        return result;
    }

    @Transactional
    public void handle(final UUID id) {
        log.info("Deleting feedback target with id - {}", id);
        NullCheckUtils.checkNullConstraints(List.of("id"), id);
        repository.delete(id);
        log.info("Successfully deleted feedback target with id - {}", id);
    }

}
