package com.hostel.repository;

import com.hostel.model.ItemAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemAllocationRepository extends JpaRepository<ItemAllocation, Long> {
    List<ItemAllocation> findByStudentId(Long studentId);
    List<ItemAllocation> findByItemId(Long itemId);
    List<ItemAllocation> findByStatus(String status);
    List<ItemAllocation> findByStudentIdAndStatus(Long studentId, String status);
    List<ItemAllocation> findByItemIdAndStatus(Long itemId, String status);
}
