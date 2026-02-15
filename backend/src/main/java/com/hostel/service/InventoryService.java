package com.hostel.service;

import com.hostel.exception.DuplicateResourceException;
import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.InventoryItem;
import com.hostel.model.ItemAllocation;
import com.hostel.model.Student;
import com.hostel.repository.InventoryItemRepository;
import com.hostel.repository.ItemAllocationRepository;
import com.hostel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {
    
    private final InventoryItemRepository inventoryItemRepository;
    private final ItemAllocationRepository itemAllocationRepository;
    private final StudentRepository studentRepository;
    
    // ============ INVENTORY ITEM OPERATIONS ============
    
    @Transactional
    public InventoryItem createItem(InventoryItem item) {
        if (item.getItemCode() != null && inventoryItemRepository.existsByItemCode(item.getItemCode())) {
            throw new DuplicateResourceException("Item with code " + item.getItemCode() + " already exists");
        }
        
        if (item.getItemCode() == null) {
            item.setItemCode("ITEM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        
        return inventoryItemRepository.save(item);
    }
    
    public List<InventoryItem> getAllItems() {
        return inventoryItemRepository.findAll();
    }
    
    public InventoryItem getItemById(Long id) {
        return inventoryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with id: " + id));
    }
    
    public List<InventoryItem> getItemsByCategory(String category) {
        return inventoryItemRepository.findByCategory(category);
    }
    
    public List<InventoryItem> getItemsByStatus(String status) {
        return inventoryItemRepository.findByStatus(status);
    }
    
    @Transactional
    public InventoryItem updateItem(Long id, InventoryItem itemDetails) {
        InventoryItem item = getItemById(id);
        
        item.setItemName(itemDetails.getItemName());
        item.setCategory(itemDetails.getCategory());
        item.setQuantity(itemDetails.getQuantity());
        item.setUnit(itemDetails.getUnit());
        item.setLocation(itemDetails.getLocation());
        item.setCondition(itemDetails.getCondition());
        item.setStatus(itemDetails.getStatus());
        item.setDescription(itemDetails.getDescription());
        
        return inventoryItemRepository.save(item);
    }
    
    @Transactional
    public void deleteItem(Long id) {
        InventoryItem item = getItemById(id);
        inventoryItemRepository.delete(item);
    }
    
    // ============ ITEM ALLOCATION OPERATIONS ============
    
    @Transactional
    public ItemAllocation allocateItem(ItemAllocation allocation) {
        // Validate student
        Student student = studentRepository.findById(allocation.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + allocation.getStudentId()));
        
        // Validate item
        InventoryItem item = getItemById(allocation.getItemId());
        
        // Check availability
        if (item.getQuantity() < allocation.getQuantity()) {
            throw new IllegalStateException("Insufficient quantity available");
        }
        
        // Update allocation details
        allocation.setStudentName(student.getName());
        allocation.setItemName(item.getItemName());
        allocation.setStatus("ACTIVE");
        if (allocation.getAllocatedDate() == null) {
            allocation.setAllocatedDate(LocalDate.now());
        }
        
        // Update item quantity
        item.setQuantity(item.getQuantity() - allocation.getQuantity());
        if (item.getQuantity() == 0) {
            item.setStatus("ALLOCATED");
        }
        inventoryItemRepository.save(item);
        
        return itemAllocationRepository.save(allocation);
    }
    
    public List<ItemAllocation> getAllAllocations() {
        return itemAllocationRepository.findAll();
    }
    
    public ItemAllocation getAllocationById(Long id) {
        return itemAllocationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item allocation not found with id: " + id));
    }
    
    public List<ItemAllocation> getAllocationsByStudentId(Long studentId) {
        return itemAllocationRepository.findByStudentId(studentId);
    }
    
    public List<ItemAllocation> getAllocationsByItemId(Long itemId) {
        return itemAllocationRepository.findByItemId(itemId);
    }
    
    @Transactional
    public ItemAllocation returnItem(Long allocationId, String condition, Double damageCharge) {
        ItemAllocation allocation = getAllocationById(allocationId);
        
        if (!"ACTIVE".equals(allocation.getStatus())) {
            throw new IllegalStateException("Allocation is not active");
        }
        
        // Update allocation
        allocation.setStatus("RETURNED");
        allocation.setReturnDate(LocalDate.now());
        allocation.setConditionOnReturn(condition);
        allocation.setDamageCharge(damageCharge != null ? damageCharge : 0.0);
        
        // Update item quantity
        InventoryItem item = getItemById(allocation.getItemId());
        item.setQuantity(item.getQuantity() + allocation.getQuantity());
        item.setStatus("AVAILABLE");
        inventoryItemRepository.save(item);
        
        return itemAllocationRepository.save(allocation);
    }
    
    @Transactional
    public void deleteAllocation(Long id) {
        ItemAllocation allocation = getAllocationById(id);
        itemAllocationRepository.delete(allocation);
    }
}
