package com.example.demo.domain.repositories;

import com.example.demo.Application;
import com.example.demo.domain.models.*;
import com.example.demo.infrastructure.adapters.KafkaProcessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.MimeTypeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Kafka Domain Repository에서 Extend하는 공통적인 기능입니다.
 */

public class AbstractEvent {
    private static final Logger logger = LoggerFactory.getLogger(AbstractEvent.class);

    String eventType;
    String timestamp;

    public AbstractEvent(){
        SimpleDateFormat defaultSimpleDateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
        this.timestamp = defaultSimpleDateFormat.format(new Date());
    }

    public String toJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        return json;
    }

    public void publish(){
        this.publish(this.toJson());
    }
    public void publish(String json){
        if( json != null ){
            KafkaProcessor processor = Application.applicationContext.getBean(KafkaProcessor.class);
            MessageChannel outputChannel = processor.outboundTopic();

            outputChannel.send(MessageBuilder
                    .withPayload(json)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                    .build());

        }
    }

    public void publishAfterCommit(){
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {

            @Override
            public void afterCompletion(int status) {
                AbstractEvent.this.publish();
            }
        });
    }


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isThis(String eventType){
        return getEventType().equals(eventType);
    }
}