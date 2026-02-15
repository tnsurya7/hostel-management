'use client';

import { useState } from 'react';
import { Student } from '@/types/student';

interface StudentFormEnhancedProps {
  onSubmit: (student: Student) => Promise<void>;
  initialData?: Student;
  isLoading?: boolean;
  onCancel?: () => void;
}

export default function StudentFormEnhanced({ onSubmit, initialData, isLoading, onCancel }: StudentFormEnhancedProps) {
  const [formData, setFormData] = useState<Student>(
    initialData || {
      name: '',
      email: '',
      phoneNumber: '',
      dateOfBirth: '',
      address: '',
      roomNumber: '',
      feesPaid: false,
      admissionDate: '',
      parentName: '',
      parentPhone: '',
      parentEmail: '',
      emergencyContact: '',
      bloodGroup: '',
      course: '',
      yearOfStudy: '',
      status: 'ACTIVE',
    }
  );

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      {/* Personal Information */}
      <div>
        <h3 className="text-lg font-semibold mb-4 text-gray-900 dark:text-white">Personal Information</h3>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label htmlFor="name" className="block text-sm font-medium mb-1">
              Full Name *
            </label>
            <input
              type="text"
              id="name"
              required
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
              placeholder="Enter full name"
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
            <label htmlFor="phoneNumber" className="block text-sm font-medium mb-1">
              Phone Number
            </label>
            <input
              type="tel"
              id="phoneNumber"
              value={formData.phoneNumber}
              onChange={(e) => setFormData({ ...formData, phoneNumber: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
              placeholder="+1234567890"
            />
          </div>

          <div>
            <label htmlFor="dateOfBirth" className="block text-sm font-medium mb-1">
              Date of Birth
            </label>
            <input
              type="date"
              id="dateOfBirth"
              value={formData.dateOfBirth}
              onChange={(e) => setFormData({ ...formData, dateOfBirth: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
            />
          </div>

          <div>
            <label htmlFor="bloodGroup" className="block text-sm font-medium mb-1">
              Blood Group
            </label>
            <select
              id="bloodGroup"
              value={formData.bloodGroup}
              onChange={(e) => setFormData({ ...formData, bloodGroup: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
            >
              <option value="">Select Blood Group</option>
              <option value="A+">A+</option>
              <option value="A-">A-</option>
              <option value="B+">B+</option>
              <option value="B-">B-</option>
              <option value="AB+">AB+</option>
              <option value="AB-">AB-</option>
              <option value="O+">O+</option>
              <option value="O-">O-</option>
            </select>
          </div>

          <div className="md:col-span-2">
            <label htmlFor="address" className="block text-sm font-medium mb-1">
              Address
            </label>
            <textarea
              id="address"
              value={formData.address}
              onChange={(e) => setFormData({ ...formData, address: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
              placeholder="Enter full address"
              rows={2}
            />
          </div>
        </div>
      </div>

      {/* Academic Information */}
      <div>
        <h3 className="text-lg font-semibold mb-4 text-gray-900 dark:text-white">Academic Information</h3>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label htmlFor="course" className="block text-sm font-medium mb-1">
              Course
            </label>
            <input
              type="text"
              id="course"
              value={formData.course}
              onChange={(e) => setFormData({ ...formData, course: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
              placeholder="e.g., Computer Science"
            />
          </div>

          <div>
            <label htmlFor="yearOfStudy" className="block text-sm font-medium mb-1">
              Year of Study
            </label>
            <select
              id="yearOfStudy"
              value={formData.yearOfStudy}
              onChange={(e) => setFormData({ ...formData, yearOfStudy: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
            >
              <option value="">Select Year</option>
              <option value="1st Year">1st Year</option>
              <option value="2nd Year">2nd Year</option>
              <option value="3rd Year">3rd Year</option>
              <option value="4th Year">4th Year</option>
            </select>
          </div>
        </div>
      </div>

      {/* Hostel Information */}
      <div>
        <h3 className="text-lg font-semibold mb-4 text-gray-900 dark:text-white">Hostel Information</h3>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
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

          <div>
            <label htmlFor="admissionDate" className="block text-sm font-medium mb-1">
              Admission Date
            </label>
            <input
              type="date"
              id="admissionDate"
              value={formData.admissionDate}
              onChange={(e) => setFormData({ ...formData, admissionDate: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
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
        </div>
      </div>

      {/* Parent/Guardian Information */}
      <div>
        <h3 className="text-lg font-semibold mb-4 text-gray-900 dark:text-white">Parent/Guardian Information</h3>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label htmlFor="parentName" className="block text-sm font-medium mb-1">
              Parent/Guardian Name
            </label>
            <input
              type="text"
              id="parentName"
              value={formData.parentName}
              onChange={(e) => setFormData({ ...formData, parentName: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
              placeholder="Enter parent name"
            />
          </div>

          <div>
            <label htmlFor="parentPhone" className="block text-sm font-medium mb-1">
              Parent Phone
            </label>
            <input
              type="tel"
              id="parentPhone"
              value={formData.parentPhone}
              onChange={(e) => setFormData({ ...formData, parentPhone: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
              placeholder="+1234567890"
            />
          </div>

          <div>
            <label htmlFor="parentEmail" className="block text-sm font-medium mb-1">
              Parent Email
            </label>
            <input
              type="email"
              id="parentEmail"
              value={formData.parentEmail}
              onChange={(e) => setFormData({ ...formData, parentEmail: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
              placeholder="parent@example.com"
            />
          </div>

          <div>
            <label htmlFor="emergencyContact" className="block text-sm font-medium mb-1">
              Emergency Contact
            </label>
            <input
              type="tel"
              id="emergencyContact"
              value={formData.emergencyContact}
              onChange={(e) => setFormData({ ...formData, emergencyContact: e.target.value })}
              className="w-full px-3 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-800"
              placeholder="+1234567890"
            />
          </div>
        </div>
      </div>

      {/* Action Buttons */}
      <div className="flex gap-3">
        <button
          type="submit"
          disabled={isLoading}
          className="flex-1 bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-lg transition disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {isLoading ? 'Saving...' : initialData ? 'Update Student' : 'Add Student'}
        </button>
        {onCancel && (
          <button
            type="button"
            onClick={onCancel}
            className="px-6 bg-gray-500 hover:bg-gray-600 text-white font-medium py-2 rounded-lg transition"
          >
            Cancel
          </button>
        )}
      </div>
    </form>
  );
}
