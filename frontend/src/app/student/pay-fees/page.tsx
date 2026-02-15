'use client';

import { useState, useEffect } from 'react';
import { useRouter } from 'next/navigation';
import axios from 'axios';
import { API_ENDPOINTS } from '@/lib/api';

export default function PayFeesPage() {
  const router = useRouter();
  const [user, setUser] = useState<any>(null);
  const [studentData, setStudentData] = useState<any>(null);
  const [pendingFees, setPendingFees] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [paying, setPaying] = useState(false);
  const [selectedFee, setSelectedFee] = useState<any>(null);
  const [paymentMethod, setPaymentMethod] = useState('UPI');

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

    fetchData(parsedUser.email);
  }, [router]);

  const fetchData = async (email: string) => {
    try {
      const studentsRes = await axios.get(API_ENDPOINTS.STUDENTS);
      const student = studentsRes.data.find((s: any) => s.email === email);
      setStudentData(student);

      if (student) {
        const feesRes = await axios.get(API_ENDPOINTS.FEES.PAYMENTS_BY_STUDENT(student.id));
        const pending = feesRes.data.filter((f: any) => f.status === 'PENDING');
        setPendingFees(pending);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setLoading(false);
    }
  };

  const handlePayment = async (fee: any) => {
    setPaying(true);
    setSelectedFee(fee);

    try {
      // Simulate payment processing
      await new Promise(resolve => setTimeout(resolve, 2000));

      // Update fee payment status
      await axios.put(API_ENDPOINTS.FEES.PAYMENT_BY_ID(fee.id), {
        ...fee,
        status: 'PAID',
        paymentDate: new Date().toISOString().split('T')[0],
        paymentMethod: paymentMethod,
        transactionId: `TXN${Date.now()}`,
        receiptNumber: `RCP-${Date.now()}`,
      });

      alert('Payment successful!');
      fetchData(user.email); // Refresh data
      setSelectedFee(null);
    } catch (error) {
      console.error('Error processing payment:', error);
      alert('Payment failed. Please try again.');
    } finally {
      setPaying(false);
    }
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
        <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex justify-between items-center">
            <h1 className="text-2xl font-bold text-gray-900">Pay Fees</h1>
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
      <main className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Student Info */}
        <div className="bg-white rounded-lg shadow p-6 mb-6">
          <h2 className="text-lg font-semibold text-gray-900 mb-4">Student Information</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <p className="text-sm text-gray-600">Name</p>
              <p className="text-base font-medium text-gray-900">{studentData?.name}</p>
            </div>
            <div>
              <p className="text-sm text-gray-600">Course</p>
              <p className="text-base font-medium text-gray-900">{studentData?.course}</p>
            </div>
            <div>
              <p className="text-sm text-gray-600">Room Number</p>
              <p className="text-base font-medium text-gray-900">{studentData?.roomNumber || 'Not Assigned'}</p>
            </div>
          </div>
        </div>

        {/* Pending Fees */}
        <div className="bg-white rounded-lg shadow">
          <div className="px-6 py-4 border-b border-gray-200">
            <h2 className="text-lg font-semibold text-gray-900">
              Pending Fees ({pendingFees.length})
            </h2>
          </div>

          {pendingFees.length === 0 ? (
            <div className="p-8 text-center">
              <svg className="mx-auto h-12 w-12 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              <p className="mt-4 text-lg font-medium text-gray-900">All fees paid!</p>
              <p className="mt-2 text-sm text-gray-600">You have no pending fee payments.</p>
            </div>
          ) : (
            <div className="overflow-x-auto">
              <table className="min-w-full divide-y divide-gray-200">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Fee Type</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Amount</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Due Date</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Semester</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Action</th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                  {pendingFees.map((fee) => (
                    <tr key={fee.id} className="hover:bg-gray-50">
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                        {fee.feeTypeName}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        ₹{fee.totalAmount || fee.amount}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                        {new Date(fee.dueDate).toLocaleDateString()}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {fee.semester}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm">
                        <button
                          onClick={() => handlePayment(fee)}
                          disabled={paying && selectedFee?.id === fee.id}
                          className="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors disabled:opacity-50"
                        >
                          {paying && selectedFee?.id === fee.id ? 'Processing...' : 'Pay Now'}
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>

        {/* Payment Method Selection */}
        {pendingFees.length > 0 && (
          <div className="mt-6 bg-white rounded-lg shadow p-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">Payment Method</h3>
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
              {['UPI', 'Credit Card', 'Debit Card', 'Net Banking'].map((method) => (
                <button
                  key={method}
                  onClick={() => setPaymentMethod(method)}
                  className={`p-4 border-2 rounded-lg transition-colors ${
                    paymentMethod === method
                      ? 'border-indigo-600 bg-indigo-50'
                      : 'border-gray-300 hover:border-gray-400'
                  }`}
                >
                  <p className="text-sm font-medium text-gray-900">{method}</p>
                </button>
              ))}
            </div>
          </div>
        )}

        {/* Info Box */}
        <div className="mt-6 bg-blue-50 border border-blue-200 rounded-lg p-4">
          <p className="text-sm text-blue-800">
            <strong>Note:</strong> After successful payment, you will receive a receipt via email. 
            Keep the receipt for your records.
          </p>
        </div>
      </main>
    </div>
  );
}
