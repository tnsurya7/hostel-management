package com.hostel.controller;

import com.hostel.model.Student;
import com.hostel.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Student Management", description = "APIs for managing hostel students")
public class StudentController {
    
    private final StudentService studentService;
    
    @PostMapping
    @Operation(summary = "Create a new student")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }
    
    @GetMapping
    @Operation(summary = "Get all students")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update student")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student studentDetails) {
        Student updatedStudent = studentService.updateStudent(id, studentDetails);
        return ResponseEntity.ok(updatedStudent);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student deleted successfully");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/room/{roomNumber}")
    @Operation(summary = "Get students by room number")
    public ResponseEntity<List<Student>> getStudentsByRoom(@PathVariable String roomNumber) {
        List<Student> students = studentService.getStudentsByRoom(roomNumber);
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/fees/{status}")
    @Operation(summary = "Get students by fee payment status")
    public ResponseEntity<List<Student>> getStudentsByFeeStatus(@PathVariable Boolean status) {
        List<Student> students = studentService.getStudentsByFeeStatus(status);
        return ResponseEntity.ok(students);
    }
    
    @GetMapping("/stats")
    @Operation(summary = "Get student statistics")
    public ResponseEntity<Map<String, Long>> getStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalStudents", studentService.getTotalStudentsCount());
        stats.put("unpaidStudents", studentService.getUnpaidStudentsCount());
        return ResponseEntity.ok(stats);
    }
}
