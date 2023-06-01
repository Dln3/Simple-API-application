package com.example.demo.tables.Service;

import com.example.demo.tables.Entity.Department;
import com.example.demo.tables.Repository.DepartmentRepository;
import com.example.demo.tables.Entity.Employee;
import com.example.demo.tables.Repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployee(){
        return employeeRepository.findAll();
    }

    public void addEmployee(String name, Long departmentId){
        Optional<Employee> employeeOptional = employeeRepository.findEmployeeByName(name);
        if(employeeOptional.isPresent()){
            throw new IllegalStateException("name taken");
        }
        Department department = departmentRepository.findById(departmentId).orElseThrow(()->new IllegalStateException(
                "department with id " + departmentId + " does not exist"));
        Employee employee = new Employee(name, department);
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new IllegalStateException(
                "employee with id " + id + " does not exist"));
        Department department = employee.getDepartment();
        department.removeEmployee(employee);
        employeeRepository.deleteById(id);
    }

    public void updateEmployee(Long id, String name, Long departmentId){
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new IllegalStateException(
                            "employee with id " + id + " does not exist"));
        if(name!=null && name.length() > 0 && name!= employee.getName()){
            employee.setName(name);
        }
        if(departmentId!=null) {
            boolean exists = departmentRepository.existsById(departmentId);
            if(!exists) throw new IllegalStateException("department with id " + departmentId + " does not exist");
            employeeRepository.updateEmployeeDepartment(id, departmentId);
        }
    }
}
