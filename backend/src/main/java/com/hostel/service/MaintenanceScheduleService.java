package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.MaintenanceSchedule;
import com.hostel.repository.MaintenanceScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceScheduleService {
    
    private final MaintenanceScheduleRepository maintenanceScheduleRepository;
    
    @Transactional
    public MaintenanceSchedule createSchedule(MaintenanceSchedule schedule) {
        schedule.setStatus("SCHEDULED");
        return maintenanceScheduleRepository.save(schedule);
    }
    
    public List<MaintenanceSchedule> getAllSchedules() {
        return maintenanceScheduleRepository.findAll();
    }
    
    public MaintenanceSchedule getScheduleById(Long id) {
        return maintenanceScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance schedule not found with id: " + id));
    }
    
    public List<MaintenanceSchedule> getSchedulesByStatus(String status) {
        return maintenanceScheduleRepository.findByStatus(status);
    }
    
    public List<MaintenanceSchedule> getSchedulesByDate(LocalDate date) {
        return maintenanceScheduleRepository.findByScheduledDate(date);
    }
    
    public List<MaintenanceSchedule> getTodaySchedules() {
        return maintenanceScheduleRepository.findByScheduledDate(LocalDate.now());
    }
    
    @Transactional
    public MaintenanceSchedule updateStatus(Long id, String status) {
        MaintenanceSchedule schedule = getScheduleById(id);
        schedule.setStatus(status);
        
        if ("COMPLETED".equals(status) && schedule.getCompletedDate() == null) {
            schedule.setCompletedDate(LocalDate.now());
        }
        
        return maintenanceScheduleRepository.save(schedule);
    }
    
    @Transactional
    public MaintenanceSchedule updateSchedule(Long id, MaintenanceSchedule scheduleDetails) {
        MaintenanceSchedule schedule = getScheduleById(id);
        schedule.setTitle(scheduleDetails.getTitle());
        schedule.setDescription(scheduleDetails.getDescription());
        schedule.setMaintenanceType(scheduleDetails.getMaintenanceType());
        schedule.setCategory(scheduleDetails.getCategory());
        schedule.setLocation(scheduleDetails.getLocation());
        schedule.setScheduledDate(scheduleDetails.getScheduledDate());
        schedule.setAssignedTo(scheduleDetails.getAssignedTo());
        schedule.setPriority(scheduleDetails.getPriority());
        schedule.setEstimatedCost(scheduleDetails.getEstimatedCost());
        return maintenanceScheduleRepository.save(schedule);
    }
    
    @Transactional
    public void deleteSchedule(Long id) {
        MaintenanceSchedule schedule = getScheduleById(id);
        maintenanceScheduleRepository.delete(schedule);
    }
}
