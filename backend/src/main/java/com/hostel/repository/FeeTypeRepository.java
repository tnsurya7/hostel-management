package com.hostel.repository;

import com.hostel.model.FeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeTypeRepository extends JpaRepository<FeeType, Long> {
    Optional<FeeType> findByName(String name);
    List<FeeType> findByIsActive(Boolean isActive);
    List<FeeType> findByIsMandatory(Boolean isMandatory);
    List<FeeType> findByFrequency(String frequency);
    boolean existsByName(String name);
}
