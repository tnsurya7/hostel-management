package com.hostel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "fee_payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeePayment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID is required")
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "student_name")
    private String studentName;
    
    @NotNull(message = "Fee type ID is required")
    @Column(name = "fee_type_id", nullable = false)
    private Long feeTypeId;
    
    @Column(name = "fee_type_name")
    private String feeTypeName;
    
    @NotNull(message = "Amount is required")
    @Column(name = "amount", nullable = false)
    private Double amount;
    
    @Column(name = "late_fee")
    private Double lateFee = 0.0;
    
    @Column(name = "total_amount")
    private Double totalAmount;
    
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    
    @Column(name = "due_date")
    private LocalDate dueDate;
    
    @Column(name = "payment_method")
    private String paymentMethod; // CASH, CARD, UPI, NET_BANKING, CHEQUE
    
    @Column(name = "transaction_id")
    private String transactionId;
    
    @Column(name = "receipt_number")
    private String receiptNumber;
    
    @Column(name = "status")
    private String status = "PENDING"; // PENDING, PAID, OVERDUE, CANCELLED
    
    @Column(name = "semester")
    private String semester;
    
    @Column(name = "academic_year")
    private String academicYear;
    
    @Column(name = "remarks", length = 500)
    private String remarks;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
