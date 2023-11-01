package com.example.demo.domain.repositories;
import com.example.demo.domain.repositories.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * 배송 관련 이벤트를 도메인 인터페이스화 시켰습니다.
 */

@Getter
@Setter
public class DeliveryAction extends AbstractEvent {

    private Long id;
    private String orderId;
}