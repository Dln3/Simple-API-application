package com.example.demo.tables.Controller;

import com.example.demo.tables.Service.EmployeeService;
import com.example.demo.tables.Entity.Employee;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Get all employees")
    public List<Employee> getEmployees(){
        return employeeService.getEmployee();
    }

    @PostMapping
    @Operation(summary = "Create a new employee")
    public void addEmployee(@RequestParam String name,
                            @RequestParam Long departmentId){
        employeeService.addEmployee(name,departmentId);
    }

    @DeleteMapping(path = "{id}")
    @Operation(summary = "Delete an employee by ID")
    public void deleteEmployee(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
    }

    @PutMapping(path = "{id}")
    @Operation(summary = "Change the name and/or the department of an employee using its ID")
    public void updateEmployee(
            @PathVariable("id") Long id,
            @RequestParam (required = false) String name,
            @RequestParam (required = false) Long departmentId){
        employeeService.updateEmployee(id, name, departmentId);
    }
}
