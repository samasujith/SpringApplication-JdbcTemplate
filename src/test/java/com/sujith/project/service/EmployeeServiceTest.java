package com.sujith.project.service;

import com.sujith.project.dao.*;
import com.sujith.project.exceptions.*;
import com.sujith.project.jpa.*;
import com.sujith.project.jpaservice.*;
import com.sujith.project.service_test_files.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.web.method.annotation.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceTest {

    @MockBean
    CourseDao courseDao;
    @MockBean
    private EmployeeDao employeeDao;
    @MockBean
    private EmployeeJpa employeeJpa;
    //@InjectMocks
    @Autowired
    private EmployeeJpaService employeeService;

    //    @BeforeEach
    //     void beforerEach() {
    //        List<Course> courses = new ArrayList<>();
    //        courses.add(Course.builder().id(1).courseName("c").build());
    //        employee = new Employee(1, "sujith", "reddy", 25000, "IT", 3, Address.builder().id(1)
    //        .city("pvc").street("Ambedker center").pin(507115).build(), courses);
    //    }

    @DisplayName("FindByName-valid")
    @Test
    void findByName_valid() {
        when(employeeDao.getByName("sujith")).thenReturn(EmployeeFiles.getListOfEmployee());
        assertEquals(EmployeeFiles.getListOfEmployee(), employeeService.getByName("sujith"));
        verify(employeeDao).getByName("sujith");
    }

    @DisplayName("FindByName-throwApiRequestException")
    @Test
    void findByName_throwApiRequestException() {
        when(employeeDao.getByName("sujithreddy")).thenThrow(ApiRequestException.class);
        assertThrows(ApiRequestException.class, () -> employeeService.getByName("sujithreddy"));
        verify(employeeDao).getByName("sujithreddy");
    }

    @DisplayName("DeleteById-throwIllegalStateException")
    @Test
    void deleteById_throwIllegalStateException() {
        doThrow(IllegalStateException.class).when(employeeJpa).deleteById(null);
        assertThrows(IllegalStateException.class, () -> employeeService.delete(null));
        verify(employeeJpa).deleteById(null);

    }

    @DisplayName("DeleteById-valid")
    @Test
    void deleteById_valid() {
        doNothing().when(employeeJpa).deleteById(1);
        employeeService.delete(1);
        verify(employeeJpa).deleteById(1);

    }


    @DisplayName("UpdateSalaryById-Valid")
    @Test
    void testEmployeeService_UpdateSalaryById_Valid() {
        when(employeeDao.updateSalaryById(1, 24000)).thenReturn(EmployeeFiles.getSingleEmployee());
        assertEquals(EmployeeFiles.getSingleEmployee(), employeeService.updateSalaryById(1, 24000));
    }

    @DisplayName("UpdateSalaryById-throwApiRequestException")
    @Test
    void updateSalaryById_throwApiRequestException() {
        when(employeeDao.updateSalaryById(0, 24000)).thenThrow(NullPointerException.class);
        assertThrows(ApiRequestException.class, () -> employeeService.updateSalaryById(0, 24000));
    }

    @Test
    @DisplayName("findById_valid")
    void findById_valid() {
        when(employeeDao.findById(1)).thenReturn(EmployeeFiles.getSingleEmployee());
        assertEquals(EmployeeFiles.getSingleEmployeeDto(), employeeService.findById(1));
    }

    @Test
    @DisplayName("findById_throwApiRequestException")
    void findById_throwApiRequestException() {
        //when(employeeDao.findById(11)).thenThrow(ApiRequestException.class);
        when(employeeDao.findById(11)).thenReturn(null);

        assertThrows(ApiRequestException.class, () -> {
            employeeService.findById(11);
        });
    }

    @Test
    @DisplayName("findById_throwIllegalArgumentException")
    void findById_throwIllegalArgumentException() {
        when(employeeDao.findById(11)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> {
            employeeService.findById(11);
        });
    }

    @Test
    @DisplayName("findById_throwApiRequestException>id<=0")
    void findById_throwApiRequestExceptionIdLessThanZero() {
        when(employeeDao.findById(11)).thenThrow(ApiRequestException.class);
        assertThrows(ApiRequestException.class, () -> {
            employeeService.findById(0);
        });
    }

    @Test
    @DisplayName("findById_throwMethodArgumentTypeMisMatchException")
    void findById_throwMethodArgumentTypeMisMatchException() {
        when(employeeDao.findById(224)).thenThrow(MethodArgumentTypeMismatchException.class);
        assertThrows(MethodArgumentTypeMismatchException.class, () -> employeeService.findById(224));
    }

    @Test
    @DisplayName("findAll_valid")
    void findAll_valid() {
        when(employeeJpa.findAll()).thenReturn(EmployeeFiles.getListOfEmployee());
        assertEquals(EmployeeFiles.getListOfEmployee(), employeeService.findAll());
    }

    @Test
    @DisplayName("findAll_throwApiRequestException")
    void findAll_throwApiRequestException() {
        when(employeeJpa.findAll()).thenThrow(ApiRequestException.class);
        assertThrows(ApiRequestException.class, () -> employeeService.findAll());
    }

    @Test
    @DisplayName("findMaxSalary_valid")
    void findMaxSalary_valid() {
        when(employeeDao.maxSalary()).thenReturn(70000);
        assertEquals(70000, employeeService.maxSalary());
    }

    @Test
    @DisplayName("findMaxSalary_throwApiRequestException")
    void findMaxSalary_throwApiRequestException() {
        when(employeeDao.maxSalary()).thenThrow(NullPointerException.class);
        assertThrows(ApiRequestException.class, () -> employeeService.maxSalary());
    }

    @DisplayName("findMaxSalaryInDepartment_valid")
    @Test
    void findMaxSalaryInDepartment_valid() {
        when(employeeDao.maxInDept("IT")).thenReturn(45000);
        assertEquals(45000, employeeService.maxInDept("IT"));
    }

    @DisplayName("findMaxSalaryInDepartment_throwApiRequestException")
    @Test
    void findMaxSalaryInDepartment_throwApiRequestException() {
        when(employeeDao.maxInDept("IT")).thenReturn(0);
        assertThrows(ApiRequestException.class, () -> employeeService.maxInDept("IT"));
    }

    @DisplayName("findEmployeesByDepartment_valid")
    @Test
    void findEmployeesByDepartment_valid() {
        when(employeeDao.findByDepartment("IT")).thenReturn(EmployeeFiles.getListOfEmployee());
        assertEquals(EmployeeFiles.getListOfEmployee(), employeeService.findByDepartment("IT"));
    }

    @DisplayName("findEmployeesByDepartment_throwApiRequestException")
    @Test
    void findEmployeesByDepartment_throwApiRequestException() {
        // when(employeeDao.findByDepartment("IIT")).thenThrow(ApiRequestException.class);
        when(employeeDao.findByDepartment("IIT")).thenReturn(new ArrayList<>());
        assertThrows(ApiRequestException.class, () -> employeeService.findByDepartment("IIT"));
    }

    @DisplayName("findEmployeesByCourseName_valid")
    @Test
    void findEmployeesByCourseName_valid() {
        when(employeeDao.getEmployeesByCourseName("C")).thenReturn(EmployeeFiles.getListOfEmployee());
        assertEquals(EmployeeFiles.getListOfEmployee(), employeeService.getEmployeesByCourseName("C"));
    }

    @DisplayName("findEmployeesByCourseName_throwApiRequestException")
    @Test
    void findEmployeesByCourseName_throwApiRequestException() {
        when(employeeDao.getEmployeesByCourseName("C++")).thenThrow(ApiRequestException.class);
        assertThrows(ApiRequestException.class, () -> employeeService.getEmployeesByCourseName("C++"));
    }

    @DisplayName("saveAll_valid")
    @Test
    void saveAll_valid() {
        when(employeeJpa.saveAll(EmployeeFiles.getListOfEmployee())).thenReturn(EmployeeFiles.getListOfEmployee());
        assertEquals(EmployeeFiles.getListOfEmployee(), employeeService.saveAll(EmployeeFiles.getListOfEmployee()));
    }

    @DisplayName("saveAll_NonameEmployee")
    @Test
    void saveAll_NoNameEmployee() {
        when(employeeJpa.saveAll(EmployeeFiles.getIllegalStateEmployeeResult())).thenReturn(EmployeeFiles.getIllegalStateEmployeeResult());
        assertEquals(EmployeeFiles.getIllegalStateEmployeeResult(), employeeService.saveAll(EmployeeFiles.getIllegalStateEmployee()));
        // assertThrows(IllegalStateException.class,() -> employeeService.saveAll(EmployeeFiles.getIllegalStateEmployee()));

    }


    @DisplayName("updateEmployee_valid")
    @Test
    void updateEmployee_valid() {
        when(employeeDao.findById(1)).thenReturn(EmployeeFiles.getSingleEmployeeWithCourses());
        when(courseDao.getAll()).thenReturn(EmployeeFiles.getCourses());
        when(employeeDao.update(EmployeeFiles.getSingleEmployeeWithCourses())).thenReturn(EmployeeFiles.getUpdateSingleEmployeeWithCourses());
        assertEquals(EmployeeFiles.getUpdateSingleEmployeeWithCourses(), employeeService.update(EmployeeFiles.getEmployeeDtoWithCourse()));
    }


    @DisplayName("updateEmployee_throwApiRequestException")
    @Test
    void updateEmployee_throwApiRequestException() {
        when(employeeDao.findById(1)).thenThrow(NullPointerException.class);
        when(courseDao.getAll()).thenReturn(EmployeeFiles.getCourses());
        when(employeeDao.update(EmployeeFiles.getSingleEmployeeWithCourses())).thenThrow(IllegalStateException.class);
        assertThrows(ApiRequestException.class, () -> employeeService.update(EmployeeFiles.getEmployeeDtoWithCourse()));
    }

    @DisplayName("updateEmployee_throwIllegalStateException")
    @Test
    void updateEmployee_throwIllegalStateException() {
        when(employeeDao.findById(1)).thenReturn(EmployeeFiles.getSingleEmployeeWithCourses());
        when(courseDao.getAll()).thenReturn(EmployeeFiles.getCourses());
        when(employeeDao.update(EmployeeFiles.getSingleEmployeeWithCourses())).thenThrow(IllegalStateException.class);
        assertThrows(IllegalStateException.class, () -> employeeService.update(EmployeeFiles.getEmployeeDtoWithCourse()));
    }

    @DisplayName("saveEmployee_valid")
    @Test
    void saveEmployee_valid() {
        when(employeeDao.save(EmployeeFiles.getSingleEmployeeWithCourses())).thenReturn(EmployeeFiles.getSingleEmployeeWithCourses());
        assertEquals(EmployeeFiles.getSingleEmployeeWithCourses(), employeeService.save(EmployeeFiles.getSingleEmployeeWithCoursesDto()));
    }

    @DisplayName("saveEmployee_throwNullPointerException")
    @Test
    void saveEmployee_throwInvalidDataAccessApiUsageException() {
        when(employeeDao.save(EmployeeFiles.getEmployeeWithNoFirstName())).thenThrow(NullPointerException.class);
        assertThrows(NullPointerException.class, () -> employeeService.save(EmployeeFiles.getEmployeeDtoWithNoFirstName()));
    }


}
