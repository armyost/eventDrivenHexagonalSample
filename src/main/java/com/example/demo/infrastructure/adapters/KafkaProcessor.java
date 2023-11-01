package com.example.demo.infrastructure.adapters;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/*
 * 송수신할 Kafka Topic정보를 Application.yml에서 불러옵니다.
 */

public interface KafkaProcessor {

    String INPUT_PAY = "event-pay-in";
    String INPUT_DELIVERY = "event-delivery-in";
    String OUTPUT = "event-out";

    @Input(INPUT_PAY)
    SubscribableChannel inboundTopicPay();

    @Input(INPUT_DELIVERY)
    SubscribableChannel inboundTopicDelivery();

    @Output(OUTPUT)
    MessageChannel outboundTopic();

}