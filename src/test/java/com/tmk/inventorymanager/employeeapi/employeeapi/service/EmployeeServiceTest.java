package com.tmk.inventorymanager.employeeapi.employeeapi.service;

import com.tmk.inventorymanager.employeeapi.employeeapi.model.Employee;
import com.tmk.inventorymanager.employeeapi.employeeapi.repository.EmployeeRepository;
import com.tmk.inventorymanager.employeeapi.employeeapi.exception.DuplicateEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Arrays.asList(new Employee("John", "Doe", "john@email.com", "IT"));
        when(employeeRepository.findAll()).thenReturn(employees);
        List<Employee> result = employeeService.getAllEmployees();
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testGetEmployeeById_Found() {
        Employee emp = new Employee("Jane", "Smith", "jane@email.com", "HR");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        Optional<Employee> result = employeeService.getEmployeeById(1L);
        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getFirstName());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Employee> result = employeeService.getEmployeeById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateEmployee_Success() {
        Employee emp = new Employee("John", "Doe", "john@email.com", "IT");
        when(employeeRepository.existsByEmail(emp.getEmail())).thenReturn(false);
        when(employeeRepository.save(emp)).thenReturn(emp);
        Employee result = employeeService.createEmployee(emp);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testCreateEmployee_DuplicateEmail() {
        Employee emp = new Employee("John", "Doe", "john@email.com", "IT");
        when(employeeRepository.existsByEmail(emp.getEmail())).thenReturn(true);
        assertThrows(DuplicateEmailException.class, () -> employeeService.createEmployee(emp));
    }

    @Test
    void testUpdateEmployee_Found() {
        Employee emp = new Employee("John", "Doe", "john@email.com", "IT");
        Employee updated = new Employee("Jane", "Smith", "jane@email.com", "HR");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(employeeRepository.existsByEmail(updated.getEmail())).thenReturn(false);
        when(employeeRepository.save(any(Employee.class))).thenReturn(updated);
        Optional<Employee> result = employeeService.updateEmployee(1L, updated);
        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getFirstName());
    }

    @Test
    void testUpdateEmployee_NotFound() {
        Employee updated = new Employee("Jane", "Smith", "jane@email.com", "HR");
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Employee> result = employeeService.updateEmployee(1L, updated);
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateEmployee_DuplicateEmail() {
        Employee emp = new Employee("John", "Doe", "john@email.com", "IT");
        Employee updated = new Employee("Jane", "Smith", "jane@email.com", "HR");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
        when(employeeRepository.existsByEmail(updated.getEmail())).thenReturn(true);
        assertThrows(DuplicateEmailException.class, () -> employeeService.updateEmployee(1L, updated));
    }

    @Test
    void testDeleteEmployee_Found() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);
        boolean result = employeeService.deleteEmployee(1L);
        assertTrue(result);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.existsById(1L)).thenReturn(false);
        boolean result = employeeService.deleteEmployee(1L);
        assertFalse(result);
    }
}
