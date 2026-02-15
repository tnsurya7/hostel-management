package com.hostel.controller;

import com.hostel.model.MessMenu;
import com.hostel.service.MessMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mess-menu")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Mess Menu Management", description = "APIs for managing mess menu")
public class MessMenuController {
    
    private final MessMenuService messMenuService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Create mess menu")
    public ResponseEntity<MessMenu> createMessMenu(@Valid @RequestBody MessMenu messMenu) {
        MessMenu createdMenu = messMenuService.createMessMenu(messMenu);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenu);
    }
    
    @GetMapping
    @Operation(summary = "Get all mess menus")
    public ResponseEntity<List<MessMenu>> getAllMessMenus() {
        List<MessMenu> menus = messMenuService.getAllMessMenus();
        return ResponseEntity.ok(menus);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get mess menu by ID")
    public ResponseEntity<MessMenu> getMessMenuById(@PathVariable Long id) {
        MessMenu menu = messMenuService.getMessMenuById(id);
        return ResponseEntity.ok(menu);
    }
    
    @GetMapping("/date/{date}")
    @Operation(summary = "Get mess menu by date")
    public ResponseEntity<List<MessMenu>> getMessMenuByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<MessMenu> menus = messMenuService.getMessMenuByDate(date);
        return ResponseEntity.ok(menus);
    }
    
    @GetMapping("/today")
    @Operation(summary = "Get today's mess menu")
    public ResponseEntity<List<MessMenu>> getTodayMenu() {
        List<MessMenu> menus = messMenuService.getTodayMenu();
        return ResponseEntity.ok(menus);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'WARDEN')")
    @Operation(summary = "Update mess menu")
    public ResponseEntity<MessMenu> updateMessMenu(@PathVariable Long id, @Valid @RequestBody MessMenu messMenu) {
        MessMenu updatedMenu = messMenuService.updateMessMenu(id, messMenu);
        return ResponseEntity.ok(updatedMenu);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete mess menu")
    public ResponseEntity<Map<String, String>> deleteMessMenu(@PathVariable Long id) {
        messMenuService.deleteMessMenu(id);
        return ResponseEntity.ok(Map.of("message", "Mess menu deleted successfully"));
    }
}
