package com.sujith.project.controller;

import com.sujith.project.controller_test_files.*;
import com.sujith.project.exceptions.*;
import com.sujith.project.jpaservice.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;

import java.nio.charset.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @MockBean
    EmployeeJpaService employeeService;
    @Autowired
    EmployeeController employeeController;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("getEmployeeById_valid")
    void getEmployeeById_valid() throws Exception {
        when(employeeService.findById(1)).thenReturn(EmployeeDtoObject.getStr());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/1"))
                .andExpect(status().isOk()).andExpect(result -> EmployeeDtoObject.getStr());
    }

    @Test
    @DisplayName("getEmployeeById_throwApiRequestException")
    void getEmployeeById_throwApiRequestException() throws Exception {
        when(employeeService.findById(15)).thenThrow(new ApiRequestException("Employee not found with given id : 15"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/15"))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.message")
                        .value("Employee not found with given id : 15"));
    }

    @Test
    @DisplayName("getEmployeeById_throwApiRequestException <=0")
    void getEmployeeById_throwApiRequestException1() throws Exception {
        when(employeeService.findById(0))
                .thenThrow(new ApiRequestException("Employee not found with given id : 0 id is less than or equal to zero"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/0"))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.message")
                        .value("Employee not found with given id : 0 id is less than or equal to zero"));
    }

    @Test
    @DisplayName("getMaxSalary_valid")
    void getMaxSalary_valid() throws Exception {
        when(employeeService.maxSalary()).thenReturn(70000);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/maxSalary"))
                .andExpect(status().isOk()).andExpect(content().json("70000"));
    }

    @Test
    @DisplayName("getMaxSalary_throwApiRequestException")
    void getMaxSalary_throwApiRequestException() throws Exception {
        when(employeeService.maxSalary()).thenThrow(new ApiRequestException("No Employees were present in database"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/maxSalary"))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.message")
                        .value("No Employees were present in database"));
    }

    @Test
    @DisplayName("getMaxSalaryByDepartment_valid")
    void getMaxSalaryByDepartment_valid() throws Exception {
        when(employeeService.maxInDept("IT")).thenReturn(45000);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/maxSalary")
                .param("departmentName", "IT")).andExpect(status().isOk()).andExpect(content().json("45000"));
    }

    @Test
    @DisplayName("getMaxSalaryByDepartment_throwApiRequestException")
    void getMaxSalaryByDepartment_throwApiRequestException() throws Exception {
        when(employeeService.maxInDept("IIT"))
                .thenThrow(new ApiRequestException("Department does not exist"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department/maxSalary")
                        .param("departmentName", "IIT")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Department does not exist"));
    }

    @Test
    @DisplayName("getEmployeeDetailsByName_valid")
    void getEmployeeDetailsByName_valid() throws Exception {
        when(employeeService.getByName("sujith")).thenReturn(EmployeeDtoObject.getEmployeeList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee").param("firstName", "sujith"))
                .andExpect(status().isOk()).andExpect(result -> {
                    EmployeeDtoObject.getEmployeeList();
                });
    }

    @Test
    @DisplayName("getEmployeeDetailsByName_throwApiRequestException")
    void getEmployeeDetailsByName_throwApiRequestException() throws Exception {
        when(employeeService.getByName("suji")).thenThrow(new ApiRequestException("No Employee was found with given name: suji"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee")
                        .param("firstName", "suji")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No Employee was found with given name: suji"));
    }

    @Test
    @DisplayName("getEmployeeDetailsByCourseName_valid")
    void getEmployeeDetailsByCourseName_valid() throws Exception {
        when(employeeService.getEmployeesByCourseName("IT")).thenReturn(EmployeeDtoObject.getEmployeeList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/course")
                .param("name", "IT")).andExpect(status().isOk()).andExpect(result -> {
                    EmployeeDtoObject.getEmployeeList();
                });
    }

    @Test
    @DisplayName("getEmployeeDetailsByCourseName_throwApiRequestException")
    void getEmployeeDetailsByCourseName_throwApiRequestException() throws Exception {
        when(employeeService.getEmployeesByCourseName("IIT")).thenThrow(new ApiRequestException("No employees were found with given course : IIT"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/course")
                        .param("name", "IIT")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No employees were found with given course : IIT"));
    }

    @Test
    @DisplayName("insertEmployee_Notvalid")
    void insertEmployee_Notvalid() throws Exception {
        when(employeeService.save(EmployeeDtoObject.getStr())).thenReturn(EmployeeDtoObject.getEmp());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee")
                .contentType(MediaType.APPLICATION_PROBLEM_JSON).characterEncoding(StandardCharsets.UTF_8)
                .content(EmployeeDtoObject.getEmpJson())).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("getAllEmployees_valid")
    void getAllEmployees_valid() throws Exception {
        when(employeeService.findAll()).thenReturn(EmployeeDtoObject.getEmployeeList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/findAll")).andExpect(status().isOk()).andExpect(result -> {
            EmployeeDtoObject.getEmployeeList();
        });
    }

    @Test
    @DisplayName("getAllEmployees_throwApiRequestException")
    void getAllEmployees_throwApiRequestException() throws Exception {
        when(employeeService.findAll()).thenThrow(new ApiRequestException("No Employees were found in Database"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/findAll"))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.message").value("No Employees were found in Database"));
    }

    @Test
    @DisplayName("insertEmployee_valid")
    void insertEmployee_valid() throws Exception {
        when(employeeService.save(EmployeeDtoObject.getStr())).thenReturn(EmployeeDtoObject.getEmp());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee").characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON).content(EmployeeDtoObject.getEmpJson()))
                .andExpect(status().isOk()).andExpect(result -> {
                    EmployeeDtoObject.getEmp();
                });
    }

    @Test
    @DisplayName("insertEmployeeList_valid")
    void insertEmployeeList_valid() throws Exception {
        when(employeeService.saveAll(EmployeeDtoObject.getEmployeeList())).thenReturn(EmployeeDtoObject.getEmployeeList());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/employee/insertAll").characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON).content(EmployeeDtoObject.getEmpListJson()))
                .andExpect(status().isOk()).andExpect(result -> {
                    EmployeeDtoObject.getEmp();
                });
    }

    @Test
    @DisplayName("updateEmployee_valid")
    void updateEmployee_valid() throws Exception {
        when(employeeService.update(EmployeeDtoObject.getStr())).thenReturn(EmployeeDtoObject.getEmp());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/employee").characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON).content(EmployeeDtoObject.getEmpJson()))
                .andExpect(status().isOk()).andExpect(result -> {
                    EmployeeDtoObject.getEmp();
                });
    }

    @Test
    @DisplayName("updateEmployee_throwApiRequestException")
    void updateEmployee_throwApiRequestException() throws Exception {
        when(employeeService.update(EmployeeDtoObject.getInvalidStr())).thenThrow(new ApiRequestException("No Employee was found to Update"));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/employee").characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON).content(EmployeeDtoObject.getInvalidEmpJson()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("updateSalaryById_valid")
    void updtaeSalaryById_valid() throws Exception {
        when(employeeService.updateSalaryById(1, 25000)).thenReturn(EmployeeDtoObject.getEmp());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/employee/updateSalaryById")
                .param("id", "1").param("salary", "25000")).andExpect(status().isOk()).andExpect(result -> {
                    EmployeeDtoObject.getEmp();
                });
    }

    @Test
    @DisplayName("updateSalaryById_throwApiRequestException")
    void updtaeSalaryById_throwApiRequestException() throws Exception {
        when(employeeService.updateSalaryById(11, 25000)).thenThrow(new ApiRequestException("Employee not found"));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/employee/updateSalaryById")
                        .param("id", "11").param("salary", "25000")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Employee not found"));
    }


    @Test
    @DisplayName("getEmployeesByDepartment_valid")
    void getEmployeesByDepartment_valid() throws Exception {
        when(employeeService.findByDepartment("IT")).thenReturn(EmployeeDtoObject.getEmployeeList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department")
                .param("deptName", "IT")).andExpect(status().isOk()).andExpect(result -> {
                    EmployeeDtoObject.getEmpListJson();
                });

    }

    @Test
    @DisplayName("getEmployeesByDepartment_throwApiRequestException")
    void getEmployeesByDepartment_throwApiRequestException() throws Exception {
        when(employeeService.findByDepartment("IIT")).thenThrow(new ApiRequestException("No Employees found in the given department"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employee/department")
                        .param("deptName", "IIT")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No Employees found in the given department"));

    }

    @Test
    @DisplayName("DeleteEmployeeById-Valid")
    void deleteByEmployeeId_valid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/employee/1")).andExpect(status().isOk());

    }

    @Test
    @DisplayName("DeleteEmployeeById-throwIllegalStateExeption")
    void deleteByEmployeeId_throwIllegalStateException() throws Exception {
        doThrow(IllegalStateException.class).when(employeeService).delete(1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/employee/1")).andExpect(status().isBadRequest());

    }
}

