'use client';

import { useState } from 'react';
import { Student } from '@/types/student';

interface StudentFormProps {
  onSubmit: (student: Student) => Promise<void>;
  initialData?: Student;
  isLoading?: boolean;
}

export default function StudentForm({ onSubmit, initialData, isLoading }: StudentFormProps) {
  const [formData, setFormData] = useState<Student>(
    initialData || {
      name: '',
      email: '',
      roomNumber: '',
      feesPaid: false,
    }
  );

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label htmlFor="name" className="block text-sm font-medium mb-1">
          Name *
        </label>
        <input
          type="text"
          id="name"
          required
          value={formData.name}
          onChange={(e) => setFormData({ ...formData, name: e.target.value })}
          className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
          placeholder="Enter student name"
        />
      </div>

      <div>
        <label htmlFor="email" className="block text-sm font-medium mb-1">
          Email *
        </label>
        <input
          type="email"
          id="email"
          required
          value={formData.email}
          onChange={(e) => setFormData({ ...formData, email: e.target.value })}
          className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
          placeholder="student@example.com"
        />
      </div>

      <div>
        <label htmlFor="roomNumber" className="block text-sm font-medium mb-1">
          Room Number
        </label>
        <input
          type="text"
          id="roomNumber"
          value={formData.roomNumber}
          onChange={(e) => setFormData({ ...formData, roomNumber: e.target.value })}
          className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
          placeholder="e.g., 101"
        />
      </div>

      <div className="flex items-center">
        <input
          type="checkbox"
          id="feesPaid"
          checked={formData.feesPaid}
          onChange={(e) => setFormData({ ...formData, feesPaid: e.target.checked })}
          className="w-4 h-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
        />
        <label htmlFor="feesPaid" className="ml-2 text-sm font-medium">
          Fees Paid
        </label>
      </div>

      <button
        type="submit"
        disabled={isLoading}
        className="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-lg transition disabled:opacity-50 disabled:cursor-not-allowed"
      >
        {isLoading ? 'Saving...' : initialData ? 'Update Student' : 'Add Student'}
      </button>
    </form>
  );
}
