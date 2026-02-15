package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.Attendance;
import com.hostel.model.Student;
import com.hostel.repository.AttendanceRepository;
import com.hostel.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    
    @Transactional
    public Attendance markAttendance(Attendance attendance) {
        Student student = studentRepository.findById(attendance.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + attendance.getStudentId()));
        
        attendance.setStudentName(student.getName());
        
        // Check if attendance already exists for this student and date
        attendanceRepository.findByStudentIdAndDate(attendance.getStudentId(), attendance.getDate())
                .ifPresent(existing -> {
                    throw new IllegalStateException("Attendance already marked for this date");
                });
        
        return attendanceRepository.save(attendance);
    }
    
    @Transactional
    public Attendance checkIn(Long studentId, LocalDate date) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        
        Attendance attendance = new Attendance();
        attendance.setStudentId(studentId);
        attendance.setStudentName(student.getName());
        attendance.setDate(date);
        attendance.setCheckInTime(LocalTime.now());
        attendance.setStatus("PRESENT");
        
        return attendanceRepository.save(attendance);
    }
    
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }
    
    public Attendance getAttendanceById(Long id) {
        return attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + id));
    }
    
    public List<Attendance> getAttendanceByStudentId(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }
    
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }
    
    public Long getAttendancePercentage(Long studentId) {
        Long presentDays = attendanceRepository.countPresentDays(studentId);
        Long absentDays = attendanceRepository.countAbsentDays(studentId);
        Long totalDays = presentDays + absentDays;
        
        if (totalDays == 0) return 0L;
        return (presentDays * 100) / totalDays;
    }
    
    @Transactional
    public void deleteAttendance(Long id) {
        Attendance attendance = getAttendanceById(id);
        attendanceRepository.delete(attendance);
    }
}
