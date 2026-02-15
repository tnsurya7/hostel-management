export interface Student {
  id?: number;
  name: string;
  email: string;
  phoneNumber?: string;
  dateOfBirth?: string;
  address?: string;
  roomNumber?: string;
  feesPaid: boolean;
  admissionDate?: string;
  parentName?: string;
  parentPhone?: string;
  parentEmail?: string;
  emergencyContact?: string;
  bloodGroup?: string;
  course?: string;
  yearOfStudy?: string;
  status?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface LeaveRequest {
  id?: number;
  studentId: number;
  studentName?: string;
  fromDate: string;
  toDate: string;
  reason: string;
  status?: string;
  approvedBy?: string;
  approvalDate?: string;
  remarks?: string;
  parentConsent?: boolean;
  parentContact?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface StudentStats {
  totalStudents: number;
  unpaidStudents: number;
}
