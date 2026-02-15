package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.Student;
import com.hostel.model.Visitor;
import com.hostel.repository.StudentRepository;
import com.hostel.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VisitorService {
    
    private final VisitorRepository visitorRepository;
    private final StudentRepository studentRepository;
    
    @Transactional
    public Visitor createVisitor(Visitor visitor) {
        Student student = studentRepository.findById(visitor.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + visitor.getStudentId()));
        
        visitor.setStudentName(student.getName());
        visitor.setStatus("PENDING");
        visitor.setPassNumber("VP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        
        return visitorRepository.save(visitor);
    }
    
    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }
    
    public Visitor getVisitorById(Long id) {
        return visitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found with id: " + id));
    }
    
    public List<Visitor> getVisitorsByStudentId(Long studentId) {
        return visitorRepository.findByStudentId(studentId);
    }
    
    public List<Visitor> getVisitorsByStatus(String status) {
        return visitorRepository.findByStatus(status);
    }
    
    @Transactional
    public Visitor approveVisitor(Long id, String approvedBy) {
        Visitor visitor = getVisitorById(id);
        visitor.setStatus("APPROVED");
        visitor.setApprovedBy(approvedBy);
        return visitorRepository.save(visitor);
    }
    
    @Transactional
    public Visitor rejectVisitor(Long id, String rejectedBy, String remarks) {
        Visitor visitor = getVisitorById(id);
        visitor.setStatus("REJECTED");
        visitor.setApprovedBy(rejectedBy);
        visitor.setRemarks(remarks);
        return visitorRepository.save(visitor);
    }
    
    @Transactional
    public Visitor markEntry(Long id) {
        Visitor visitor = getVisitorById(id);
        visitor.setEntryTime(LocalDateTime.now());
        visitor.setStatus("IN_PROGRESS");
        return visitorRepository.save(visitor);
    }
    
    @Transactional
    public Visitor markExit(Long id) {
        Visitor visitor = getVisitorById(id);
        visitor.setExitTime(LocalDateTime.now());
        visitor.setStatus("COMPLETED");
        return visitorRepository.save(visitor);
    }
    
    @Transactional
    public void deleteVisitor(Long id) {
        Visitor visitor = getVisitorById(id);
        visitorRepository.delete(visitor);
    }
}
