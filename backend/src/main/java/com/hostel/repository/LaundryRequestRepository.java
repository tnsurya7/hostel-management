package com.hostel.repository;

import com.hostel.model.LaundryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaundryRequestRepository extends JpaRepository<LaundryRequest, Long> {
    List<LaundryRequest> findByStudentId(Long studentId);
    List<LaundryRequest> findByStatus(String status);
    List<LaundryRequest> findByPaymentStatus(String paymentStatus);
    List<LaundryRequest> findByServiceType(String serviceType);
}
