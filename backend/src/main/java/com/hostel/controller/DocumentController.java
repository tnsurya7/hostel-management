package com.hostel.controller;

import com.hostel.model.Document;
import com.hostel.service.DocumentService;
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
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Document Management", description = "APIs for managing student documents")
public class DocumentController {
    
    private final DocumentService documentService;
    
    @PostMapping
    @Operation(summary = "Upload a new document")
    public ResponseEntity<Document> uploadDocument(@Valid @RequestBody Document document) {
        Document uploadedDocument = documentService.uploadDocument(document);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedDocument);
    }
    
    @GetMapping
    @Operation(summary = "Get all documents")
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get document by ID")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id);
        return ResponseEntity.ok(document);
    }
    
    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get documents by student ID")
    public ResponseEntity<List<Document>> getDocumentsByStudentId(@PathVariable Long studentId) {
        List<Document> documents = documentService.getDocumentsByStudentId(studentId);
        return ResponseEntity.ok(documents);
    }
    
    @GetMapping("/type/{documentType}")
    @Operation(summary = "Get documents by type")
    public ResponseEntity<List<Document>> getDocumentsByType(@PathVariable String documentType) {
        List<Document> documents = documentService.getDocumentsByType(documentType);
        return ResponseEntity.ok(documents);
    }
    
    @GetMapping("/verification-status/{status}")
    @Operation(summary = "Get documents by verification status")
    public ResponseEntity<List<Document>> getDocumentsByVerificationStatus(@PathVariable String status) {
        List<Document> documents = documentService.getDocumentsByVerificationStatus(status);
        return ResponseEntity.ok(documents);
    }
    
    @GetMapping("/expired")
    @Operation(summary = "Get expired documents")
    public ResponseEntity<List<Document>> getExpiredDocuments() {
        List<Document> documents = documentService.getExpiredDocuments();
        return ResponseEntity.ok(documents);
    }
    
    @PatchMapping("/{id}/verify")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Verify document")
    public ResponseEntity<Document> verifyDocument(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {
        String verifiedBy = request.get("verifiedBy");
        String status = request.get("status");
        String remarks = request.get("remarks");
        Document document = documentService.verifyDocument(id, verifiedBy, status, remarks);
        return ResponseEntity.ok(document);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update document")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @Valid @RequestBody Document document) {
        Document updatedDocument = documentService.updateDocument(id, document);
        return ResponseEntity.ok(updatedDocument);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete document")
    public ResponseEntity<Map<String, String>> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok(Map.of("message", "Document deleted successfully"));
    }
}
