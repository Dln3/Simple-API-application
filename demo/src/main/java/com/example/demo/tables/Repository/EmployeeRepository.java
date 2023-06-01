package com.example.demo.tables.Repository;

import com.example.demo.tables.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.name = ?1")
    Optional<Employee> findEmployeeByName(String name);

    @Modifying
    @Query("UPDATE Employee e SET e.department.id = :departmentId WHERE e.id = :employeeId")
    void updateEmployeeDepartment(@Param("employeeId") Long employeeId, @Param("departmentId") Long departmentId);

}
