import { Component } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { Employee } from '../../models/employee';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent {
  employee: Employee = {} as Employee;

  constructor(
    private employeeService: EmployeeService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.employeeService.createEmployee(this.employee).subscribe({
      next: () => {
        this.router.navigate(['/employees']);
      },
      error: (error) => {
        console.error('Error adding employee:', error);
      }
    });
  }
}
