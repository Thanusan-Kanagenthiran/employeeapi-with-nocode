package com.tmk.inventorymanager.employeeapi.employeeapi.controller;

import com.tmk.inventorymanager.employeeapi.employeeapi.model.Employee;
import com.tmk.inventorymanager.employeeapi.employeeapi.service.EmployeeService;
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
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    public EmployeeControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Arrays.asList(new Employee("John", "Doe", "john@email.com", "IT"));
        when(employeeService.getAllEmployees()).thenReturn(employees);
        List<Employee> result = employeeController.getAllEmployees();
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testGetEmployeeById_Found() {
        Employee emp = new Employee("Jane", "Smith", "jane@email.com", "HR");
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(emp));
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
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Employee> response = employeeController.getEmployeeById(1L);
        assertEquals(ResponseEntity.notFound().build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void testCreateEmployee() {
        Employee emp = new Employee("John", "Doe", "john@email.com", "IT");
        when(employeeService.existsByEmail(emp.getEmail())).thenReturn(false);
        when(employeeService.createEmployee(emp)).thenReturn(emp);
        ResponseEntity<?> response = employeeController.createEmployee(emp);
        assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        assertNotNull(response.getBody());
        if (response.getBody() instanceof Employee employee) {
            assertEquals("John", employee.getFirstName());
        } else {
            fail("Response body is not an Employee");
        }
    }

    @Test
    void testUpdateEmployee_Found() {
        Employee emp = new Employee("John", "Doe", "john@email.com", "IT");
        Employee updated = new Employee("Jane", "Smith", "jane@email.com", "HR");
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(emp));
        when(employeeService.existsByEmail(updated.getEmail())).thenReturn(false);
        when(employeeService.updateEmployee(1L, updated)).thenReturn(Optional.of(updated));
        ResponseEntity<?> response = employeeController.updateEmployee(1L, updated);
        assertEquals(ResponseEntity.ok().build().getStatusCode(), response.getStatusCode());
        assertNotNull(response.getBody());
        if (response.getBody() instanceof Employee employee) {
            assertEquals("Jane", employee.getFirstName());
        } else {
            fail("Response body is not an Employee");
        }
    }

    @Test
    void testUpdateEmployee_NotFound() {
        Employee updated = new Employee("Jane", "Smith", "jane@email.com", "HR");
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.empty());
        ResponseEntity<?> response = employeeController.updateEmployee(1L, updated);
        assertEquals(ResponseEntity.notFound().build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void testDeleteEmployee_Found() {
        when(employeeService.deleteEmployee(1L)).thenReturn(true);
        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);
        assertEquals(ResponseEntity.noContent().build().getStatusCode(), response.getStatusCode());
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeService.deleteEmployee(1L)).thenReturn(false);
        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);
        assertEquals(ResponseEntity.notFound().build().getStatusCode(), response.getStatusCode());
    }
}
