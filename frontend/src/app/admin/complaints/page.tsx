'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import axios from 'axios';
import { API_ENDPOINTS } from '@/lib/api';

export default function AdminComplaintsPage() {
  const router = useRouter();
  const [complaints, setComplaints] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState('ALL');

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      router.push('/login');
      return;
    }
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    fetchComplaints();
  }, [router]);

  const fetchComplaints = async () => {
    try {
      const response = await axios.get(API_ENDPOINTS.COMPLAINTS);
      setComplaints(response.data);
    } catch (error) {
      console.error('Error fetching complaints:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleStatusChange = async (id: number, newStatus: string) => {
    try {
      const complaint: any = complaints.find((c: any) => c.id === id);
      if (!complaint) {
        alert('Complaint not found');
        return;
      }
      await axios.put(API_ENDPOINTS.COMPLAINT_BY_ID(id), {
        ...complaint,
        status: newStatus,
      });
      alert(`Complaint status updated to ${newStatus}!`);
      fetchComplaints();
    } catch (error) {
      console.error('Error updating complaint:', error);
      alert('Failed to update complaint status.');
    }
  };

  const filteredComplaints = complaints.filter((complaint: any) => {
    if (filter === 'ALL') return true;
    return complaint.status === filter;
  });

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600"></div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-white shadow">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex justify-between items-center">
            <h1 className="text-2xl font-bold text-gray-900">Complaints Management</h1>
            <button
              onClick={() => router.push('/dashboard/admin')}
              className="px-4 py-2 text-gray-600 hover:text-gray-900"
            >
              ← Back to Dashboard
            </button>
          </div>
        </div>
      </header>

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Filter Tabs */}
        <div className="bg-white rounded-lg shadow mb-6">
          <div className="flex border-b border-gray-200">
            {['ALL', 'OPEN', 'IN_PROGRESS', 'RESOLVED'].map((status) => (
              <button
                key={status}
                onClick={() => setFilter(status)}
                className={`px-6 py-3 font-medium text-sm ${
                  filter === status
                    ? 'border-b-2 border-indigo-600 text-indigo-600'
                    : 'text-gray-500 hover:text-gray-700'
                }`}
              >
                {status.replace('_', ' ')} ({complaints.filter((c: any) => status === 'ALL' || c.status === status).length})
              </button>
            ))}
          </div>
        </div>

        {/* Complaints Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredComplaints.map((complaint: any) => (
            <div key={complaint.id} className="bg-white rounded-lg shadow p-6">
              <div className="flex justify-between items-start mb-4">
                <h3 className="text-lg font-semibold text-gray-900">{complaint.title}</h3>
                <span className={`px-2 py-1 text-xs font-semibold rounded-full ${
                  complaint.priority === 'HIGH' ? 'bg-red-100 text-red-800' :
                  complaint.priority === 'MEDIUM' ? 'bg-yellow-100 text-yellow-800' :
                  'bg-green-100 text-green-800'
                }`}>
                  {complaint.priority}
                </span>
              </div>
              
              <p className="text-sm text-gray-600 mb-2">
                <strong>Student:</strong> {complaint.studentName}
              </p>
              <p className="text-sm text-gray-600 mb-2">
                <strong>Category:</strong> {complaint.category}
              </p>
              <p className="text-sm text-gray-700 mb-4 line-clamp-3">
                {complaint.description}
              </p>
              
              <div className="flex items-center justify-between">
                <span className={`px-2 py-1 text-xs font-semibold rounded-full ${
                  complaint.status === 'RESOLVED' ? 'bg-green-100 text-green-800' :
                  complaint.status === 'IN_PROGRESS' ? 'bg-blue-100 text-blue-800' :
                  'bg-yellow-100 text-yellow-800'
                }`}>
                  {complaint.status.replace('_', ' ')}
                </span>
                
                {complaint.status !== 'RESOLVED' && (
                  <select
                    value={complaint.status}
                    onChange={(e) => handleStatusChange(complaint.id, e.target.value)}
                    className="text-sm border border-gray-300 rounded px-2 py-1 text-gray-900 bg-white"
                  >
                    <option value="OPEN">Open</option>
                    <option value="IN_PROGRESS">In Progress</option>
                    <option value="RESOLVED">Resolved</option>
                  </select>
                )}
              </div>
            </div>
          ))}
        </div>

        {filteredComplaints.length === 0 && (
          <div className="bg-white rounded-lg shadow p-12 text-center">
            <p className="text-gray-500">No complaints found.</p>
          </div>
        )}
      </main>
    </div>
  );
}
