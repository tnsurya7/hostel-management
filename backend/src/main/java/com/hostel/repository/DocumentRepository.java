package com.hostel.repository;

import com.hostel.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByStudentId(Long studentId);
    List<Document> findByDocumentType(String documentType);
    List<Document> findByVerificationStatus(String verificationStatus);
    List<Document> findByIsExpired(Boolean isExpired);
    List<Document> findByExpiryDateBefore(LocalDate date);
    List<Document> findByStudentIdAndDocumentType(Long studentId, String documentType);
}
