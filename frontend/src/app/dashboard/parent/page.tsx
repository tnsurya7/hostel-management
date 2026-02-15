'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import axios from 'axios';

export default function ParentDashboard() {
  const router = useRouter();
  const [user, setUser] = useState<any>(null);
  const [childData, setChildData] = useState<any>(null);
  const [leaveRequests, setLeaveRequests] = useState([]);
  const [feePayments, setFeePayments] = useState([]);
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

    fetchParentData();
  }, [router]);

  const fetchParentData = async () => {
    try {
      const studentsRes = await axios.get('http://localhost:8080/api/students');
      
      // In real app, would link parent to specific child via parent email or ID
      // For demo, find student whose parent email matches logged-in user's email
      const userEmail = user?.email;
      let child = studentsRes.data.find((s: any) => s.parentEmail === userEmail);
      
      // If not found, use first student as fallback for demo
      if (!child && studentsRes.data.length > 0) {
        child = studentsRes.data[0];
      }
      
      setChildData(child);

      if (child) {
        const [leavesRes, feesRes] = await Promise.all([
          axios.get('http://localhost:8080/api/leave-requests'),
          axios.get(`http://localhost:8080/api/fees/payments/student/${child.id}`).catch(() => ({ data: [] })),
        ]);

        const childLeaves = leavesRes.data.filter((l: any) => l.studentId === child.id);
        setLeaveRequests(childLeaves);
        setFeePayments(feesRes.data);
      }
    } catch (error) {
      console.error('Error fetching parent data:', error);
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

  const pendingFees = feePayments.filter((f: any) => f.status === 'PENDING');
  const pendingLeaves = leaveRequests.filter((l: any) => l.status === 'PENDING');

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex justify-between items-center">
            <div>
              <h1 className="text-2xl font-bold text-gray-900">Parent Dashboard</h1>
              <p className="text-sm text-gray-600">Welcome, {user?.fullName}</p>
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
        {/* Child Info Card */}
        <div className="bg-white rounded-lg shadow p-6 mb-8">
          <h2 className="text-xl font-bold text-gray-900 mb-4">Child Information</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <p className="text-sm text-gray-600">Name</p>
              <p className="text-lg font-semibold text-gray-900">{childData?.name}</p>
            </div>
            <div>
              <p className="text-sm text-gray-600">Course</p>
              <p className="text-lg font-semibold text-gray-900">{childData?.course}</p>
            </div>
            <div>
              <p className="text-sm text-gray-600">Year of Study</p>
              <p className="text-lg font-semibold text-gray-900">{childData?.yearOfStudy}</p>
            </div>
            <div>
              <p className="text-sm text-gray-600">Room Number</p>
              <p className="text-lg font-semibold text-gray-900">{childData?.roomNumber || 'Not Assigned'}</p>
            </div>
            <div>
              <p className="text-sm text-gray-600">Email</p>
              <p className="text-lg font-semibold text-gray-900">{childData?.email}</p>
            </div>
            <div>
              <p className="text-sm text-gray-600">Phone</p>
              <p className="text-lg font-semibold text-gray-900">{childData?.phoneNumber}</p>
            </div>
          </div>
        </div>

        {/* Stats Grid */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <div className="bg-white rounded-lg shadow p-6">
            <div className="flex items-center">
              <div className="p-3 bg-yellow-100 rounded-full">
                <svg className="w-8 h-8 text-yellow-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              <div className="ml-4">
                <p className="text-sm text-gray-600">Pending Fees</p>
                <p className="text-2xl font-bold text-gray-900">{pendingFees.length}</p>
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
                <p className="text-sm text-gray-600">Pending Leaves</p>
                <p className="text-2xl font-bold text-gray-900">{pendingLeaves.length}</p>
              </div>
            </div>
          </div>

          <div className="bg-white rounded-lg shadow p-6">
            <div className="flex items-center">
              <div className="p-3 bg-green-100 rounded-full">
                <svg className="w-8 h-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              <div className="ml-4">
                <p className="text-sm text-gray-600">Total Leaves</p>
                <p className="text-2xl font-bold text-gray-900">{leaveRequests.length}</p>
              </div>
            </div>
          </div>
        </div>

        {/* Pending Leave Requests */}
        <div className="bg-white rounded-lg shadow mb-8">
          <div className="px-6 py-4 border-b border-gray-200">
            <h2 className="text-lg font-semibold text-gray-900">Pending Leave Requests</h2>
          </div>
          <div className="p-6">
            {pendingLeaves.length === 0 ? (
              <p className="text-gray-500 text-center py-4">No pending leave requests</p>
            ) : (
              <div className="space-y-4">
                {pendingLeaves.map((leave: any) => (
                  <div key={leave.id} className="border border-gray-200 rounded-lg p-4">
                    <div className="flex justify-between items-start">
                      <div>
                        <p className="font-semibold text-gray-900">{leave.reason}</p>
                        <p className="text-sm text-gray-600 mt-1">
                          From: {new Date(leave.fromDate).toLocaleDateString()} - To: {new Date(leave.toDate).toLocaleDateString()}
                        </p>
                      </div>
                      <span className="px-3 py-1 bg-yellow-100 text-yellow-800 text-xs font-semibold rounded-full">
                        {leave.status}
                      </span>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>

        {/* Pending Fee Payments */}
        <div className="bg-white rounded-lg shadow">
          <div className="px-6 py-4 border-b border-gray-200">
            <h2 className="text-lg font-semibold text-gray-900">Pending Fee Payments</h2>
          </div>
          <div className="overflow-x-auto">
            {pendingFees.length === 0 ? (
              <p className="text-gray-500 text-center py-8">No pending fees</p>
            ) : (
              <table className="min-w-full divide-y divide-gray-200">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Fee Type</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Amount</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Due Date</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Status</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Action</th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                  {pendingFees.map((fee: any) => (
                    <tr key={fee.id}>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{fee.feeTypeName}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">₹{fee.amount}</td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {new Date(fee.dueDate).toLocaleDateString()}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className="px-2 py-1 bg-yellow-100 text-yellow-800 text-xs font-semibold rounded-full">
                          {fee.status}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">
                        <button className="text-blue-600 hover:text-blue-800 font-medium">
                          Pay Now
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>
        </div>
      </main>
    </div>
  );
}
