package com.hostel.repository;

import com.hostel.model.MessMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessMenuRepository extends JpaRepository<MessMenu, Long> {
    List<MessMenu> findByDate(LocalDate date);
    List<MessMenu> findByMealType(String mealType);
    Optional<MessMenu> findByDateAndMealType(LocalDate date, String mealType);
    List<MessMenu> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<MessMenu> findByDayOfWeek(String dayOfWeek);
    List<MessMenu> findByIsSpecial(Boolean isSpecial);
}
