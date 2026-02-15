package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.Document;
import com.hostel.model.Student;
import com.hostel.repository.DocumentRepository;
import com.hostel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    
    private final DocumentRepository documentRepository;
    private final StudentRepository studentRepository;
    
    @Transactional
    public Document uploadDocument(Document document) {
        Student student = studentRepository.findById(document.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + document.getStudentId()));
        
        document.setStudentName(student.getName());
        document.setVerificationStatus("PENDING");
        
        // Check if document is expired
        if (document.getExpiryDate() != null && document.getExpiryDate().isBefore(LocalDate.now())) {
            document.setIsExpired(true);
        }
        
        return documentRepository.save(document);
    }
    
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }
    
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with id: " + id));
    }
    
    public List<Document> getDocumentsByStudentId(Long studentId) {
        return documentRepository.findByStudentId(studentId);
    }
    
    public List<Document> getDocumentsByType(String documentType) {
        return documentRepository.findByDocumentType(documentType);
    }
    
    public List<Document> getDocumentsByVerificationStatus(String status) {
        return documentRepository.findByVerificationStatus(status);
    }
    
    public List<Document> getExpiredDocuments() {
        return documentRepository.findByIsExpired(true);
    }
    
    @Transactional
    public Document verifyDocument(Long id, String verifiedBy, String status, String remarks) {
        Document document = getDocumentById(id);
        document.setVerificationStatus(status);
        document.setVerifiedBy(verifiedBy);
        document.setVerifiedDate(LocalDateTime.now());
        document.setRemarks(remarks);
        return documentRepository.save(document);
    }
    
    @Transactional
    public Document updateDocument(Long id, Document documentDetails) {
        Document document = getDocumentById(id);
        document.setDocumentName(documentDetails.getDocumentName());
        document.setDocumentType(documentDetails.getDocumentType());
        document.setExpiryDate(documentDetails.getExpiryDate());
        
        // Check if document is expired
        if (document.getExpiryDate() != null && document.getExpiryDate().isBefore(LocalDate.now())) {
            document.setIsExpired(true);
        } else {
            document.setIsExpired(false);
        }
        
        return documentRepository.save(document);
    }
    
    @Transactional
    public void deleteDocument(Long id) {
        Document document = getDocumentById(id);
        documentRepository.delete(document);
    }
}
