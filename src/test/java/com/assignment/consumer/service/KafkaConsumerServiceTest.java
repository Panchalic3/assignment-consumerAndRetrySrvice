package com.assignment.consumer.service;

import com.assignment.consumer.model.EventPayload;
import com.assignment.consumer.model.RetryEvent;
import com.assignment.consumer.repository.RetryEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ReceiverApiClient apiClientService;

    @Mock
    private RetryEventRepository retryEventRepository;

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @Test
    void shouldConsumeAndSendToReceiverSuccessfully() throws Exception {

        String message = "{\"eventId\":\"123\"}";
        EventPayload payload = new EventPayload();
        payload.setEventId("123");

        when(objectMapper.readValue(message, EventPayload.class))
                .thenReturn(payload);

        kafkaConsumerService.consume(message);

        verify(apiClientService).sendToReceiver(payload);
        verify(retryEventRepository, never()).save(any());
    }

    @Test
    void shouldSaveToRetry_whenExceptionOccurs() throws Exception {

        String message = "invalid-json";

        when(objectMapper.readValue(message, EventPayload.class))
                .thenThrow(new RuntimeException("error"));

        kafkaConsumerService.consume(message);

        verify(retryEventRepository).save(any(RetryEvent.class));
        verify(apiClientService, never()).sendToReceiver(any());
    }
}
