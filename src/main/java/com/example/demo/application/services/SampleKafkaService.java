package com.example.demo.application.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.domain.models.*;
import com.example.demo.domain.repositories.*;

import lombok.RequiredArgsConstructor;

/* 
 * Controller에서 수신한 Message를 Entity에 매핑하고, 기타 Usecase와 Business Logic을 정의합니다.
*/

@RequiredArgsConstructor
@Service
public class SampleKafkaService {
    private static final Logger logger = LoggerFactory.getLogger(SampleKafkaService.class);
    private final PaymentRepository paymentRepository;

    /* 여러 Topic에 대응하는 Service를 정의할 수 있는 구조입니다 */
    
    public void paymentAction(PaymentAction paymentAction) {
        logger.info ("##### paymentAction called #####");

        if (paymentAction.isThis("PaymentCancelled")) {
            logger.info("##### listener cancel Payment : " + paymentAction.toJson());

            Payment payment = new Payment();

            payment.setAction("cancel");
            payment.setOrderId(paymentAction.getOrderId());

            paymentRepository.save(payment);
        }

        if (paymentAction.isThis("Paid")) {
            logger.info("##### listener paid Payment : " + paymentAction.toJson());

            Payment payment = new Payment();

            payment.setAction("pay");
            payment.setOrderId(paymentAction.getOrderId());

            paymentRepository.save(payment);
        }
    }

    public void deliveryAction(DeliveryAction deliveryAction) {
        logger.info ("##### deliveryAction called #####");

        if (deliveryAction.isThis("DeliveryCancelled")) {
            logger.info("##### listener cancel Delivery : " + deliveryAction.toJson());
        }

        if (deliveryAction.isThis("DeliveryOrdered")) {
            logger.info("##### listener orderd Delivery : " + deliveryAction.toJson());
        }
    }
}