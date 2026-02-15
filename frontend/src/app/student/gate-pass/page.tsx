'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import axios from 'axios';
import { API_ENDPOINTS } from '@/lib/api';

export default function GatePassPage() {
  const router = useRouter();
  const [user, setUser] = useState<any>(null);
  const [studentData, setStudentData] = useState<any>(null);
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    purpose: '',
    passType: 'DAY_PASS',
    fromTime: '',
    toTime: '',
  });

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

    fetchStudentData(parsedUser.email);
  }, [router]);

  const fetchStudentData = async (email: string) => {
    try {
      const response = await axios.get(API_ENDPOINTS.STUDENTS);
      const student = response.data.find((s: any) => s.email === email);
      setStudentData(student);
    } catch (error) {
      console.error('Error fetching student data:', error);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const passNumber = `GP-${Date.now()}`;
      const qrCode = `QR-${Date.now()}`;

      await axios.post(API_ENDPOINTS.GATE_PASSES, {
        studentId: studentData.id,
        studentName: studentData.name,
        purpose: formData.purpose,
        passType: formData.passType,
        fromTime: new Date(formData.fromTime).toISOString(),
        toTime: new Date(formData.toTime).toISOString(),
        status: 'PENDING',
        passNumber,
        qrCode,
      });

      alert('Gate pass request submitted successfully!');
      router.push('/dashboard/student');
    } catch (error) {
      console.error('Error submitting gate pass:', error);
      alert('Failed to submit gate pass request. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow">
        <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex justify-between items-center">
            <h1 className="text-2xl font-bold text-gray-900">Request Gate Pass</h1>
            <button
              onClick={() => router.push('/dashboard/student')}
              className="px-4 py-2 text-gray-600 hover:text-gray-900"
            >
              ← Back to Dashboard
            </button>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="bg-white rounded-lg shadow p-6">
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Student Name
              </label>
              <input
                type="text"
                value={studentData?.name || ''}
                disabled
                className="w-full px-4 py-2 text-gray-900 bg-gray-100 border border-gray-300 rounded-lg"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Pass Type *
              </label>
              <select
                value={formData.passType}
                onChange={(e) => setFormData({ ...formData, passType: e.target.value })}
                className="w-full px-4 py-2 text-gray-900 bg-white border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                required
              >
                <option value="DAY_PASS">Day Pass</option>
                <option value="WEEKEND_PASS">Weekend Pass</option>
                <option value="EMERGENCY_PASS">Emergency Pass</option>
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Purpose *
              </label>
              <textarea
                value={formData.purpose}
                onChange={(e) => setFormData({ ...formData, purpose: e.target.value })}
                className="w-full px-4 py-2 text-gray-900 bg-white border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                rows={3}
                placeholder="Enter purpose of visit..."
                required
              />
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  From Date & Time *
                </label>
                <input
                  type="datetime-local"
                  value={formData.fromTime}
                  onChange={(e) => setFormData({ ...formData, fromTime: e.target.value })}
                  className="w-full px-4 py-2 text-gray-900 bg-white border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  To Date & Time *
                </label>
                <input
                  type="datetime-local"
                  value={formData.toTime}
                  onChange={(e) => setFormData({ ...formData, toTime: e.target.value })}
                  className="w-full px-4 py-2 text-gray-900 bg-white border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                  required
                />
              </div>
            </div>

            <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
              <p className="text-sm text-blue-800">
                <strong>Note:</strong> Your gate pass request will be sent for approval. 
                Once approved, you will receive a QR code that you need to show at the gate.
              </p>
            </div>

            <div className="flex gap-4">
              <button
                type="button"
                onClick={() => router.push('/dashboard/student')}
                className="flex-1 px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition-colors"
              >
                Cancel
              </button>
              <button
                type="submit"
                disabled={loading}
                className="flex-1 px-4 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition-colors disabled:opacity-50"
              >
                {loading ? 'Submitting...' : 'Submit Gate Pass Request'}
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
}
