package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.EmergencyAlert;
import com.hostel.model.EmergencyContact;
import com.hostel.model.Student;
import com.hostel.repository.EmergencyAlertRepository;
import com.hostel.repository.EmergencyContactRepository;
import com.hostel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmergencyService {
    
    private final EmergencyAlertRepository emergencyAlertRepository;
    private final EmergencyContactRepository emergencyContactRepository;
    private final StudentRepository studentRepository;
    
    // ============ EMERGENCY ALERT OPERATIONS ============
    
    @Transactional
    public EmergencyAlert createAlert(EmergencyAlert alert) {
        if (alert.getStudentId() != null) {
            Student student = studentRepository.findById(alert.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + alert.getStudentId()));
            alert.setStudentName(student.getName());
        }
        
        alert.setStatus("ACTIVE");
        return emergencyAlertRepository.save(alert);
    }
    
    @Transactional
    public EmergencyAlert createSOSAlert(Long studentId, String location, String description) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        
        EmergencyAlert alert = new EmergencyAlert();
        alert.setStudentId(studentId);
        alert.setStudentName(student.getName());
        alert.setAlertType("SOS");
        alert.setSeverity("CRITICAL");
        alert.setLocation(location);
        alert.setDescription(description);
        alert.setStatus("ACTIVE");
        alert.setReportedBy(student.getName());
        
        return emergencyAlertRepository.save(alert);
    }
    
    public List<EmergencyAlert> getAllAlerts() {
        return emergencyAlertRepository.findAll();
    }
    
    public EmergencyAlert getAlertById(Long id) {
        return emergencyAlertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emergency alert not found with id: " + id));
    }
    
    public List<EmergencyAlert> getAlertsByStatus(String status) {
        return emergencyAlertRepository.findByStatus(status);
    }
    
    public List<EmergencyAlert> getActiveAlerts() {
        return emergencyAlertRepository.findByStatus("ACTIVE");
    }
    
    public List<EmergencyAlert> getCriticalAlerts() {
        return emergencyAlertRepository.findBySeverity("CRITICAL");
    }
    
    @Transactional
    public EmergencyAlert acknowledgeAlert(Long id, String respondedBy) {
        EmergencyAlert alert = getAlertById(id);
        alert.setStatus("ACKNOWLEDGED");
        alert.setRespondedBy(respondedBy);
        alert.setResponseTime(LocalDateTime.now());
        return emergencyAlertRepository.save(alert);
    }
    
    @Transactional
    public EmergencyAlert resolveAlert(Long id, String actionTaken, String remarks) {
        EmergencyAlert alert = getAlertById(id);
        alert.setStatus("RESOLVED");
        alert.setActionTaken(actionTaken);
        alert.setRemarks(remarks);
        alert.setResolutionTime(LocalDateTime.now());
        return emergencyAlertRepository.save(alert);
    }
    
    @Transactional
    public void deleteAlert(Long id) {
        EmergencyAlert alert = getAlertById(id);
        emergencyAlertRepository.delete(alert);
    }
    
    // ============ EMERGENCY CONTACT OPERATIONS ============
    
    @Transactional
    public EmergencyContact addEmergencyContact(EmergencyContact contact) {
        Student student = studentRepository.findById(contact.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + contact.getStudentId()));
        
        contact.setStudentName(student.getName());
        return emergencyContactRepository.save(contact);
    }
    
    public List<EmergencyContact> getAllEmergencyContacts() {
        return emergencyContactRepository.findAll();
    }
    
    public EmergencyContact getEmergencyContactById(Long id) {
        return emergencyContactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emergency contact not found with id: " + id));
    }
    
    public List<EmergencyContact> getEmergencyContactsByStudentId(Long studentId) {
        return emergencyContactRepository.findByStudentIdOrderByPriorityAsc(studentId);
    }
    
    @Transactional
    public EmergencyContact updateEmergencyContact(Long id, EmergencyContact contactDetails) {
        EmergencyContact contact = getEmergencyContactById(id);
        contact.setContactName(contactDetails.getContactName());
        contact.setContactPhone(contactDetails.getContactPhone());
        contact.setRelationship(contactDetails.getRelationship());
        contact.setEmail(contactDetails.getEmail());
        contact.setAddress(contactDetails.getAddress());
        contact.setIsPrimary(contactDetails.getIsPrimary());
        contact.setPriority(contactDetails.getPriority());
        return emergencyContactRepository.save(contact);
    }
    
    @Transactional
    public void deleteEmergencyContact(Long id) {
        EmergencyContact contact = getEmergencyContactById(id);
        emergencyContactRepository.delete(contact);
    }
}
