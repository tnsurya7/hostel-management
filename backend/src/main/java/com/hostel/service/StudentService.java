package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.exception.DuplicateResourceException;
import com.hostel.model.Student;
import com.hostel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    
    private final StudentRepository studentRepository;
    
    @Transactional
    public Student createStudent(Student student) {
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Student with email " + student.getEmail() + " already exists");
        }
        return studentRepository.save(student);
    }
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }
    
    @Transactional
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = getStudentById(id);
        
        // Check if email is being changed and if new email already exists
        if (!student.getEmail().equals(studentDetails.getEmail())) {
            if (studentRepository.findByEmail(studentDetails.getEmail()).isPresent()) {
                throw new DuplicateResourceException("Student with email " + studentDetails.getEmail() + " already exists");
            }
        }
        
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setRoomNumber(studentDetails.getRoomNumber());
        student.setFeesPaid(studentDetails.getFeesPaid());
        
        return studentRepository.save(student);
    }
    
    @Transactional
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
    
    public List<Student> getStudentsByRoom(String roomNumber) {
        return studentRepository.findByRoomNumber(roomNumber);
    }
    
    public List<Student> getStudentsByFeeStatus(Boolean feesPaid) {
        return studentRepository.findByFeesPaid(feesPaid);
    }
    
    public long getTotalStudentsCount() {
        return studentRepository.count();
    }
    
    public long getUnpaidStudentsCount() {
        return studentRepository.countByFeesPaid(false);
    }
}
