import axiosInstance from './axios';
import { Student, StudentStats, LeaveRequest } from '@/types/student';

export const studentApi = {
  // Get all students
  getAllStudents: async (): Promise<Student[]> => {
    const response = await axiosInstance.get('/students');
    return response.data;
  },

  // Get student by ID
  getStudentById: async (id: number): Promise<Student> => {
    const response = await axiosInstance.get(`/students/${id}`);
    return response.data;
  },

  // Create new student
  createStudent: async (student: Student): Promise<Student> => {
    const response = await axiosInstance.post('/students', student);
    return response.data;
  },

  // Update student
  updateStudent: async (id: number, student: Student): Promise<Student> => {
    const response = await axiosInstance.put(`/students/${id}`, student);
    return response.data;
  },

  // Delete student
  deleteStudent: async (id: number): Promise<void> => {
    await axiosInstance.delete(`/students/${id}`);
  },

  // Get students by room
  getStudentsByRoom: async (roomNumber: string): Promise<Student[]> => {
    const response = await axiosInstance.get(`/students/room/${roomNumber}`);
    return response.data;
  },

  // Get students by fee status
  getStudentsByFeeStatus: async (status: boolean): Promise<Student[]> => {
    const response = await axiosInstance.get(`/students/fees/${status}`);
    return response.data;
  },

  // Get statistics
  getStats: async (): Promise<StudentStats> => {
    const response = await axiosInstance.get('/students/stats');
    return response.data;
  },
};

export const leaveRequestApi = {
  // Get all leave requests
  getAllLeaveRequests: async (): Promise<LeaveRequest[]> => {
    const response = await axiosInstance.get('/leave-requests');
    return response.data;
  },

  // Get leave request by ID
  getLeaveRequestById: async (id: number): Promise<LeaveRequest> => {
    const response = await axiosInstance.get(`/leave-requests/${id}`);
    return response.data;
  },

  // Get leave requests by student
  getLeaveRequestsByStudent: async (studentId: number): Promise<LeaveRequest[]> => {
    const response = await axiosInstance.get(`/leave-requests/student/${studentId}`);
    return response.data;
  },

  // Get leave requests by status
  getLeaveRequestsByStatus: async (status: string): Promise<LeaveRequest[]> => {
    const response = await axiosInstance.get(`/leave-requests/status/${status}`);
    return response.data;
  },

  // Create leave request
  createLeaveRequest: async (leaveRequest: LeaveRequest): Promise<LeaveRequest> => {
    const response = await axiosInstance.post('/leave-requests', leaveRequest);
    return response.data;
  },

  // Approve leave request
  approveLeaveRequest: async (id: number, approvedBy: string, remarks: string): Promise<LeaveRequest> => {
    const response = await axiosInstance.put(`/leave-requests/${id}/approve`, { approvedBy, remarks });
    return response.data;
  },

  // Reject leave request
  rejectLeaveRequest: async (id: number, rejectedBy: string, remarks: string): Promise<LeaveRequest> => {
    const response = await axiosInstance.put(`/leave-requests/${id}/reject`, { rejectedBy, remarks });
    return response.data;
  },

  // Delete leave request
  deleteLeaveRequest: async (id: number): Promise<void> => {
    await axiosInstance.delete(`/leave-requests/${id}`);
  },
};
