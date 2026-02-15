package com.hostel.service;

import com.hostel.exception.DuplicateResourceException;
import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.FeePayment;
import com.hostel.model.FeeType;
import com.hostel.model.Student;
import com.hostel.repository.FeePaymentRepository;
import com.hostel.repository.FeeTypeRepository;
import com.hostel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeeService {
    
    private final FeePaymentRepository feePaymentRepository;
    private final FeeTypeRepository feeTypeRepository;
    private final StudentRepository studentRepository;
    
    // ============ FEE TYPE OPERATIONS ============
    
    @Transactional
    public FeeType createFeeType(FeeType feeType) {
        if (feeTypeRepository.existsByName(feeType.getName())) {
            throw new DuplicateResourceException("Fee type with name " + feeType.getName() + " already exists");
        }
        return feeTypeRepository.save(feeType);
    }
    
    public List<FeeType> getAllFeeTypes() {
        return feeTypeRepository.findAll();
    }
    
    public List<FeeType> getActiveFeeTypes() {
        return feeTypeRepository.findByIsActive(true);
    }
    
    public FeeType getFeeTypeById(Long id) {
        return feeTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fee type not found with id: " + id));
    }
    
    @Transactional
    public FeeType updateFeeType(Long id, FeeType feeTypeDetails) {
        FeeType feeType = getFeeTypeById(id);
        
        if (!feeType.getName().equals(feeTypeDetails.getName()) && 
            feeTypeRepository.existsByName(feeTypeDetails.getName())) {
            throw new DuplicateResourceException("Fee type with name " + feeTypeDetails.getName() + " already exists");
        }
        
        feeType.setName(feeTypeDetails.getName());
        feeType.setAmount(feeTypeDetails.getAmount());
        feeType.setFrequency(feeTypeDetails.getFrequency());
        feeType.setDescription(feeTypeDetails.getDescription());
        feeType.setIsMandatory(feeTypeDetails.getIsMandatory());
        feeType.setIsActive(feeTypeDetails.getIsActive());
        
        return feeTypeRepository.save(feeType);
    }
    
    @Transactional
    public void deleteFeeType(Long id) {
        FeeType feeType = getFeeTypeById(id);
        feeTypeRepository.delete(feeType);
    }
    
    // ============ FEE PAYMENT OPERATIONS ============
    
    @Transactional
    public FeePayment createFeePayment(FeePayment feePayment) {
        // Validate student
        Student student = studentRepository.findById(feePayment.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + feePayment.getStudentId()));
        
        // Validate fee type
        FeeType feeType = getFeeTypeById(feePayment.getFeeTypeId());
        
        // Set student and fee type names
        feePayment.setStudentName(student.getName());
        feePayment.setFeeTypeName(feeType.getName());
        
        // Set amount from fee type if not provided
        if (feePayment.getAmount() == null) {
            feePayment.setAmount(feeType.getAmount());
        }
        
        // Calculate late fee if overdue
        if (feePayment.getDueDate() != null && LocalDate.now().isAfter(feePayment.getDueDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(feePayment.getDueDate(), LocalDate.now());
            double lateFeePerDay = 10.0; // Rs. 10 per day
            feePayment.setLateFee(daysOverdue * lateFeePerDay);
            feePayment.setStatus("OVERDUE");
        }
        
        // Calculate total amount
        feePayment.setTotalAmount(feePayment.getAmount() + feePayment.getLateFee());
        
        return feePaymentRepository.save(feePayment);
    }
    
    public List<FeePayment> getAllFeePayments() {
        return feePaymentRepository.findAll();
    }
    
    public FeePayment getFeePaymentById(Long id) {
        return feePaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fee payment not found with id: " + id));
    }
    
    public List<FeePayment> getFeePaymentsByStudentId(Long studentId) {
        return feePaymentRepository.findByStudentId(studentId);
    }
    
    public List<FeePayment> getFeePaymentsByStatus(String status) {
        return feePaymentRepository.findByStatus(status);
    }
    
    public List<FeePayment> getOverdueFeePayments() {
        return feePaymentRepository.findByDueDateBefore(LocalDate.now());
    }
    
    public Double getTotalPaidByStudent(Long studentId) {
        Double total = feePaymentRepository.getTotalPaidByStudent(studentId);
        return total != null ? total : 0.0;
    }
    
    public Double getTotalPendingByStudent(Long studentId) {
        Double total = feePaymentRepository.getTotalPendingByStudent(studentId);
        return total != null ? total : 0.0;
    }
    
    @Transactional
    public FeePayment payFee(Long id, String paymentMethod, String transactionId) {
        FeePayment feePayment = getFeePaymentById(id);
        
        if ("PAID".equals(feePayment.getStatus())) {
            throw new IllegalStateException("Fee payment is already paid");
        }
        
        feePayment.setStatus("PAID");
        feePayment.setPaymentDate(LocalDate.now());
        feePayment.setPaymentMethod(paymentMethod);
        feePayment.setTransactionId(transactionId);
        feePayment.setReceiptNumber("RCP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        
        return feePaymentRepository.save(feePayment);
    }
    
    @Transactional
    public FeePayment updateFeePayment(Long id, FeePayment feePaymentDetails) {
        FeePayment feePayment = getFeePaymentById(id);
        
        feePayment.setAmount(feePaymentDetails.getAmount());
        feePayment.setLateFee(feePaymentDetails.getLateFee());
        feePayment.setTotalAmount(feePaymentDetails.getAmount() + feePaymentDetails.getLateFee());
        feePayment.setDueDate(feePaymentDetails.getDueDate());
        feePayment.setSemester(feePaymentDetails.getSemester());
        feePayment.setAcademicYear(feePaymentDetails.getAcademicYear());
        feePayment.setRemarks(feePaymentDetails.getRemarks());
        
        return feePaymentRepository.save(feePayment);
    }
    
    @Transactional
    public void deleteFeePayment(Long id) {
        FeePayment feePayment = getFeePaymentById(id);
        feePaymentRepository.delete(feePayment);
    }
}
