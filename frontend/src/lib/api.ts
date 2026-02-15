// API Configuration with environment variables
const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

export const API_ENDPOINTS = {
  // Base URL
  BASE: API_BASE_URL,
  
  // Auth endpoints
  AUTH: {
    LOGIN: `${API_BASE_URL}/api/auth/login`,
    SIGNUP: `${API_BASE_URL}/api/auth/signup`,
    ME: `${API_BASE_URL}/api/auth/me`,
  },
  
  // Student endpoints
  STUDENTS: `${API_BASE_URL}/api/students`,
  STUDENT_BY_ID: (id: number) => `${API_BASE_URL}/api/students/${id}`,
  
  // Room endpoints
  ROOMS: `${API_BASE_URL}/api/rooms`,
  ROOM_BY_ID: (id: number) => `${API_BASE_URL}/api/rooms/${id}`,
  
  // Fee endpoints
  FEES: {
    PAYMENTS: `${API_BASE_URL}/api/fees/payments`,
    PAYMENT_BY_ID: (id: number) => `${API_BASE_URL}/api/fees/payments/${id}`,
    PAYMENTS_BY_STUDENT: (studentId: number) => `${API_BASE_URL}/api/fees/payments/student/${studentId}`,
    TYPES: `${API_BASE_URL}/api/fees/types`,
  },
  
  // Leave request endpoints
  LEAVE_REQUESTS: `${API_BASE_URL}/api/leave-requests`,
  LEAVE_REQUEST_BY_ID: (id: number) => `${API_BASE_URL}/api/leave-requests/${id}`,
  
  // Complaint endpoints
  COMPLAINTS: `${API_BASE_URL}/api/complaints`,
  COMPLAINT_BY_ID: (id: number) => `${API_BASE_URL}/api/complaints/${id}`,
  
  // Gate pass endpoints
  GATE_PASSES: `${API_BASE_URL}/api/gate-passes`,
  GATE_PASS_BY_ID: (id: number) => `${API_BASE_URL}/api/gate-passes/${id}`,
  
  // Attendance endpoints
  ATTENDANCE: `${API_BASE_URL}/api/attendance`,
  
  // Mess menu endpoints
  MESS_MENU: `${API_BASE_URL}/api/mess-menu`,
  
  // Notification endpoints
  NOTIFICATIONS: `${API_BASE_URL}/api/notifications`,
  
  // Visitor endpoints
  VISITORS: `${API_BASE_URL}/api/visitors`,
};

export default API_BASE_URL;
