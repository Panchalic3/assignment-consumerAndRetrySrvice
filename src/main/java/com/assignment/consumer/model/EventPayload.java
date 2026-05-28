package com.assignment.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventPayload {
    private String userName;
    private String eventId;
    private String data;
    private LocalDateTime timestamp;
}
