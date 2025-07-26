package com.example.employee.controller;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private EmployeeService employeeService;
    
    @Test
    public void getEmployee_ShouldReturnEmployee() throws Exception {
        EmployeeDTO dto = new EmployeeDTO();
        when(employeeService.getEmployeeById(any())).thenReturn(dto);
        
        mockMvc.perform(get("/api/employees/1"))
               .andExpect(status().isOk());
    }

    @Test
    public void createEmployee_ShouldReturnCreatedEmployee() throws Exception {
        EmployeeDTO dto = new EmployeeDTO();
        when(employeeService.createEmployee(any())).thenReturn(dto);
        
        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John\",\"department\":\"IT\",\"email\":\"john@example.com\"}"))
               .andExpect(status().isOk());
    }

    @Test
    public void getEmployeesByDepartment_ShouldReturnEmployeesList() throws Exception {
        List<EmployeeDTO> dtos = new ArrayList<>();
        when(employeeService.getEmployeesByDepartment(any())).thenReturn(dtos);
        
        mockMvc.perform(get("/api/employees/department/IT"))
               .andExpect(status().isOk());
    }

    @Test
    public void deleteEmployee_ShouldReturnNoContent() throws Exception {
        doNothing().when(employeeService).deleteEmployee(any());
        
        mockMvc.perform(delete("/api/employees/1"))
               .andExpect(status().isNoContent());
    }
}
