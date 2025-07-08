package com.tmk.inventorymanager.employeeapi.employeeapi.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    @Test
    void testEmployeeConstructorAndGetters() {
        Employee emp = new Employee("John", "Doe", "john.doe@email.com", "IT");
        assertNull(emp.getId());
        assertEquals("John", emp.getFirstName());
        assertEquals("Doe", emp.getLastName());
        assertEquals("john.doe@email.com", emp.getEmail());
        assertEquals("IT", emp.getDepartment());
    }

    @Test
    void testSetters() {
        Employee emp = new Employee();
        emp.setId(1L);
        emp.setFirstName("Jane");
        emp.setLastName("Smith");
        emp.setEmail("jane.smith@email.com");
        emp.setDepartment("HR");
        assertEquals(1L, emp.getId());
        assertEquals("Jane", emp.getFirstName());
        assertEquals("Smith", emp.getLastName());
        assertEquals("jane.smith@email.com", emp.getEmail());
        assertEquals("HR", emp.getDepartment());
    }
}
