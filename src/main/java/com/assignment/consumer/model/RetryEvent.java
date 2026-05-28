package com.assignment.consumer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "retry_events")
public class RetryEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String payload;
    private int retryCount;
    private String status;

    public RetryEvent(String payloadParam, int retryCountParam, String statusParam) {
        this.payload = payloadParam;
        this.retryCount = retryCountParam;
        this.status = statusParam;
    }

}

