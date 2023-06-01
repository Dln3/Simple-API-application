package com.example.demo.tables.Controller;

import com.example.demo.tables.Entity.Department;
import com.example.demo.tables.Service.DepartmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
public class DepartmentControllerTest {

    @InjectMocks
    private DepartmentController departmentController;

    @Mock
    private DepartmentService departmentService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
    }

    @Test
    public void testGetDepartments() throws Exception {
        // Create a list of departments for the mock response
        List<Department> departments = Arrays.asList(
                new Department(1L, "Department 1"),
                new Department(2L, "Department 2")
        );

        // Mock the service method to return the list of departments
        Mockito.when(departmentService.getDepartments()).thenReturn(departments);

        // Perform the GET request to the endpoint
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/department"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departmentId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Department 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].departmentId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Department 2"));

        // Verify that the service method was called
        Mockito.verify(departmentService, Mockito.times(1)).getDepartments();
    }
}

