package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.GatePass;
import com.hostel.model.Student;
import com.hostel.repository.GatePassRepository;
import com.hostel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GatePassService {
    
    private final GatePassRepository gatePassRepository;
    private final StudentRepository studentRepository;
    
    @Transactional
    public GatePass createGatePass(GatePass gatePass) {
        Student student = studentRepository.findById(gatePass.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + gatePass.getStudentId()));
        
        gatePass.setStudentName(student.getName());
        gatePass.setStatus("PENDING");
        gatePass.setPassNumber("GP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        gatePass.setQrCode("QR-" + UUID.randomUUID().toString());
        
        return gatePassRepository.save(gatePass);
    }
    
    public List<GatePass> getAllGatePasses() {
        return gatePassRepository.findAll();
    }
    
    public GatePass getGatePassById(Long id) {
        return gatePassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gate pass not found with id: " + id));
    }
    
    public List<GatePass> getGatePassesByStudentId(Long studentId) {
        return gatePassRepository.findByStudentId(studentId);
    }
    
    public List<GatePass> getGatePassesByStatus(String status) {
        return gatePassRepository.findByStatus(status);
    }
    
    @Transactional
    public GatePass approveGatePass(Long id, String approvedBy) {
        GatePass gatePass = getGatePassById(id);
        gatePass.setStatus("APPROVED");
        gatePass.setApprovedBy(approvedBy);
        gatePass.setApprovalDate(LocalDateTime.now());
        return gatePassRepository.save(gatePass);
    }
    
    @Transactional
    public GatePass rejectGatePass(Long id, String rejectedBy, String remarks) {
        GatePass gatePass = getGatePassById(id);
        gatePass.setStatus("REJECTED");
        gatePass.setApprovedBy(rejectedBy);
        gatePass.setRemarks(remarks);
        return gatePassRepository.save(gatePass);
    }
    
    @Transactional
    public GatePass markReturn(Long id) {
        GatePass gatePass = getGatePassById(id);
        gatePass.setActualReturnTime(LocalDateTime.now());
        gatePass.setStatus("COMPLETED");
        
        // Check if late return
        if (gatePass.getActualReturnTime().isAfter(gatePass.getToTime())) {
            gatePass.setLateReturn(true);
            long hoursLate = ChronoUnit.HOURS.between(gatePass.getToTime(), gatePass.getActualReturnTime());
            gatePass.setPenaltyAmount(hoursLate * 50.0); // Rs. 50 per hour
        }
        
        return gatePassRepository.save(gatePass);
    }
    
    @Transactional
    public void deleteGatePass(Long id) {
        GatePass gatePass = getGatePassById(id);
        gatePassRepository.delete(gatePass);
    }
}
