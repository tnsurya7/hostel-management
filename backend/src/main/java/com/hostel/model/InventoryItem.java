package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Item name is required")
    @Column(name = "item_name", nullable = false)
    private String itemName;
    
    @Column(name = "item_code", unique = true)
    private String itemCode;
    
    @Column(name = "category")
    private String category; // FURNITURE, ELECTRONICS, BEDDING, KITCHEN, SPORTS, OTHER
    
    @NotNull(message = "Quantity is required")
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "unit")
    private String unit; // PIECE, SET, KG, LITER
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "condition")
    private String condition = "GOOD"; // GOOD, FAIR, POOR, DAMAGED
    
    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;
    
    @Column(name = "purchase_price")
    private Double purchasePrice;
    
    @Column(name = "status")
    private String status = "AVAILABLE"; // AVAILABLE, ALLOCATED, MAINTENANCE, DAMAGED, DISPOSED
    
    @Column(name = "description", length = 500)
    private String description;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
