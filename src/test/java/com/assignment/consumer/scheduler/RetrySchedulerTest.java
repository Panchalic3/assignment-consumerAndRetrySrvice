package com.assignment.consumer.scheduler;

import com.assignment.consumer.model.EventPayload;
import com.assignment.consumer.model.RetryEvent;
import com.assignment.consumer.repository.RetryEventRepository;
import com.assignment.consumer.service.ReceiverApiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.assignment.consumer.util.Constants.FAILED;
import static com.assignment.consumer.util.Constants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrySchedulerTest {

    @Mock
    private RetryEventRepository retryEventRepository;
    @Mock
    private ReceiverApiClient receiverApiClient;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private RetryScheduler retryScheduler;
    private RetryEvent event;

    @BeforeEach
    void setUp() {
        event = new RetryEvent();
        event.setStatus(FAILED);
        event.setRetryCount(0);

        when(retryEventRepository.findByStatus(FAILED))
                .thenReturn(List.of(event));
    }

    @Test
    void shouldRetryAndMarkSuccess() throws Exception {

        event.setPayload("{\"userName\":\"Panchali\"}");

        EventPayload payload = new EventPayload();
        when(objectMapper.readValue(anyString(), eq(EventPayload.class)))
                .thenReturn(payload);
        retryScheduler.retryFailedMessages();

        verify(receiverApiClient).sendToReceiver(payload);
        verify(retryEventRepository).save(event);

        assertEquals(SUCCESS, event.getStatus());
    }

    @Test
    void shouldIncreaseRetryCount_whenExceptionOccurs() throws Exception {

        event.setPayload("invalid-json");
        event.setRetryCount(1);

        when(objectMapper.readValue(anyString(), eq(EventPayload.class)))
                .thenThrow(new RuntimeException("error"));

        retryScheduler.retryFailedMessages();

        verify(retryEventRepository).save(event);

        assertEquals(2, event.getRetryCount());
    }
}