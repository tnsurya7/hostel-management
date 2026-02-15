package com.hostel.repository;

import com.hostel.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByCategory(String category);
    List<Announcement> findByIsActive(Boolean isActive);
    List<Announcement> findByTargetAudience(String targetAudience);
    List<Announcement> findByPriority(String priority);
}
