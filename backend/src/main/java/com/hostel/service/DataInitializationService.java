package com.hostel.service;

import com.hostel.model.*;
import com.hostel.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataInitializationService implements CommandLineRunner {
    
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;
    private final RoomAllocationRepository roomAllocationRepository;
    private final FeeTypeRepository feeTypeRepository;
    private final FeePaymentRepository feePaymentRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final ComplaintRepository complaintRepository;
    private final VisitorRepository visitorRepository;
    private final AttendanceRepository attendanceRepository;
    private final MessMenuRepository messMenuRepository;
    private final NotificationRepository notificationRepository;
    private final GatePassRepository gatePassRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    @Transactional
    public void run(String... args) {
        log.info("Starting data initialization...");
        
        // Check if data already exists
        if (userRepository.count() > 0) {
            log.info("Data already exists. Skipping initialization.");
            return;
        }
        
        try {
            createUsers();
            createFeeTypes();
            createRooms();
            createStudents();
            createFeePayments();
            createLeaveRequests();
            createComplaints();
            createVisitors();
            createAttendance();
            createMessMenu();
            createNotifications();
            createGatePasses();
            
            log.info("Data initialization completed successfully!");
        } catch (Exception e) {
            log.error("Error during data initialization", e);
        }
    }
    
    private void createUsers() {
        // Admin user
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("admin@hostel.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setFullName("Admin User");
        Set<String> adminRoles = new HashSet<>();
        adminRoles.add("ADMIN");
        admin.setRoles(adminRoles);
        userRepository.save(admin);
        
        // Student user
        User student = new User();
        student.setUsername("student");
        student.setEmail("student@hostel.com");
        student.setPassword(passwordEncoder.encode("student123"));
        student.setFullName("John Doe");
        Set<String> studentRoles = new HashSet<>();
        studentRoles.add("STUDENT");
        student.setRoles(studentRoles);
        userRepository.save(student);
        
        // Parent user
        User parent = new User();
        parent.setUsername("parent");
        parent.setEmail("parent@hostel.com");
        parent.setPassword(passwordEncoder.encode("parent123"));
        parent.setFullName("Parent User");
        Set<String> parentRoles = new HashSet<>();
        parentRoles.add("PARENT");
        parent.setRoles(parentRoles);
        userRepository.save(parent);
        
        // Warden user
        User warden = new User();
        warden.setUsername("warden");
        warden.setEmail("warden@hostel.com");
        warden.setPassword(passwordEncoder.encode("warden123"));
        warden.setFullName("Warden User");
        Set<String> wardenRoles = new HashSet<>();
        wardenRoles.add("WARDEN");
        warden.setRoles(wardenRoles);
        userRepository.save(warden);
        
        log.info("Created 4 users (admin, student, parent, warden)");
    }
    
    private void createFeeTypes() {
        String[] feeTypes = {"HOSTEL_FEE", "MESS_FEE", "SECURITY_DEPOSIT", "MAINTENANCE_FEE", "LAUNDRY_FEE"};
        Double[] amounts = {5000.0, 3000.0, 10000.0, 1000.0, 500.0};
        String[] frequencies = {"MONTHLY", "MONTHLY", "ONE_TIME", "SEMESTER", "MONTHLY"};
        
        for (int i = 0; i < feeTypes.length; i++) {
            FeeType feeType = new FeeType();
            feeType.setName(feeTypes[i]);
            feeType.setAmount(amounts[i]);
            feeType.setFrequency(frequencies[i]);
            feeType.setDescription("Standard " + feeTypes[i].replace("_", " ").toLowerCase());
            feeType.setIsMandatory(true);
            feeType.setIsActive(true);
            feeTypeRepository.save(feeType);
        }
        
        log.info("Created 5 fee types");
    }
    
    private void createRooms() {
        String[] blocks = {"A", "B", "C"};
        String[] roomTypes = {"SINGLE", "DOUBLE", "TRIPLE"};
        Integer[] capacities = {1, 2, 3};
        
        int roomCount = 0;
        for (String block : blocks) {
            for (int floor = 1; floor <= 3; floor++) {
                for (int i = 0; i < roomTypes.length; i++) {
                    roomCount++;
                    Room room = new Room();
                    room.setRoomNumber(block + floor + String.format("%02d", roomCount % 10 + 1));
                    room.setRoomType(roomTypes[i]);
                    room.setCapacity(capacities[i]);
                    room.setFloor(floor);
                    room.setBlock(block);
                    room.setCurrentOccupancy(0);
                    room.setStatus("AVAILABLE");
                    room.setRentAmount(5000.0 + (i * 1000));
                    room.setHasAc(i == 0);
                    room.setHasAttachedBathroom(true);
                    Room savedRoom = roomRepository.save(room);
                    
                    // Create beds for the room
                    for (int b = 1; b <= capacities[i]; b++) {
                        Bed bed = new Bed();
                        bed.setRoomId(savedRoom.getId());
                        bed.setBedNumber("B" + b);
                        bed.setIsOccupied(false);
                        bed.setStatus("AVAILABLE");
                        bedRepository.save(bed);
                    }
                }
            }
        }
        
        log.info("Created " + roomCount + " rooms with beds");
    }
    
    private void createStudents() {
        String[] names = {
            "John Doe", "Jane Smith", "Mike Johnson", "Sarah Williams", "David Brown",
            "Emily Davis", "James Wilson", "Emma Martinez", "Robert Anderson", "Olivia Taylor",
            "William Thomas", "Sophia Jackson", "Michael White", "Isabella Harris", "Daniel Martin"
        };
        
        String[] courses = {"Computer Science", "Mechanical Engineering", "Civil Engineering", "Electronics", "Business Administration"};
        String[] years = {"1st Year", "2nd Year", "3rd Year", "4th Year"};
        
        for (int i = 0; i < names.length; i++) {
            Student student = new Student();
            student.setName(names[i]);
            
            // First student should match the "student" user for login
            if (i == 0) {
                student.setEmail("student@hostel.com");
                student.setParentEmail("parent@hostel.com");
            } else {
                student.setEmail(names[i].toLowerCase().replace(" ", ".") + "@student.com");
                student.setParentEmail("parent." + names[i].toLowerCase().replace(" ", ".") + "@email.com");
            }
            
            student.setPhoneNumber("98765432" + String.format("%02d", i + 10));
            student.setDateOfBirth(LocalDate.of(2000 + (i % 5), (i % 12) + 1, (i % 28) + 1));
            student.setAddress(i + 1 + " Main Street, City " + (i % 5 + 1));
            student.setCourse(courses[i % courses.length]);
            student.setYearOfStudy(years[i % years.length]);
            student.setAdmissionDate(LocalDate.now().minusMonths(i));
            student.setParentName("Parent of " + names[i]);
            student.setParentPhone("99887766" + String.format("%02d", i + 10));
            student.setBloodGroup(new String[]{"A+", "B+", "O+", "AB+", "A-"}[i % 5]);
            student.setStatus("ACTIVE");
            student.setFeesPaid(i % 3 != 0); // Some students have unpaid fees
            
            // Allocate room to some students
            if (i < 10) {
                student.setRoomNumber("A10" + (i + 1));
            }
            
            studentRepository.save(student);
        }
        
        log.info("Created " + names.length + " students");
    }
    
    private void createFeePayments() {
        var students = studentRepository.findAll();
        var feeTypes = feeTypeRepository.findAll();
        
        int paymentCount = 0;
        for (Student student : students) {
            for (FeeType feeType : feeTypes) {
                FeePayment payment = new FeePayment();
                payment.setStudentId(student.getId());
                payment.setStudentName(student.getName());
                payment.setFeeTypeId(feeType.getId());
                payment.setFeeTypeName(feeType.getName());
                payment.setAmount(feeType.getAmount());
                payment.setLateFee(0.0);
                payment.setTotalAmount(feeType.getAmount());
                payment.setDueDate(LocalDate.now().plusDays(30));
                payment.setSemester("Spring 2026");
                payment.setAcademicYear("2025-2026");
                
                // Some payments are paid, some pending
                if (paymentCount % 3 == 0) {
                    payment.setStatus("PAID");
                    payment.setPaymentDate(LocalDate.now().minusDays(paymentCount % 10));
                    payment.setPaymentMethod("UPI");
                    payment.setTransactionId("TXN" + System.currentTimeMillis() + paymentCount);
                    payment.setReceiptNumber("RCP-" + String.format("%08d", paymentCount + 1));
                } else {
                    payment.setStatus("PENDING");
                }
                
                feePaymentRepository.save(payment);
                paymentCount++;
            }
        }
        
        log.info("Created " + paymentCount + " fee payments");
    }
    
    private void createLeaveRequests() {
        var students = studentRepository.findAll();
        String[] reasons = {"Family Function", "Medical Emergency", "Personal Work", "Festival", "Home Visit"};
        
        int count = 0;
        for (int i = 0; i < Math.min(10, students.size()); i++) {
            Student student = students.get(i);
            LeaveRequest leave = new LeaveRequest();
            leave.setStudentId(student.getId());
            leave.setStudentName(student.getName());
            leave.setReason(reasons[i % reasons.length]);
            leave.setFromDate(LocalDate.now().plusDays(i + 1));
            leave.setToDate(LocalDate.now().plusDays(i + 3));
            leave.setStatus(i % 3 == 0 ? "APPROVED" : "PENDING");
            leave.setParentConsent(i % 2 == 0);
            leaveRequestRepository.save(leave);
            count++;
        }
        
        log.info("Created " + count + " leave requests");
    }
    
    private void createComplaints() {
        var students = studentRepository.findAll();
        String[] categories = {"ROOM", "MESS", "MAINTENANCE", "SECURITY", "CLEANLINESS"};
        String[] titles = {
            "AC not working", "Food quality issue", "Water leakage", "Security concern", "Room not cleaned",
            "Broken furniture", "Mess timing issue", "Electrical problem", "Noise complaint", "Bathroom issue"
        };
        
        int count = 0;
        for (int i = 0; i < Math.min(10, students.size()); i++) {
            Student student = students.get(i);
            Complaint complaint = new Complaint();
            complaint.setStudentId(student.getId());
            complaint.setStudentName(student.getName());
            complaint.setTitle(titles[i % titles.length]);
            complaint.setDescription("Detailed description of " + titles[i % titles.length].toLowerCase());
            complaint.setCategory(categories[i % categories.length]);
            complaint.setPriority(i % 3 == 0 ? "HIGH" : "MEDIUM");
            complaint.setStatus(i % 4 == 0 ? "RESOLVED" : "OPEN");
            complaintRepository.save(complaint);
            count++;
        }
        
        log.info("Created " + count + " complaints");
    }
    
    private void createVisitors() {
        var students = studentRepository.findAll();
        
        int count = 0;
        for (int i = 0; i < Math.min(8, students.size()); i++) {
            Student student = students.get(i);
            Visitor visitor = new Visitor();
            visitor.setStudentId(student.getId());
            visitor.setStudentName(student.getName());
            visitor.setVisitorName("Visitor " + (i + 1));
            visitor.setVisitorPhone("98765" + String.format("%05d", i + 10000));
            visitor.setRelationship(i % 2 == 0 ? "Parent" : "Friend");
            visitor.setPurpose("Meeting");
            visitor.setStatus(i % 3 == 0 ? "APPROVED" : "PENDING");
            visitor.setPassNumber("VP-" + String.format("%08d", i + 1));
            visitorRepository.save(visitor);
            count++;
        }
        
        log.info("Created " + count + " visitor records");
    }
    
    private void createAttendance() {
        var students = studentRepository.findAll();
        
        int count = 0;
        for (Student student : students) {
            // Create attendance for last 10 days
            for (int i = 0; i < 10; i++) {
                Attendance attendance = new Attendance();
                attendance.setStudentId(student.getId());
                attendance.setStudentName(student.getName());
                attendance.setDate(LocalDate.now().minusDays(i));
                attendance.setStatus(i % 5 == 0 ? "ABSENT" : "PRESENT");
                attendanceRepository.save(attendance);
                count++;
            }
        }
        
        log.info("Created " + count + " attendance records");
    }
    
    private void createMessMenu() {
        String[] mealTypes = {"BREAKFAST", "LUNCH", "SNACKS", "DINNER"};
        String[][] menuItems = {
            {"Idli, Sambar, Chutney, Tea", "Poha, Tea, Banana"},
            {"Rice, Dal, Roti, Sabzi, Salad", "Rice, Sambar, Roti, Paneer Curry"},
            {"Tea, Biscuits, Samosa", "Coffee, Sandwich"},
            {"Rice, Roti, Dal, Sabzi, Curd", "Rice, Roti, Chicken Curry, Salad"}
        };
        
        int count = 0;
        for (int day = 0; day < 7; day++) {
            LocalDate date = LocalDate.now().plusDays(day);
            for (int i = 0; i < mealTypes.length; i++) {
                MessMenu menu = new MessMenu();
                menu.setDate(date);
                menu.setMealType(mealTypes[i]);
                menu.setMenuItems(menuItems[i][day % 2]);
                menu.setDayOfWeek(date.getDayOfWeek().toString());
                menu.setIsSpecial(day == 0); // Sunday special
                messMenuRepository.save(menu);
                count++;
            }
        }
        
        log.info("Created " + count + " mess menu items");
    }
    
    private void createNotifications() {
        String[] titles = {
            "Welcome to Hostel", "Fee Payment Reminder", "Mess Menu Updated", 
            "Maintenance Schedule", "Important Announcement"
        };
        String[] messages = {
            "Welcome to our hostel management system!",
            "Please pay your pending fees before the due date.",
            "New mess menu has been updated for this week.",
            "Scheduled maintenance on Sunday 10 AM.",
            "All students must attend the meeting on Monday."
        };
        
        for (int i = 0; i < titles.length; i++) {
            Notification notification = new Notification();
            notification.setTitle(titles[i]);
            notification.setMessage(messages[i]);
            notification.setType(i % 2 == 0 ? "INFO" : "ANNOUNCEMENT");
            notification.setTargetAudience("ALL");
            notification.setPriority(i == 4 ? "HIGH" : "NORMAL");
            notification.setIsRead(false);
            notification.setSentBy("Admin");
            notificationRepository.save(notification);
        }
        
        log.info("Created " + titles.length + " notifications");
    }
    
    private void createGatePasses() {
        var students = studentRepository.findAll();
        
        int count = 0;
        for (int i = 0; i < Math.min(5, students.size()); i++) {
            Student student = students.get(i);
            GatePass gatePass = new GatePass();
            gatePass.setStudentId(student.getId());
            gatePass.setStudentName(student.getName());
            gatePass.setPurpose("Going home for weekend");
            gatePass.setPassType(i % 2 == 0 ? "WEEKEND_PASS" : "DAY_PASS");
            gatePass.setFromTime(LocalDateTime.now().plusDays(i + 1));
            gatePass.setToTime(LocalDateTime.now().plusDays(i + 2));
            gatePass.setStatus(i % 3 == 0 ? "APPROVED" : "PENDING");
            gatePass.setPassNumber("GP-" + String.format("%08d", i + 1));
            gatePass.setQrCode("QR-" + System.currentTimeMillis() + i);
            gatePassRepository.save(gatePass);
            count++;
        }
        
        log.info("Created " + count + " gate passes");
    }
}
