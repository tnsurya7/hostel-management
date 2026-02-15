package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.LeaveRequest;
import com.hostel.model.Student;
import com.hostel.repository.LeaveRequestRepository;
import com.hostel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveRequestService {
    
    private final LeaveRequestRepository leaveRequestRepository;
    private final StudentRepository studentRepository;
    
    @Transactional
    public LeaveRequest createLeaveRequest(LeaveRequest leaveRequest) {
        // Verify student exists
        Student student = studentRepository.findById(leaveRequest.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + leaveRequest.getStudentId()));
        
        leaveRequest.setStudentName(student.getName());
        leaveRequest.setStatus("PENDING");
        return leaveRequestRepository.save(leaveRequest);
    }
    
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }
    
    public LeaveRequest getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with id: " + id));
    }
    
    public List<LeaveRequest> getLeaveRequestsByStudent(Long studentId) {
        return leaveRequestRepository.findByStudentIdOrderByCreatedAtDesc(studentId);
    }
    
    public List<LeaveRequest> getLeaveRequestsByStatus(String status) {
        return leaveRequestRepository.findByStatus(status);
    }
    
    @Transactional
    public LeaveRequest approveLeaveRequest(Long id, String approvedBy, String remarks) {
        LeaveRequest leaveRequest = getLeaveRequestById(id);
        leaveRequest.setStatus("APPROVED");
        leaveRequest.setApprovedBy(approvedBy);
        leaveRequest.setApprovalDate(LocalDateTime.now());
        leaveRequest.setRemarks(remarks);
        
        // Update student status
        Student student = studentRepository.findById(leaveRequest.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        student.setStatus("ON_LEAVE");
        studentRepository.save(student);
        
        return leaveRequestRepository.save(leaveRequest);
    }
    
    @Transactional
    public LeaveRequest rejectLeaveRequest(Long id, String rejectedBy, String remarks) {
        LeaveRequest leaveRequest = getLeaveRequestById(id);
        leaveRequest.setStatus("REJECTED");
        leaveRequest.setApprovedBy(rejectedBy);
        leaveRequest.setApprovalDate(LocalDateTime.now());
        leaveRequest.setRemarks(remarks);
        return leaveRequestRepository.save(leaveRequest);
    }
    
    @Transactional
    public void deleteLeaveRequest(Long id) {
        LeaveRequest leaveRequest = getLeaveRequestById(id);
        leaveRequestRepository.delete(leaveRequest);
    }
}
