package com.sujith.project.dao;

import com.sujith.project.entity.*;
import com.sujith.project.exceptions.*;
import jakarta.persistence.*;
import jakarta.validation.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private final EntityManager entityManager;
    private final CourseDao courseDao;
    private final Logger logger = LoggerFactory.getLogger(EmployeeDaoImpl.class);

    @Autowired
    public EmployeeDaoImpl(EntityManager entityManager, CourseDao courseDao) {
        this.entityManager = entityManager;
        this.courseDao = courseDao;
    }


    @Override
    public Employee findById(int id) {

        return entityManager.find(Employee.class, id);

    }


    @Override
    public List<Employee> getByName(String fname) {
        TypedQuery<Employee> emplist = entityManager.createQuery("FROM Employee WHERE firstName=:Fname", Employee.class);
        emplist.setParameter("Fname", fname);
        if (emplist.getResultList().isEmpty()) {
            logger.warn("No employees were found with given name : {}", fname);
            throw new ApiRequestException("No Employee was found with given name: " + fname);
        } else {
            return emplist.getResultList();
        }
    }

    @Override
    public Employee save(@Valid Employee theEmployee) {
        List<Course> tempList = new ArrayList<>();
        List<Course> courses = theEmployee.getCourseList();
        for (Course temp1 : courses) {
            Query query = entityManager.createQuery("SELECT COUNT(courseName) FROM Course c WHERE c.courseName=:nameValue");
            query.setParameter("nameValue", temp1.getCourseName());
            long count = (long) query.getSingleResult();
            if (count >= 1) {
                Course tempcourse = courseDao.getCourseIdByName(temp1.getCourseName());
                tempList.add(tempcourse);
            } else {
                tempList.add(temp1);
            }
        }
        theEmployee.setCourseList(tempList);

        entityManager.persist(theEmployee);

        return theEmployee;
    }

    @Override
    public Employee update(Employee theEmployee) {

        return entityManager.merge(theEmployee);
    }


    @Override
    public int maxSalary() {
        Query forMax = entityManager.createQuery(" select MAX(salary) FROM Employee ", Employee.class);

        if (((int) forMax.getSingleResult()) <= 0) {
            throw new ApiRequestException("No department found with given name");
        } else {
            return (int) forMax.getSingleResult();
        }

    }

    @Override
    public List<Employee> findByDepartment(String dept) {
        TypedQuery<Employee> empList = entityManager.createQuery("From Employee where department=:department", Employee.class);
        empList.setParameter("department", dept);
        return empList.getResultList();
    }

    @Override
    public int maxInDept(String dept) {
        List<Employee> empList = findByDepartment(dept);
        int max = 0;
        for (Employee emp : empList) {
            if (emp.getSalary() > max) {
                max = emp.getSalary();
            }
        }
        return max;

    }

    @Override
    public Employee updateSalaryById(int id, int salary) {
        Employee theEmployee = findById(id);
        theEmployee.setSalary(salary);
        entityManager.merge(theEmployee);
        return theEmployee;
    }

    @Override
    public List<Employee> getEmployeesByCourseName(String name) {
        String tempQuery = "SELECT e.id FROM employees e JOIN employee_course ec ON e.id = ec.employee_id "
                +
                " JOIN Course c ON ec.course_id = c.id WHERE c.course_name = " + "\"" + name + "\"";
        Query query = entityManager.createNativeQuery(tempQuery);
        List tempEmp = query.getResultList().stream()
                .map(id -> findById((Integer) id)).toList();

        if (tempEmp.isEmpty()) {
            throw new ApiRequestException("No employees were found with given course : " + name);
        } else {
            return tempEmp;
        }
    }
}
