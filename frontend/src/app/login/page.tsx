'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';
import axios from 'axios';
import { API_ENDPOINTS } from '@/lib/api';

export default function LoginPage() {
  const router = useRouter();
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });
  const [userType, setUserType] = useState<'admin' | 'student' | 'parent'>('admin');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  // Quick login credentials
  const quickLogins = {
    admin: { username: 'admin', password: 'admin123' },
    student: { username: 'student', password: 'student123' },
    parent: { username: 'parent', password: 'parent123' },
  };

  const handleQuickLogin = (type: 'admin' | 'student' | 'parent') => {
    setUserType(type);
    setFormData(quickLogins[type]);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const response = await axios.post(API_ENDPOINTS.AUTH.LOGIN, formData);
      
      console.log('Login response:', response.data);
      
      // Store token and user info
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('userId', response.data.id);
      localStorage.setItem('user', JSON.stringify(response.data));
      
      // Set axios default header
      axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
      
      // Small delay to ensure storage is complete
      await new Promise(resolve => setTimeout(resolve, 100));
      
      // Redirect based on role (roles come without ROLE_ prefix from backend)
      const roles = response.data.roles || [];
      console.log('User roles:', roles);
      
      // Check for roles (backend returns them as ADMIN, STUDENT, PARENT, not ROLE_ADMIN)
      if (roles.includes('ADMIN') || roles.includes('ROLE_ADMIN')) {
        console.log('Redirecting to admin dashboard');
        window.location.href = '/dashboard/admin';
      } else if (roles.includes('STUDENT') || roles.includes('ROLE_STUDENT')) {
        console.log('Redirecting to student dashboard');
        window.location.href = '/dashboard/student';
      } else if (roles.includes('PARENT') || roles.includes('ROLE_PARENT')) {
        console.log('Redirecting to parent dashboard');
        window.location.href = '/dashboard/parent';
      } else {
        console.log('Redirecting to home');
        window.location.href = '/';
      }
    } catch (err: any) {
      console.error('Login error:', err);
      setError(err.response?.data?.message || 'Login failed. Please check your credentials.');
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 flex items-center justify-center p-4">
      <div className="max-w-md w-full">
        {/* Logo and Title */}
        <div className="text-center mb-8">
          <div className="inline-block p-3 bg-indigo-600 rounded-full mb-4">
            <svg className="w-12 h-12 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
            </svg>
          </div>
          <h1 className="text-3xl font-bold text-gray-900">Hostel Management</h1>
          <p className="text-gray-600 mt-2">Sign in to your account</p>
        </div>

        {/* Login Card */}
        <div className="bg-white rounded-lg shadow-xl p-8">
          {/* User Type Selector */}
          <div className="flex gap-2 mb-6">
            <button
              type="button"
              onClick={() => setUserType('admin')}
              className={`flex-1 py-2 px-4 rounded-lg font-medium transition-colors ${
                userType === 'admin'
                  ? 'bg-indigo-600 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              Admin
            </button>
            <button
              type="button"
              onClick={() => setUserType('student')}
              className={`flex-1 py-2 px-4 rounded-lg font-medium transition-colors ${
                userType === 'student'
                  ? 'bg-indigo-600 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              Student
            </button>
            <button
              type="button"
              onClick={() => setUserType('parent')}
              className={`flex-1 py-2 px-4 rounded-lg font-medium transition-colors ${
                userType === 'parent'
                  ? 'bg-indigo-600 text-white'
                  : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              Parent
            </button>
          </div>

          {/* Quick Login Buttons */}
          <div className="mb-6 p-4 bg-blue-50 rounded-lg">
            <p className="text-sm text-gray-600 mb-2">Quick Login (Demo):</p>
            <div className="flex gap-2">
              <button
                type="button"
                onClick={() => handleQuickLogin('admin')}
                className="flex-1 py-2 px-3 bg-blue-600 text-white text-sm rounded hover:bg-blue-700 transition-colors"
              >
                Admin Demo
              </button>
              <button
                type="button"
                onClick={() => handleQuickLogin('student')}
                className="flex-1 py-2 px-3 bg-green-600 text-white text-sm rounded hover:bg-green-700 transition-colors"
              >
                Student Demo
              </button>
              <button
                type="button"
                onClick={() => handleQuickLogin('parent')}
                className="flex-1 py-2 px-3 bg-purple-600 text-white text-sm rounded hover:bg-purple-700 transition-colors"
              >
                Parent Demo
              </button>
            </div>
          </div>

          {/* Error Message */}
          {error && (
            <div className="mb-4 p-3 bg-red-50 border border-red-200 rounded-lg">
              <p className="text-sm text-red-600">{error}</p>
            </div>
          )}

          {/* Login Form */}
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Username
              </label>
              <input
                type="text"
                value={formData.username}
                onChange={(e) => setFormData({ ...formData, username: e.target.value })}
                className="w-full px-4 py-3 text-gray-900 bg-white border-2 border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition-all"
                placeholder="Enter your username"
                required
                autoComplete="username"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Password
              </label>
              <input
                type="password"
                value={formData.password}
                onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                className="w-full px-4 py-3 text-gray-900 bg-white border-2 border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition-all"
                placeholder="Enter your password"
                required
                autoComplete="current-password"
              />
            </div>

            <button
              type="submit"
              disabled={loading}
              className="w-full py-3 px-4 bg-indigo-600 text-white font-semibold text-base rounded-lg hover:bg-indigo-700 focus:outline-none focus:ring-4 focus:ring-indigo-300 transition-all disabled:opacity-50 disabled:cursor-not-allowed shadow-lg hover:shadow-xl"
            >
              {loading ? (
                <span className="flex items-center justify-center">
                  <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  Signing in...
                </span>
              ) : (
                'Sign In'
              )}
            </button>
          </form>

          {/* Demo Credentials */}
          <div className="mt-6 p-4 bg-gray-50 rounded-lg">
            <p className="text-xs text-gray-600 font-medium mb-2">Demo Credentials:</p>
            <div className="space-y-1 text-xs text-gray-500">
              <p><strong>Admin:</strong> admin / admin123</p>
              <p><strong>Student:</strong> student / student123</p>
              <p><strong>Parent:</strong> parent / parent123</p>
            </div>
          </div>
        </div>

        {/* Footer */}
        <p className="text-center text-sm text-gray-600 mt-6">
          Hostel Management System v1.0 - All 20 Features Active
        </p>
      </div>
    </div>
  );
}
