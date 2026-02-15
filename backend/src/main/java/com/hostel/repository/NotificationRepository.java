package com.hostel.repository;

import com.hostel.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByTargetAudience(String targetAudience);
    List<Notification> findByTargetUserId(Long targetUserId);
    List<Notification> findByType(String type);
    List<Notification> findByIsRead(Boolean isRead);
    List<Notification> findByTargetUserIdAndIsRead(Long targetUserId, Boolean isRead);
    List<Notification> findByPriority(String priority);
}
