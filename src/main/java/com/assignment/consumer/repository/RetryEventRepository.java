package com.assignment.consumer.repository;

import com.assignment.consumer.model.RetryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RetryEventRepository extends JpaRepository<RetryEvent, Long> {

    List<RetryEvent> findByStatus(String status);
}
