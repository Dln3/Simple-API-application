package com.example.demo.tables.Entity;

import com.example.demo.tables.Entity.Department;
import jakarta.persistence.*;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee(){};

    public Employee(String name, Department department) {
        this.name = name;
        this.department = department;

    }

    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartment(Department department) {
        if(this.department != null) this.department.removeEmployee(this);
        department.addEmployee(this);
        this.department = department;
    }
}
