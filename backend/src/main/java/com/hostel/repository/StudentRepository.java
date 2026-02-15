package com.hostel.repository;

import com.hostel.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
    List<Student> findByRoomNumber(String roomNumber);
    List<Student> findByFeesPaid(Boolean feesPaid);
    long countByFeesPaid(Boolean feesPaid);
}
