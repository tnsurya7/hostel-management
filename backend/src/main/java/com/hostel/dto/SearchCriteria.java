package com.hostel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {
    
    // Student search criteria
    private String name;
    private String email;
    private String phone;
    private String roomNumber;
    private String status;
    private String gender;
    private String course;
    private String year;
    private String department;
    
    // Date range
    private LocalDate fromDate;
    private LocalDate toDate;
    
    // Fee search criteria
    private String feeStatus;
    private Double minAmount;
    private Double maxAmount;
    
    // Room search criteria
    private String roomType;
    private String roomStatus;
    private Integer floor;
    private String block;
    
    // Complaint search criteria
    private String complaintCategory;
    private String complaintPriority;
    private String complaintStatus;
    
    // General search
    private String searchTerm;
    private String sortBy;
    private String sortOrder; // ASC, DESC
    
    // Pagination
    private Integer page = 0;
    private Integer size = 10;
}
