package com.hostel.service;

import com.hostel.exception.ResourceNotFoundException;
import com.hostel.model.MessMenu;
import com.hostel.repository.MessMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessMenuService {
    
    private final MessMenuRepository messMenuRepository;
    
    @Transactional
    public MessMenu createMessMenu(MessMenu messMenu) {
        return messMenuRepository.save(messMenu);
    }
    
    public List<MessMenu> getAllMessMenus() {
        return messMenuRepository.findAll();
    }
    
    public MessMenu getMessMenuById(Long id) {
        return messMenuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mess menu not found with id: " + id));
    }
    
    public List<MessMenu> getMessMenuByDate(LocalDate date) {
        return messMenuRepository.findByDate(date);
    }
    
    public List<MessMenu> getTodayMenu() {
        return messMenuRepository.findByDate(LocalDate.now());
    }
    
    @Transactional
    public MessMenu updateMessMenu(Long id, MessMenu messMenuDetails) {
        MessMenu messMenu = getMessMenuById(id);
        messMenu.setDate(messMenuDetails.getDate());
        messMenu.setMealType(messMenuDetails.getMealType());
        messMenu.setMenuItems(messMenuDetails.getMenuItems());
        messMenu.setDayOfWeek(messMenuDetails.getDayOfWeek());
        messMenu.setIsSpecial(messMenuDetails.getIsSpecial());
        messMenu.setRemarks(messMenuDetails.getRemarks());
        return messMenuRepository.save(messMenu);
    }
    
    @Transactional
    public void deleteMessMenu(Long id) {
        MessMenu messMenu = getMessMenuById(id);
        messMenuRepository.delete(messMenu);
    }
}
