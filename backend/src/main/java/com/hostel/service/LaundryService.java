package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.LaundryRequest;
import com.hostel.model.Student;
import com.hostel.repository.LaundryRequestRepository;
import com.hostel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LaundryService {
    
    private final LaundryRequestRepository laundryRequestRepository;
    private final StudentRepository studentRepository;
    
    @Transactional
    public LaundryRequest createRequest(LaundryRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + request.getStudentId()));
        
        request.setStudentName(student.getName());
        request.setRoomNumber(student.getRoomNumber());
        request.setStatus("PENDING");
        
        // Calculate charges based on service type and item count
        double baseCharge = switch (request.getServiceType()) {
            case "WASH" -> 5.0;
            case "IRON" -> 3.0;
            case "DRY_CLEAN" -> 15.0;
            case "WASH_AND_IRON" -> 7.0;
            default -> 5.0;
        };
        request.setCharges(baseCharge * request.getItemCount());
        
        return laundryRequestRepository.save(request);
    }
    
    public List<LaundryRequest> getAllRequests() {
        return laundryRequestRepository.findAll();
    }
    
    public LaundryRequest getRequestById(Long id) {
        return laundryRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Laundry request not found with id: " + id));
    }
    
    public List<LaundryRequest> getRequestsByStudentId(Long studentId) {
        return laundryRequestRepository.findByStudentId(studentId);
    }
    
    public List<LaundryRequest> getRequestsByStatus(String status) {
        return laundryRequestRepository.findByStatus(status);
    }
    
    @Transactional
    public LaundryRequest updateStatus(Long id, String status) {
        LaundryRequest request = getRequestById(id);
        request.setStatus(status);
        
        if ("DELIVERED".equals(status) && request.getActualDeliveryDate() == null) {
            request.setActualDeliveryDate(LocalDate.now());
        }
        
        return laundryRequestRepository.save(request);
    }
    
    @Transactional
    public LaundryRequest markPaid(Long id) {
        LaundryRequest request = getRequestById(id);
        request.setPaymentStatus("PAID");
        return laundryRequestRepository.save(request);
    }
    
    @Transactional
    public void deleteRequest(Long id) {
        LaundryRequest request = getRequestById(id);
        laundryRequestRepository.delete(request);
    }
}
