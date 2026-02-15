package com.hostel.service;

import com.hostel.dto.SearchCriteria;
import com.hostel.model.*;
import com.hostel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    
    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;
    private final FeePaymentRepository feePaymentRepository;
    private final ComplaintRepository complaintRepository;
    private final AttendanceRepository attendanceRepository;
    private final GatePassRepository gatePassRepository;
    
    public Map<String, Object> advancedSearch(SearchCriteria criteria) {
        Map<String, Object> results = new HashMap<>();
        
        // Search students
        List<Student> students = searchStudents(criteria);
        results.put("students", students);
        results.put("studentCount", students.size());
        
        // Search rooms
        List<Room> rooms = searchRooms(criteria);
        results.put("rooms", rooms);
        results.put("roomCount", rooms.size());
        
        // Search fees
        List<FeePayment> fees = searchFees(criteria);
        results.put("fees", fees);
        results.put("feeCount", fees.size());
        
        // Search complaints
        List<Complaint> complaints = searchComplaints(criteria);
        results.put("complaints", complaints);
        results.put("complaintCount", complaints.size());
        
        results.put("totalResults", students.size() + rooms.size() + fees.size() + complaints.size());
        
        return results;
    }
    
    public List<Student> searchStudents(SearchCriteria criteria) {
        List<Student> allStudents = studentRepository.findAll();
        
        return allStudents.stream()
                .filter(student -> {
                    if (criteria.getName() != null && !student.getName().toLowerCase().contains(criteria.getName().toLowerCase())) {
                        return false;
                    }
                    if (criteria.getEmail() != null && !student.getEmail().toLowerCase().contains(criteria.getEmail().toLowerCase())) {
                        return false;
                    }
                    if (criteria.getPhone() != null && student.getPhoneNumber() != null && !student.getPhoneNumber().contains(criteria.getPhone())) {
                        return false;
                    }
                    if (criteria.getRoomNumber() != null && !criteria.getRoomNumber().equals(student.getRoomNumber())) {
                        return false;
                    }
                    if (criteria.getStatus() != null && !criteria.getStatus().equals(student.getStatus())) {
                        return false;
                    }
                    // Gender field not available in Student model
                    // if (criteria.getGender() != null && !criteria.getGender().equals(student.getGender())) {
                    //     return false;
                    // }
                    if (criteria.getCourse() != null && student.getCourse() != null && !student.getCourse().toLowerCase().contains(criteria.getCourse().toLowerCase())) {
                        return false;
                    }
                    if (criteria.getYear() != null && student.getYearOfStudy() != null && !student.getYearOfStudy().equals(criteria.getYear())) {
                        return false;
                    }
                    // Department field not available in Student model
                    // if (criteria.getDepartment() != null && student.getDepartment() != null && !student.getDepartment().toLowerCase().contains(criteria.getDepartment().toLowerCase())) {
                    //     return false;
                    // }
                    if (criteria.getSearchTerm() != null) {
                        String searchTerm = criteria.getSearchTerm().toLowerCase();
                        return student.getName().toLowerCase().contains(searchTerm) ||
                               student.getEmail().toLowerCase().contains(searchTerm) ||
                               (student.getPhoneNumber() != null && student.getPhoneNumber().contains(searchTerm));
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
    
    public List<Room> searchRooms(SearchCriteria criteria) {
        List<Room> allRooms = roomRepository.findAll();
        
        return allRooms.stream()
                .filter(room -> {
                    if (criteria.getRoomNumber() != null && !room.getRoomNumber().contains(criteria.getRoomNumber())) {
                        return false;
                    }
                    if (criteria.getRoomType() != null && !criteria.getRoomType().equals(room.getRoomType())) {
                        return false;
                    }
                    if (criteria.getRoomStatus() != null && !criteria.getRoomStatus().equals(room.getStatus())) {
                        return false;
                    }
                    if (criteria.getFloor() != null && !criteria.getFloor().equals(room.getFloor())) {
                        return false;
                    }
                    if (criteria.getBlock() != null && !criteria.getBlock().equals(room.getBlock())) {
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
    
    public List<FeePayment> searchFees(SearchCriteria criteria) {
        List<FeePayment> allFees = feePaymentRepository.findAll();
        
        return allFees.stream()
                .filter(fee -> {
                    if (criteria.getFeeStatus() != null && !criteria.getFeeStatus().equals(fee.getStatus())) {
                        return false;
                    }
                    if (criteria.getMinAmount() != null && fee.getTotalAmount() < criteria.getMinAmount()) {
                        return false;
                    }
                    if (criteria.getMaxAmount() != null && fee.getTotalAmount() > criteria.getMaxAmount()) {
                        return false;
                    }
                    if (criteria.getFromDate() != null && fee.getPaymentDate() != null && fee.getPaymentDate().isBefore(criteria.getFromDate())) {
                        return false;
                    }
                    if (criteria.getToDate() != null && fee.getPaymentDate() != null && fee.getPaymentDate().isAfter(criteria.getToDate())) {
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
    
    public List<Complaint> searchComplaints(SearchCriteria criteria) {
        List<Complaint> allComplaints = complaintRepository.findAll();
        
        return allComplaints.stream()
                .filter(complaint -> {
                    if (criteria.getComplaintCategory() != null && !criteria.getComplaintCategory().equals(complaint.getCategory())) {
                        return false;
                    }
                    if (criteria.getComplaintPriority() != null && !criteria.getComplaintPriority().equals(complaint.getPriority())) {
                        return false;
                    }
                    if (criteria.getComplaintStatus() != null && !criteria.getComplaintStatus().equals(complaint.getStatus())) {
                        return false;
                    }
                    if (criteria.getSearchTerm() != null) {
                        String searchTerm = criteria.getSearchTerm().toLowerCase();
                        return complaint.getTitle().toLowerCase().contains(searchTerm) ||
                               complaint.getDescription().toLowerCase().contains(searchTerm);
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
    
    public Map<String, Object> quickSearch(String searchTerm) {
        SearchCriteria criteria = new SearchCriteria();
        criteria.setSearchTerm(searchTerm);
        
        return advancedSearch(criteria);
    }
}
