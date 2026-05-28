package com.assignment.consumer.service;

import com.assignment.consumer.model.EventPayload;
import com.assignment.consumer.model.RetryEvent;
import com.assignment.consumer.repository.RetryEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.assignment.consumer.util.Constants.*;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaConsumerService {

    private final ObjectMapper objectMapper;
    private final ReceiverApiClient apiClientService;
    private final RetryEventRepository retryEventRepository;


    /**
     * Consumes message from Kafka topic - event-topic
     * Converts it to EventPayload.
     * Forwards it to the receiver service.
     *
     * @param message JSON message received from Kafka
     * @throws RuntimeException if deserialization or API call fails
     */

    @KafkaListener(topics = "event-topic", groupId = "consumer-group")
    public void consume(String message) {
        try {
            EventPayload payload = this.objectMapper.readValue(message, EventPayload.class);
            log.info(MSG_SENDING_TO_RECEIVER_LOG, payload.getEventId());
            this.apiClientService.sendToReceiver(payload);

        } catch (Exception exception) {
            log.error(RETRY_FAILURE_LOG_ADD_RETRY_LOG);
            RetryEvent retryEvent = new RetryEvent(message, 0, FAILED);
            this.retryEventRepository.save(retryEvent);
        }
    }
}
