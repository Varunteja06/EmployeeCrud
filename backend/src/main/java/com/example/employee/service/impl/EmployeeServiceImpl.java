package com.example.employee.service.impl;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;
import com.example.employee.exception.EmployeeNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final Map<Long, Employee> employeeMap = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setId(idGenerator.incrementAndGet());
        employeeMap.put(employee.getId(), employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeMap.get(id);
        if (employee == null || employee.isDeleted()) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeMap.values().stream()
                .filter(emp -> !emp.isDeleted())
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        if (!employeeMap.containsKey(id)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setId(id);
        employeeMap.put(id, employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeMap.get(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        employee.setDeleted(true);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDepartment(String department) {
        return employeeMap.values().stream()
                .filter(emp -> !emp.isDeleted() && 
                       emp.getDepartment().equalsIgnoreCase(department))
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                .collect(Collectors.toList());
    }
}
