package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.openapitools.model.StudentDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final StudentService studentService;

    @KafkaListener(topics = "update_student", groupId = "group_id")
    public void listen(@Payload StudentDTO studentDTO, Acknowledgment acknowledgment) {
        System.out.println("Received message: " + studentDTO);
        studentService.updateStudents(studentDTO.getId(), studentDTO);
        acknowledgment.acknowledge();
    }
}
