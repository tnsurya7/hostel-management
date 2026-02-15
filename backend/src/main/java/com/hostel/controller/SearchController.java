package com.hostel.controller;

import com.hostel.dto.SearchCriteria;
import com.hostel.model.*;
import com.hostel.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Advanced Search", description = "APIs for advanced search and filtering")
public class SearchController {
    
    private final SearchService searchService;
    
    @PostMapping("/advanced")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Advanced search across all entities")
    public ResponseEntity<Map<String, Object>> advancedSearch(@RequestBody SearchCriteria criteria) {
        Map<String, Object> results = searchService.advancedSearch(criteria);
        return ResponseEntity.ok(results);
    }
    
    @GetMapping("/quick")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Quick search with a single search term")
    public ResponseEntity<Map<String, Object>> quickSearch(@RequestParam String searchTerm) {
        Map<String, Object> results = searchService.quickSearch(searchTerm);
        return ResponseEntity.ok(results);
    }
    
    @PostMapping("/students")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Search students with criteria")
    public ResponseEntity<List<Student>> searchStudents(@RequestBody SearchCriteria criteria) {
        List<Student> students = searchService.searchStudents(criteria);
        return ResponseEntity.ok(students);
    }
    
    @PostMapping("/rooms")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Search rooms with criteria")
    public ResponseEntity<List<Room>> searchRooms(@RequestBody SearchCriteria criteria) {
        List<Room> rooms = searchService.searchRooms(criteria);
        return ResponseEntity.ok(rooms);
    }
    
    @PostMapping("/fees")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Search fees with criteria")
    public ResponseEntity<List<FeePayment>> searchFees(@RequestBody SearchCriteria criteria) {
        List<FeePayment> fees = searchService.searchFees(criteria);
        return ResponseEntity.ok(fees);
    }
    
    @PostMapping("/complaints")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Search complaints with criteria")
    public ResponseEntity<List<Complaint>> searchComplaints(@RequestBody SearchCriteria criteria) {
        List<Complaint> complaints = searchService.searchComplaints(criteria);
        return ResponseEntity.ok(complaints);
    }
}
