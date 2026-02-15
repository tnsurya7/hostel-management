'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import axios from 'axios';
import { API_ENDPOINTS } from '@/lib/api';

export default function StudentDashboard() {
  const router = useRouter();
  const [user, setUser] = useState<any>(null);
  const [studentData, setStudentData] = useState<any>(null);
  const [stats, setStats] = useState({
    attendance: 0,
    pendingFees: 0,
    leaveRequests: 0,
    complaints: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const userData = localStorage.getItem('user');
    
    if (!token || !userData) {
      router.push('/login');
      return;
    }

    const parsedUser = JSON.parse(userData);
    setUser(parsedUser);
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

    fetchStudentData();
  }, [router]);

  const fetchStudentData = async () => {
    try {
      // Get all students and find the one matching logged-in user's email
      const studentsRes = await axios.get(API_ENDPOINTS.STUDENTS);
      
      // Find student by matching email with logged-in user
      const userEmail = user?.email;
      let student = studentsRes.data.find((s: any) => s.email === userEmail);
      
      // If not found by email, use first student as fallback for demo
      if (!student && studentsRes.data.length > 0) {
        student = studentsRes.data[0];
      }
      
      setStudentData(student);

      if (student) {
        // Fetch data specific to this student
        const [feesRes, leavesRes, complaintsRes, attendanceRes] = await Promise.all([
          axios.get(API_ENDPOINTS.FEES.PAYMENTS_BY_STUDENT(student.id)).catch(() => ({ data: [] })),
          axios.get(API_ENDPOINTS.LEAVE_REQUESTS),
          axios.get(API_ENDPOINTS.COMPLAINTS),
          axios.get(API_ENDPOINTS.ATTENDANCE),
        ]);

        // Calculate stats for this specific student
        const pendingFees = feesRes.data.filter((f: any) => f.status === 'PENDING').length;
        const myLeaves = leavesRes.data.filter((l: any) => l.studentId === student.id);
        const myComplaints = complaintsRes.data.filter((c: any) => c.studentId === student.id);
        const myAttendance = attendanceRes.data.filter((a: any) => a.studentId === student.id);
        const presentDays = myAttendance.filter((a: any) => a.status === 'PRESENT').length;
        const attendancePercent = myAttendance.length > 0 ? (presentDays / myAttendance.length) * 100 : 0;

        setStats({
          attendance: Math.round(attendancePercent),
          pendingFees,
          leaveRequests: myLeaves.length,
          complaints: myComplaints.length,
        });
      }
    } catch (error) {
      console.error('Error fetching student data:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    router.push('/login');
  };

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex justify-between items-center">
            <div>
              <h1 className="text-2xl font-bold text-gray-900">Student Dashboard</h1>
              <p className="text-sm text-gray-600">Welcome back, {user?.fullName}</p>
            </div>
            <button
              onClick={handleLogout}
              className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors"
            >
              Logout
            </button>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Profile Card */}
        <div className="bg-white rounded-lg shadow p-6 mb-8">
          <div className="flex items-center">
            <div className="w-20 h-20 bg-indigo-100 rounded-full flex items-center justify-center">
              <span className="text-3xl font-bold text-indigo-600">
                {studentData?.name?.charAt(0) || 'S'}
              </span>
            </div>
            <div className="ml-6">
              <h2 className="text-2xl font-bold text-gray-900">{studentData?.name || 'Student Name'}</h2>
              <p className="text-gray-600">{studentData?.course || 'Course'} - {studentData?.yearOfStudy || 'Year'}</p>
              <p className="text-sm text-gray-500">Room: {studentData?.roomNumber || 'Not Assigned'}</p>
            </div>
          </div>
        </div>

        {/* Stats Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <div className="bg-white rounded-lg shadow p-6">
            <div className="flex items-center">
              <div className="p-3 bg-green-100 rounded-full">
                <svg className="w-8 h-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              <div className="ml-4">
                <p className="text-sm text-gray-600">Attendance</p>
                <p className="text-2xl font-bold text-gray-900">{stats.attendance}%</p>
              </div>
            </div>
          </div>

          <div className="bg-white rounded-lg shadow p-6">
            <div className="flex items-center">
              <div className="p-3 bg-yellow-100 rounded-full">
                <svg className="w-8 h-8 text-yellow-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              <div className="ml-4">
                <p className="text-sm text-gray-600">Pending Fees</p>
                <p className="text-2xl font-bold text-gray-900">{stats.pendingFees}</p>
              </div>
            </div>
          </div>

          <div className="bg-white rounded-lg shadow p-6">
            <div className="flex items-center">
              <div className="p-3 bg-purple-100 rounded-full">
                <svg className="w-8 h-8 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
                </svg>
              </div>
              <div className="ml-4">
                <p className="text-sm text-gray-600">Leave Requests</p>
                <p className="text-2xl font-bold text-gray-900">{stats.leaveRequests}</p>
              </div>
            </div>
          </div>

          <div className="bg-white rounded-lg shadow p-6">
            <div className="flex items-center">
              <div className="p-3 bg-red-100 rounded-full">
                <svg className="w-8 h-8 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                </svg>
              </div>
              <div className="ml-4">
                <p className="text-sm text-gray-600">My Complaints</p>
                <p className="text-2xl font-bold text-gray-900">{stats.complaints}</p>
              </div>
            </div>
          </div>
        </div>

        {/* Quick Actions */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          <div className="bg-white rounded-lg shadow p-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">Quick Actions</h3>
            <div className="space-y-3">
              <button
                onClick={() => router.push('/student/leave-request')}
                className="w-full py-2 px-4 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors text-left"
              >
                Apply for Leave
              </button>
              <button
                onClick={() => router.push('/student/gate-pass')}
                className="w-full py-2 px-4 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors text-left"
              >
                Request Gate Pass
              </button>
              <button
                onClick={() => router.push('/student/complaint')}
                className="w-full py-2 px-4 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors text-left"
              >
                File Complaint
              </button>
              <button
                onClick={() => router.push('/student/pay-fees')}
                className="w-full py-2 px-4 bg-yellow-600 text-white rounded-lg hover:bg-yellow-700 transition-colors text-left"
              >
                Pay Fees
              </button>
            </div>
          </div>

          <div className="bg-white rounded-lg shadow p-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">Personal Info</h3>
            <div className="space-y-2 text-sm">
              <p><span className="text-gray-600">Email:</span> <span className="text-gray-900">{studentData?.email}</span></p>
              <p><span className="text-gray-600">Phone:</span> <span className="text-gray-900">{studentData?.phoneNumber}</span></p>
              <p><span className="text-gray-600">Blood Group:</span> <span className="text-gray-900">{studentData?.bloodGroup}</span></p>
              <p><span className="text-gray-600">Status:</span> <span className="text-green-600 font-semibold">{studentData?.status}</span></p>
            </div>
          </div>

          <div className="bg-white rounded-lg shadow p-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">Parent Details</h3>
            <div className="space-y-2 text-sm">
              <p><span className="text-gray-600">Name:</span> <span className="text-gray-900">{studentData?.parentName}</span></p>
              <p><span className="text-gray-600">Phone:</span> <span className="text-gray-900">{studentData?.parentPhone}</span></p>
              <p><span className="text-gray-600">Email:</span> <span className="text-gray-900">{studentData?.parentEmail}</span></p>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
}
