package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.example.demo.application.services.*;
import com.example.demo.domain.repositories.*;
import com.example.demo.infrastructure.adapters.KafkaProcessor;

/*
 * Kafka Consuming을 위한 전용 Controller 입니다.
 */

@Controller
public class SampleKafkaController {
    private static final Logger logger = LoggerFactory.getLogger(SampleKafkaController.class);

    @Autowired
    SampleKafkaService sampleKafkaService;

    /* infrastructure.adapter.KafkaProcessor에서 정의한 TOPIC에 맞추어 Service를 매핑합니다. */

    @StreamListener(KafkaProcessor.INPUT_PAY)
    public void wheneverPayment(@Payload PaymentAction paymentAction) {
        sampleKafkaService.paymentAction(paymentAction);
    }

    @StreamListener(KafkaProcessor.INPUT_DELIVERY)
    public void wheneverDelivery(@Payload DeliveryAction deliveryAction) {
        sampleKafkaService.deliveryAction(deliveryAction);
    }
}
