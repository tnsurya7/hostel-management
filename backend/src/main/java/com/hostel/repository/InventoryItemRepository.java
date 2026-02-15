package com.hostel.repository;

import com.hostel.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByItemCode(String itemCode);
    List<InventoryItem> findByCategory(String category);
    List<InventoryItem> findByStatus(String status);
    List<InventoryItem> findByCondition(String condition);
    List<InventoryItem> findByLocation(String location);
    boolean existsByItemCode(String itemCode);
}
