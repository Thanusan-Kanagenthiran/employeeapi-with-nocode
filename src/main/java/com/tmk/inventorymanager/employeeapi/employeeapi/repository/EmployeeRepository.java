package com.tmk.inventorymanager.employeeapi.employeeapi.repository;

import com.tmk.inventorymanager.employeeapi.employeeapi.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByEmail(String email);
}
