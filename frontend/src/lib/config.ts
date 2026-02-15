// API Configuration
export const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

export const API_ENDPOINTS = {
  AUTH: {
    LOGIN: `${API_URL}/api/auth/login`,
    SIGNUP: `${API_URL}/api/auth/signup`,
    ME: `${API_URL}/api/auth/me`,
  },
  STUDENTS: `${API_URL}/api/students`,
  ROOMS: `${API_URL}/api/rooms`,
  FEES: `${API_URL}/api/fees`,
  COMPLAINTS: `${API_URL}/api/complaints`,
  LEAVE_REQUESTS: `${API_URL}/api/leave-requests`,
  ATTENDANCE: `${API_URL}/api/attendance`,
  MESS_MENU: `${API_URL}/api/mess-menu`,
  NOTIFICATIONS: `${API_URL}/api/notifications`,
  GATE_PASSES: `${API_URL}/api/gate-passes`,
  VISITORS: `${API_URL}/api/visitors`,
};
