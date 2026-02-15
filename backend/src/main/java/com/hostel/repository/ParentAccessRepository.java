package com.hostel.repository;

import com.hostel.model.ParentAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentAccessRepository extends JpaRepository<ParentAccess, Long> {
    List<ParentAccess> findByParentUserId(Long parentUserId);
    List<ParentAccess> findByStudentId(Long studentId);
    Optional<ParentAccess> findByParentUserIdAndStudentId(Long parentUserId, Long studentId);
    List<ParentAccess> findByIsActive(Boolean isActive);
}
