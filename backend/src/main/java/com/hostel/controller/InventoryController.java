package com.hostel.controller;

import com.hostel.model.InventoryItem;
import com.hostel.model.ItemAllocation;
import com.hostel.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Inventory Management", description = "APIs for managing hostel inventory and item allocations")
public class InventoryController {
    
    private final InventoryService inventoryService;
    
    // ============ INVENTORY ITEM ENDPOINTS ============
    
    @PostMapping("/items")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Create a new inventory item")
    public ResponseEntity<InventoryItem> createItem(@Valid @RequestBody InventoryItem item) {
        InventoryItem createdItem = inventoryService.createItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }
    
    @GetMapping("/items")
    @Operation(summary = "Get all inventory items")
    public ResponseEntity<List<InventoryItem>> getAllItems() {
        List<InventoryItem> items = inventoryService.getAllItems();
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/items/{id}")
    @Operation(summary = "Get inventory item by ID")
    public ResponseEntity<InventoryItem> getItemById(@PathVariable Long id) {
        InventoryItem item = inventoryService.getItemById(id);
        return ResponseEntity.ok(item);
    }
    
    @GetMapping("/items/category/{category}")
    @Operation(summary = "Get items by category")
    public ResponseEntity<List<InventoryItem>> getItemsByCategory(@PathVariable String category) {
        List<InventoryItem> items = inventoryService.getItemsByCategory(category);
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/items/status/{status}")
    @Operation(summary = "Get items by status")
    public ResponseEntity<List<InventoryItem>> getItemsByStatus(@PathVariable String status) {
        List<InventoryItem> items = inventoryService.getItemsByStatus(status);
        return ResponseEntity.ok(items);
    }
    
    @PutMapping("/items/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Update inventory item")
    public ResponseEntity<InventoryItem> updateItem(@PathVariable Long id, @Valid @RequestBody InventoryItem item) {
        InventoryItem updatedItem = inventoryService.updateItem(id, item);
        return ResponseEntity.ok(updatedItem);
    }
    
    @DeleteMapping("/items/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete inventory item")
    public ResponseEntity<Map<String, String>> deleteItem(@PathVariable Long id) {
        inventoryService.deleteItem(id);
        return ResponseEntity.ok(Map.of("message", "Inventory item deleted successfully"));
    }
    
    // ============ ITEM ALLOCATION ENDPOINTS ============
    
    @PostMapping("/allocations")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Allocate item to student")
    public ResponseEntity<ItemAllocation> allocateItem(@Valid @RequestBody ItemAllocation allocation) {
        ItemAllocation createdAllocation = inventoryService.allocateItem(allocation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAllocation);
    }
    
    @GetMapping("/allocations")
    @Operation(summary = "Get all item allocations")
    public ResponseEntity<List<ItemAllocation>> getAllAllocations() {
        List<ItemAllocation> allocations = inventoryService.getAllAllocations();
        return ResponseEntity.ok(allocations);
    }
    
    @GetMapping("/allocations/{id}")
    @Operation(summary = "Get allocation by ID")
    public ResponseEntity<ItemAllocation> getAllocationById(@PathVariable Long id) {
        ItemAllocation allocation = inventoryService.getAllocationById(id);
        return ResponseEntity.ok(allocation);
    }
    
    @GetMapping("/allocations/student/{studentId}")
    @Operation(summary = "Get allocations by student ID")
    public ResponseEntity<List<ItemAllocation>> getAllocationsByStudentId(@PathVariable Long studentId) {
        List<ItemAllocation> allocations = inventoryService.getAllocationsByStudentId(studentId);
        return ResponseEntity.ok(allocations);
    }
    
    @GetMapping("/allocations/item/{itemId}")
    @Operation(summary = "Get allocations by item ID")
    public ResponseEntity<List<ItemAllocation>> getAllocationsByItemId(@PathVariable Long itemId) {
        List<ItemAllocation> allocations = inventoryService.getAllocationsByItemId(itemId);
        return ResponseEntity.ok(allocations);
    }
    
    @PatchMapping("/allocations/{id}/return")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Return allocated item")
    public ResponseEntity<ItemAllocation> returnItem(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        String condition = (String) request.get("condition");
        Double damageCharge = request.get("damageCharge") != null ? 
                Double.valueOf(request.get("damageCharge").toString()) : 0.0;
        ItemAllocation allocation = inventoryService.returnItem(id, condition, damageCharge);
        return ResponseEntity.ok(allocation);
    }
    
    @DeleteMapping("/allocations/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete item allocation")
    public ResponseEntity<Map<String, String>> deleteAllocation(@PathVariable Long id) {
        inventoryService.deleteAllocation(id);
        return ResponseEntity.ok(Map.of("message", "Item allocation deleted successfully"));
    }
}
