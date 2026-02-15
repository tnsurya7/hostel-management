package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "mess_menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessMenu {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Date is required")
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @NotBlank(message = "Meal type is required")
    @Column(name = "meal_type", nullable = false)
    private String mealType; // BREAKFAST, LUNCH, SNACKS, DINNER
    
    @NotBlank(message = "Menu items are required")
    @Column(name = "menu_items", nullable = false, length = 1000)
    private String menuItems;
    
    @Column(name = "day_of_week")
    private String dayOfWeek;
    
    @Column(name = "is_special")
    private Boolean isSpecial = false;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
