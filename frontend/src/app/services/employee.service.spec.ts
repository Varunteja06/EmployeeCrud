import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EmployeeService } from './employee.service';
import { Employee } from '../models/employee';
import { environment } from '../../environments/environment';

describe('EmployeeService', () => {
  let service: EmployeeService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [EmployeeService]
    });
    service = TestBed.inject(EmployeeService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all employees', () => {
    const mockEmployees: Employee[] = [
      { id: 1, name: 'John Doe', department: 'IT', email: 'john@example.com' },
      { id: 2, name: 'Jane Smith', department: 'HR', email: 'jane@example.com' }
    ];

    service.getEmployees().subscribe(employees => {
      expect(employees).toEqual(mockEmployees);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/employees`);
    expect(req.request.method).toBe('GET');
    req.flush(mockEmployees);
  });

  it('should get employee by id', () => {
    const mockEmployee: Employee = 
      { id: 1, name: 'John Doe', department: 'IT', email: 'john@example.com' };

    service.getEmployee(1).subscribe(employee => {
      expect(employee).toEqual(mockEmployee);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/employees/1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockEmployee);
  });

  it('should create employee', () => {
    const newEmployee: Employee = 
      { id: 1, name: 'John Doe', department: 'IT', email: 'john@example.com' };

    service.createEmployee(newEmployee).subscribe(employee => {
      expect(employee).toEqual(newEmployee);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/employees`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newEmployee);
    req.flush(newEmployee);
  });

  it('should update employee', () => {
    const updatedEmployee: Employee = 
      { id: 1, name: 'John Doe Updated', department: 'HR', email: 'john@example.com' };

    service.updateEmployee(1, updatedEmployee).subscribe(employee => {
      expect(employee).toEqual(updatedEmployee);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/employees/1`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(updatedEmployee);
    req.flush(updatedEmployee);
  });

  it('should delete employee', () => {
    service.deleteEmployee(1).subscribe(response => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/employees/1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('should get employees by department', () => {
    const mockEmployees: Employee[] = [
      { id: 1, name: 'John Doe', department: 'IT', email: 'john@example.com' },
      { id: 2, name: 'Jane Smith', department: 'IT', email: 'jane@example.com' }
    ];

    service.getEmployeesByDepartment('IT').subscribe(employees => {
      expect(employees).toEqual(mockEmployees);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/employees/department/IT`);
    expect(req.request.method).toBe('GET');
    req.flush(mockEmployees);
  });
});
