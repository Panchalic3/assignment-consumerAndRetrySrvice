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
import org.springframework.web.client.RestTemplate;

import static com.assignment.consumer.util.Constants.TARGET_URL;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiverApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ReceiverApiClient receiverApiClient;

    @Test
    void shouldCallReceiverApiSuccessfully() {

        EventPayload payload = new EventPayload();
        payload.setEventId("123");

        when(restTemplate.postForObject(anyString(), any(), eq(String.class)))
                .thenReturn("success");

        receiverApiClient.sendToReceiver(payload);

        verify(restTemplate).postForObject(
                eq(TARGET_URL),
                eq(payload),
                eq(String.class)
        );
    }
}