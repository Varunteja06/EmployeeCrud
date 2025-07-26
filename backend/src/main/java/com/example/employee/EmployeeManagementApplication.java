package com.example.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.example.employee.service.EmployeeService;
import com.example.employee.dto.EmployeeDTO;

@SpringBootApplication
public class EmployeeManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataInitializer(EmployeeService employeeService) {
        return args -> {
            EmployeeDTO emp1 = new EmployeeDTO();
            emp1.setName("Alice");
            emp1.setDepartment("HR");
            emp1.setEmail("alice@example.com");
            employeeService.createEmployee(emp1);

            EmployeeDTO emp2 = new EmployeeDTO();
            emp2.setName("Bob");
            emp2.setDepartment("IT");
            emp2.setEmail("bob@example.com");
            employeeService.createEmployee(emp2);

            EmployeeDTO emp3 = new EmployeeDTO();
            emp3.setName("Charlie");
            emp3.setDepartment("Finance");
            emp3.setEmail("charlie@example.com");
            employeeService.createEmployee(emp3);
        };
    }
}
