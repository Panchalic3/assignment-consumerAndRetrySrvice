package com.assignment.consumer.scheduler;

import com.assignment.consumer.model.EventPayload;
import com.assignment.consumer.model.RetryEvent;
import com.assignment.consumer.repository.RetryEventRepository;
import com.assignment.consumer.service.ReceiverApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.assignment.consumer.util.Constants.*;

@Component
@AllArgsConstructor
@Slf4j
public class RetryScheduler {
    private final RetryEventRepository retryEventRepository;
    private final ReceiverApiClient receiverApiClient;
    private final ObjectMapper objectMapper;

    /**
     * Retries failed events by fetching them from the database,
     * calling the receiver API, and updating their status.
     *
     * @throws RuntimeException if retry processing fails
     */

    @Scheduled(fixedDelay = 10000)
    public void retryFailedMessages() {

        List<RetryEvent> failedEvents = this.retryEventRepository.findByStatus(FAILED);

        for (RetryEvent event : failedEvents) {
            try {
                EventPayload payload = this.objectMapper.readValue(event.getPayload(), EventPayload.class);

                this.receiverApiClient.sendToReceiver(payload);
                event.setStatus(SUCCESS);

            } catch (Exception exception) {
                log.error(RETRY_FAILURE_LOG, event.getRetryCount());
                event.setRetryCount(event.getRetryCount() + 1);
            }

            this.retryEventRepository.save(event);
        }
    }
}
