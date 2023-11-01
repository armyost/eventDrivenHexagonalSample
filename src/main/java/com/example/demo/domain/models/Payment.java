package com.example.demo.domain.models;
import com.example.demo.domain.repositories.*;

import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Payment의 Entity입니다. 
 * Payment Data에 대한 작업 전에 Kafka-Publish가 필요한 경우 PrePersist에 추가 정의할 수 있습니다.
 */

@Getter
@Setter
@Entity
@Table(name="Payment_table")
public class Payment {
    
    private static final Logger logger = LoggerFactory.getLogger(Payment.class);

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String orderId;
    private Double price;
    private String action;
    private String address;
    private String item;

    @PrePersist
    public void onPrePersist(){

        /* Publish 이벤트가 필요한 경우만 추가하면 됩니다 */
        if("cancel".equals(action)){
            DeliveryAction deliveryAction = new DeliveryAction();
            BeanUtils.copyProperties(this, deliveryAction);
            deliveryAction.setOrderId(orderId);
            deliveryAction.setEventType("DeliveryCancelled");
            
            //바로 이벤트를 보내버리면 주문정보가 커밋되기도 전에 배송발송됨 이벤트가 발송되어 주문테이블의 상태가 바뀌지 않을 수 있다.
            // TX 리스너는 커밋이 완료된 후에 이벤트를 발생하도록 만들어준다.
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter(){
                @Override
                public void beforeCommit(boolean readOnly) {
                    deliveryAction.publish();
                }
            });
        }
        if("pay".equals(action)){
            /*
             * 위와 같이 정상 지불되었을때 Delivery를 시작하라는 이벤트를 추가하면 됩니다.
             */
        }
    }
}