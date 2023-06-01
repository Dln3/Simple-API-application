package com.example.demo.tables;

import com.example.demo.tables.Controller.DepartmentController;
import com.example.demo.tables.Entity.Department;
import com.example.demo.tables.Entity.Employee;
import com.example.demo.tables.Repository.DepartmentRepository;
import com.example.demo.tables.Repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Config {

    @Bean
    CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository){
        return args -> {
            Department hello = new Department("hello");
            Department hello2 = new Department("hello2");
            Employee maria = new Employee("Maria", hello);
            maria.setDepartment(hello);
            Employee kyle = new Employee("Kyle", hello2);
            kyle.setDepartment(hello2);
            Department hello3 = new Department("hello3");
            departmentRepository.saveAll(List.of(hello,hello2,hello3));
            employeeRepository.saveAll(List.of(maria, kyle));
        };
    }
}
