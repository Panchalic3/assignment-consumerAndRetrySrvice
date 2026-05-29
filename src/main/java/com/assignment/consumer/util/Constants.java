package com.assignment.consumer.util;

public class Constants {

    public static final String TARGET_URL = "http://localhost:8082/api/process";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String MSG_SENDING_TO_RECEIVER_LOG = "Sending message to receiver service with event id {}";
    public static final String MSG_SUCCESSFULLY_SENT_TO_RECEIVER_LOG = "Message successfully sent to receiver service with event id {}";
    public static final String RETRY_FAILURE_LOG_ADD_RETRY_LOG = "Failed to deliver message to receiver. Adding to retry.";
    public static final String RETRY_FAILURE_LOG = "Failed to send event to receiver service. RetryCount={}";

}
