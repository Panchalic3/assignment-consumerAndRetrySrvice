package com.assignment.consumer.service;

import com.assignment.consumer.model.EventPayload;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.assignment.consumer.util.Constants.MSG_SUCCESSFULLY_SENT_TO_RECEIVER_LOG;
import static com.assignment.consumer.util.Constants.TARGET_URL;

@Service
@AllArgsConstructor
@Slf4j
public class ReceiverApiClient {


    private final RestTemplate restTemplate;

    /**
     * Calls receiver service API.
     *
     * @param payloadParam event data
     */
    public void sendToReceiver(EventPayload payloadParam) {

        this.restTemplate.postForObject(TARGET_URL, payloadParam, String.class);
        log.info(MSG_SUCCESSFULLY_SENT_TO_RECEIVER_LOG, payloadParam.getEventId());

    }
}