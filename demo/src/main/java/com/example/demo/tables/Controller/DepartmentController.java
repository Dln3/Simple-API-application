package com.example.demo.tables.Controller;

import com.example.demo.tables.Service.DepartmentService;
import com.example.demo.tables.Entity.Department;
import com.fasterxml.classmate.TypeBindings;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/department")
public class DepartmentController {

    private final DepartmentService departmentService;
    @Autowired
    public DepartmentController(DepartmentService departmentService){
        this.departmentService = departmentService;
    }
    @GetMapping
    @Operation(summary = "Get all departments")
    public List<Department> getDepartments(){
        return departmentService.getDepartments();
    }

    @GetMapping("/export")
    @Operation(summary = "Export departments to excel file")
    public void exportDepartments(HttpServletResponse response) throws IOException {
        // Retrieve employee data from the database
        List<Department> departments = departmentService.getDepartments();

        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a new sheet
        Sheet sheet = workbook.createSheet("Departments");

        // Populate the sheet with employee data
        int rowNum = 0;
        for (Department department : departments) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(department.getDepartmentId());
            row.createCell(1).setCellValue(department.getName());
        }

        // Set response headers
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=departments.xlsx");

        // Write the workbook to the response output stream
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @PostMapping("/import")
    @Operation(summary = "Upload departments from excel file")
    public void uploadDepartmentsData(@RequestBody String filePath) {
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath))) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            for (Row row : sheet) {
                String departmentName = row.getCell(0).getStringCellValue();

                // Create and save the Department entity using departmentRepository
                Department department = new Department(departmentName);
                departmentService.addDepartment(department);
            }

            System.out.println("Data uploaded successfully.");
        } catch (IOException e) {
            // Handle any potential exceptions here
            e.printStackTrace();
        }
    }

    @PostMapping
    @Operation(summary = "Create a new department")
    public void addDepartment(@RequestBody Department department){
        departmentService.addDepartment(department);
    }

    @DeleteMapping(path = "{departmentId}")
    @Operation(summary = "Delete a department by ID")
    public void deleteDepartment(@PathVariable("departmentId") Long departmentId){
        departmentService.deleteDepartment(departmentId);
    }

    @PutMapping(path = "{departmentId}")
    @Operation(summary = "Change the name of a department by ID")
    public void updateDepartment(@PathVariable("departmentId") Long departmentId,
                                 @RequestParam String name){
        departmentService.updateDepartment(departmentId, name);
    }
}
