package com.example.demo.domain.repositories;

import com.example.demo.domain.models.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long>{


}
