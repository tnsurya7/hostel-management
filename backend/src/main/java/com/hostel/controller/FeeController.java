package com.hostel.controller;

import com.hostel.model.FeePayment;
import com.hostel.model.FeeType;
import com.hostel.service.FeeService;
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
@RequestMapping("/api/fees")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Fee Management", description = "APIs for managing fee types and payments")
public class FeeController {
    
    private final FeeService feeService;
    
    // ============ FEE TYPE ENDPOINTS ============
    
    @PostMapping("/types")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new fee type")
    public ResponseEntity<FeeType> createFeeType(@Valid @RequestBody FeeType feeType) {
        FeeType createdFeeType = feeService.createFeeType(feeType);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFeeType);
    }
    
    @GetMapping("/types")
    @Operation(summary = "Get all fee types")
    public ResponseEntity<List<FeeType>> getAllFeeTypes() {
        List<FeeType> feeTypes = feeService.getAllFeeTypes();
        return ResponseEntity.ok(feeTypes);
    }
    
    @GetMapping("/types/active")
    @Operation(summary = "Get active fee types")
    public ResponseEntity<List<FeeType>> getActiveFeeTypes() {
        List<FeeType> feeTypes = feeService.getActiveFeeTypes();
        return ResponseEntity.ok(feeTypes);
    }
    
    @GetMapping("/types/{id}")
    @Operation(summary = "Get fee type by ID")
    public ResponseEntity<FeeType> getFeeTypeById(@PathVariable Long id) {
        FeeType feeType = feeService.getFeeTypeById(id);
        return ResponseEntity.ok(feeType);
    }
    
    @PutMapping("/types/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update fee type")
    public ResponseEntity<FeeType> updateFeeType(@PathVariable Long id, @Valid @RequestBody FeeType feeType) {
        FeeType updatedFeeType = feeService.updateFeeType(id, feeType);
        return ResponseEntity.ok(updatedFeeType);
    }
    
    @DeleteMapping("/types/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete fee type")
    public ResponseEntity<Map<String, String>> deleteFeeType(@PathVariable Long id) {
        feeService.deleteFeeType(id);
        return ResponseEntity.ok(Map.of("message", "Fee type deleted successfully"));
    }
    
    // ============ FEE PAYMENT ENDPOINTS ============
    
    @PostMapping("/payments")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Create a new fee payment")
    public ResponseEntity<FeePayment> createFeePayment(@Valid @RequestBody FeePayment feePayment) {
        FeePayment createdPayment = feeService.createFeePayment(feePayment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }
    
    @GetMapping("/payments")
    @Operation(summary = "Get all fee payments")
    public ResponseEntity<List<FeePayment>> getAllFeePayments() {
        List<FeePayment> payments = feeService.getAllFeePayments();
        return ResponseEntity.ok(payments);
    }
    
    @GetMapping("/payments/{id}")
    @Operation(summary = "Get fee payment by ID")
    public ResponseEntity<FeePayment> getFeePaymentById(@PathVariable Long id) {
        FeePayment payment = feeService.getFeePaymentById(id);
        return ResponseEntity.ok(payment);
    }
    
    @GetMapping("/payments/student/{studentId}")
    @Operation(summary = "Get fee payments by student ID")
    public ResponseEntity<List<FeePayment>> getFeePaymentsByStudentId(@PathVariable Long studentId) {
        List<FeePayment> payments = feeService.getFeePaymentsByStudentId(studentId);
        return ResponseEntity.ok(payments);
    }
    
    @GetMapping("/payments/status/{status}")
    @Operation(summary = "Get fee payments by status")
    public ResponseEntity<List<FeePayment>> getFeePaymentsByStatus(@PathVariable String status) {
        List<FeePayment> payments = feeService.getFeePaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }
    
    @GetMapping("/payments/overdue")
    @Operation(summary = "Get overdue fee payments")
    public ResponseEntity<List<FeePayment>> getOverdueFeePayments() {
        List<FeePayment> payments = feeService.getOverdueFeePayments();
        return ResponseEntity.ok(payments);
    }
    
    @GetMapping("/payments/student/{studentId}/total-paid")
    @Operation(summary = "Get total paid amount by student")
    public ResponseEntity<Map<String, Double>> getTotalPaidByStudent(@PathVariable Long studentId) {
        Double total = feeService.getTotalPaidByStudent(studentId);
        return ResponseEntity.ok(Map.of("totalPaid", total));
    }
    
    @GetMapping("/payments/student/{studentId}/total-pending")
    @Operation(summary = "Get total pending amount by student")
    public ResponseEntity<Map<String, Double>> getTotalPendingByStudent(@PathVariable Long studentId) {
        Double total = feeService.getTotalPendingByStudent(studentId);
        return ResponseEntity.ok(Map.of("totalPending", total));
    }
    
    @PatchMapping("/payments/{id}/pay")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Mark fee payment as paid")
    public ResponseEntity<FeePayment> payFee(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String paymentMethod = request.get("paymentMethod");
        String transactionId = request.get("transactionId");
        FeePayment payment = feeService.payFee(id, paymentMethod, transactionId);
        return ResponseEntity.ok(payment);
    }
    
    @PutMapping("/payments/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Update fee payment")
    public ResponseEntity<FeePayment> updateFeePayment(@PathVariable Long id, @Valid @RequestBody FeePayment feePayment) {
        FeePayment updatedPayment = feeService.updateFeePayment(id, feePayment);
        return ResponseEntity.ok(updatedPayment);
    }
    
    @DeleteMapping("/payments/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete fee payment")
    public ResponseEntity<Map<String, String>> deleteFeePayment(@PathVariable Long id) {
        feeService.deleteFeePayment(id);
        return ResponseEntity.ok(Map.of("message", "Fee payment deleted successfully"));
    }
}
