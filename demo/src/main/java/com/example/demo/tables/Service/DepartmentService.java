package com.example.demo.tables.Service;

import com.example.demo.tables.Entity.Department;
import com.example.demo.tables.Repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getDepartments(){
        return departmentRepository.findAll();
    }

    public void addDepartment(Department department){
        departmentRepository.save(department);
    }

    public void addDepartments(List<Department> departments){ departmentRepository.saveAll(departments);}

    public void deleteDepartment(Long departmentId){
        Department department = departmentRepository.findById(departmentId).orElseThrow(()->new IllegalStateException(
                "department with id " + departmentId + " does not exist"));
        if(!department.isEmpty()) throw new DataIntegrityViolationException("you cannot delete a department with employees");
        departmentRepository.deleteById(departmentId);
    }

    public void updateDepartment(Long departmentId, String name) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(()->new IllegalStateException(
                "department with id " + departmentId + " does not exist"));
        department.setName(name);
    }

}
