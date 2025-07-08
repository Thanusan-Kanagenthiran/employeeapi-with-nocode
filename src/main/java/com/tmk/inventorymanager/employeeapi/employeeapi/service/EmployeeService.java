package com.tmk.inventorymanager.employeeapi.employeeapi.service;

import com.tmk.inventorymanager.employeeapi.employeeapi.model.Employee;
import com.tmk.inventorymanager.employeeapi.employeeapi.repository.EmployeeRepository;
import com.tmk.inventorymanager.employeeapi.employeeapi.exception.DuplicateEmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees");
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        logger.info("Fetching employee with id: {}", id);
        return employeeRepository.findById(id);
    }

    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public Employee createEmployee(Employee employee) {
        logger.info("Creating employee with email: {}", employee.getEmail());
        if (employeeRepository.existsByEmail(employee.getEmail())) {
            logger.warn("Attempt to create duplicate employee with email: {}", employee.getEmail());
            throw new DuplicateEmailException("Email already exists");
        }
        Employee saved = employeeRepository.save(employee);
        logger.debug("Employee created: {}", saved);
        return saved;
    }

    public Optional<Employee> updateEmployee(Long id, Employee employeeDetails) {
        logger.info("Updating employee with id: {}", id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            logger.warn("Employee with id {} not found for update", id);
            return Optional.empty();
        }
        Employee employee = optionalEmployee.get();
        // Only check for email uniqueness if the email is being changed
        if (!employee.getEmail().equals(employeeDetails.getEmail()) && employeeRepository.existsByEmail(employeeDetails.getEmail())) {
            logger.warn("Attempt to update employee id {} with duplicate email: {}", id, employeeDetails.getEmail());
            throw new DuplicateEmailException("Email already exists");
        }
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(employeeDetails.getDepartment());
        Employee updatedEmployee = employeeRepository.save(employee);
        logger.debug("Employee updated: {}", updatedEmployee);
        return Optional.of(updatedEmployee);
    }

    public boolean deleteEmployee(Long id) {
        logger.info("Deleting employee with id: {}", id);
        if (!employeeRepository.existsById(id)) {
            logger.warn("Employee with id {} not found for deletion", id);
            return false;
        }
        employeeRepository.deleteById(id);
        logger.debug("Employee with id {} deleted", id);
        return true;
    }
}
