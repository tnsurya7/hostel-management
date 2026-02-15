package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.Complaint;
import com.hostel.model.Student;
import com.hostel.repository.ComplaintRepository;
import com.hostel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService {
    
    private final ComplaintRepository complaintRepository;
    private final StudentRepository studentRepository;
    
    @Transactional
    public Complaint createComplaint(Complaint complaint) {
        Student student = studentRepository.findById(complaint.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + complaint.getStudentId()));
        
        complaint.setStudentName(student.getName());
        complaint.setStatus("OPEN");
        
        return complaintRepository.save(complaint);
    }
    
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }
    
    public Complaint getComplaintById(Long id) {
        return complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));
    }
    
    public List<Complaint> getComplaintsByStudentId(Long studentId) {
        return complaintRepository.findByStudentId(studentId);
    }
    
    public List<Complaint> getComplaintsByStatus(String status) {
        return complaintRepository.findByStatus(status);
    }
    
    public List<Complaint> getComplaintsByCategory(String category) {
        return complaintRepository.findByCategory(category);
    }
    
    public List<Complaint> getComplaintsByPriority(String priority) {
        return complaintRepository.findByPriority(priority);
    }
    
    @Transactional
    public Complaint assignComplaint(Long id, String assignedTo) {
        Complaint complaint = getComplaintById(id);
        complaint.setAssignedTo(assignedTo);
        complaint.setAssignedDate(LocalDateTime.now());
        complaint.setStatus("IN_PROGRESS");
        return complaintRepository.save(complaint);
    }
    
    @Transactional
    public Complaint resolveComplaint(Long id, String resolution) {
        Complaint complaint = getComplaintById(id);
        complaint.setStatus("RESOLVED");
        complaint.setResolution(resolution);
        complaint.setResolvedDate(LocalDateTime.now());
        return complaintRepository.save(complaint);
    }
    
    @Transactional
    public Complaint closeComplaint(Long id, String remarks) {
        Complaint complaint = getComplaintById(id);
        complaint.setStatus("CLOSED");
        complaint.setRemarks(remarks);
        return complaintRepository.save(complaint);
    }
    
    @Transactional
    public Complaint updateComplaint(Long id, Complaint complaintDetails) {
        Complaint complaint = getComplaintById(id);
        complaint.setTitle(complaintDetails.getTitle());
        complaint.setDescription(complaintDetails.getDescription());
        complaint.setCategory(complaintDetails.getCategory());
        complaint.setPriority(complaintDetails.getPriority());
        return complaintRepository.save(complaint);
    }
    
    @Transactional
    public void deleteComplaint(Long id) {
        Complaint complaint = getComplaintById(id);
        complaintRepository.delete(complaint);
    }
}
