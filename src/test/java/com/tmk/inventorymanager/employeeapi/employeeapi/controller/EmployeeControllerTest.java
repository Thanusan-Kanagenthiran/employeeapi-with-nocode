package com.tmk.inventorymanager.employeeapi.employeeapi.controller;

import com.tmk.inventorymanager.employeeapi.employeeapi.model.Employee;
import com.tmk.inventorymanager.employeeapi.employeeapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeController employeeController;

    public EmployeeControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Arrays.asList(new Employee("John", "Doe", "john@email.com", "IT"));
        when(employeeRepository.findAll()).thenReturn(employees);
        List<Employee> result = employeeController.getAllEmployees();
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testGetEmployeeById_Found() {
        Employee emp = new Employee("Jane", "Smith", "jane@email.com", "HR");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        ResponseEntity<Employee> response = employeeController.getEmployeeById(1L);
        assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        assertNotNull(response.getBody());
        if (response.getBody() != null) {
            assertEquals("Jane", response.getBody().getFirstName());
        } else {
            fail("Response body is null");
        }
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Employee> response = employeeController.getEmployeeById(1L);
        assertEquals(ResponseEntity.notFound().build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void testCreateEmployee() {
        Employee emp = new Employee("John", "Doe", "john@email.com", "IT");
        when(employeeRepository.save(emp)).thenReturn(emp);
        Employee result = employeeController.createEmployee(emp);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testUpdateEmployee_Found() {
        Employee emp = new Employee("John", "Doe", "john@email.com", "IT");
        Employee updated = new Employee("Jane", "Smith", "jane@email.com", "HR");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updated);
        ResponseEntity<Employee> response = employeeController.updateEmployee(1L, updated);
        assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        assertNotNull(response.getBody());
        if (response.getBody() != null) {
            assertEquals("Jane", response.getBody().getFirstName());
        } else {
            fail("Response body is null");
        }
    }

    @Test
    void testUpdateEmployee_NotFound() {
        Employee updated = new Employee("Jane", "Smith", "jane@email.com", "HR");
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Employee> response = employeeController.updateEmployee(1L, updated);
        assertEquals(ResponseEntity.notFound().build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void testDeleteEmployee_Found() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);
        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);
        assertEquals(ResponseEntity.noContent().build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);
        assertEquals(ResponseEntity.notFound().build().getStatusCode(), response.getStatusCode());
    }
}
