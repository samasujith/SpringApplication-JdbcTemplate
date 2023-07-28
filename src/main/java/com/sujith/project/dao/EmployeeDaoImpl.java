package com.sujith.project.dao;

import com.sujith.project.entity.*;
import jakarta.persistence.*;
import jakarta.validation.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    private final EntityManager entityManager;
    private final CourseDao courseDao;
    private final Logger logger = LoggerFactory.getLogger(EmployeeDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;
    RowMapper<Employee> rowMapper = (rs, rowNum) -> {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setSalary(rs.getInt("salary"));
        employee.setExperience(rs.getInt("experience"));
        employee.setDepartment(rs.getString("department"));
        return employee;
    };
    RowMapper<Address> addressRowMapper = (rs, rowNum) -> {
        Address address = new Address();
        address.setId(rs.getInt("id"));
        address.setCity(rs.getString("city"));
        address.setStreet(rs.getString("street"));
        address.setPin(rs.getInt("pincode"));
        return address;
    };
    RowMapper<Course> courseRowMapper = (rs, rowNum) -> {
        Course course = new Course();
        course.setCourseName(rs.getString("course_name"));
        course.setId(rs.getInt("id"));
        return course;
    };

    @Autowired
    public EmployeeDaoImpl(EntityManager entityManager, CourseDao courseDao, JdbcTemplate jdbcTemplate) {
        this.entityManager = entityManager;
        this.courseDao = courseDao;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Employee findById(int id) {
        String query = "select *  from employees e where e.id = " + id;
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setSalary(rs.getInt("salary"));
            employee.setExperience(rs.getInt("experience"));
            employee.setDepartment(rs.getString("department"));
            Address address1 = jdbcTemplate.queryForObject("select * from address a where a.id=" + rs.getInt("address_id"),
                    (rs1, rowNum1) -> {
                        Address address = new Address();
                        address.setId(rs1.getInt("id"));
                        address.setCity(rs1.getString("city"));
                        address.setStreet(rs1.getString("street"));
                        address.setPin(rs1.getInt("pincode"));
                        return address;
                    });
            employee.setAddress(address1);
            List<Course> courses = jdbcTemplate.query("select c.* from course c, employee_course ec where ec.employee_id= " + rs.getInt("id") + " and ec.course_id=c.id",
                    (rs1, rowNum1) -> {
                        Course course = new Course();
                        course.setCourseName(rs1.getString("course_name"));
                        course.setId(rs1.getInt("id"));
                        return course;
                    });
            employee.setCourseList(courses);
            return employee;
        });

    }


    @Override
    public List<Employee> getByName(String fname) {

        String query = "select * from employees e where e.first_name=" + "\"" + fname + "\"";
        List<Employee> employeeList = jdbcTemplate.query(query, rowMapper);
        for (Employee employee : employeeList) {
            Address address = new Address();
            List<Course> courses = new ArrayList<>();
            address = jdbcTemplate.queryForObject("select * from address a where a.id =" + employee.getId(), addressRowMapper);
            employee.setAddress(address);
            courses = jdbcTemplate.query("select c.* from course c, employee_course ec where ec.employee_id= " + employee.getId() + " and ec.course_id=c.id", courseRowMapper);
            employee.setCourseList(courses);
        }
        return employeeList;
    }

    @Override
    public Employee save(@Valid Employee theEmployee) {
//        List<Course> tempList = new ArrayList<>();
//        List<Course> courses = theEmployee.getCourseList();
//        for (Course temp1 : courses) {
//            Query query = entityManager.createQuery("SELECT COUNT(courseName) FROM Course c WHERE c.courseName=:nameValue");
//            query.setParameter("nameValue", temp1.getCourseName().toUpperCase());
//            long count = (long) query.getSingleResult();
//            if (count >= 1) {
//                Course tempcourse = courseDao.getCourseIdByName(temp1.getCourseName().toUpperCase());
//                tempList.add(tempcourse);
//            } else {
//                String cname = temp1.getCourseName().toUpperCase();
//                temp1.setCourseName(cname);
//                tempList.add(temp1);
//            }
//        }
//        theEmployee.setCourseList(tempList);
//
//        entityManager.persist(theEmployee);
//
//        return theEmployee;
        String nextVal = "select next_val from employees_seq";
        int next = jdbcTemplate.queryForObject(nextVal, Integer.class);
        int seqcount=next+1;

        jdbcTemplate.update("UPDATE employees_seq es set es.next_val =?",seqcount);

        Address newAddress = theEmployee.getAddress();
        String sql = "insert into  address(city, pincode, street) values (?,?,?)";
        jdbcTemplate.update(sql, newAddress.getCity(), newAddress.getPin(), newAddress.getStreet());
        String query = "INSERT into  employees( id,salary,department,first_name,last_name,experience,address_Id) values (?,?,?,?,?,?,?)";
        jdbcTemplate.update(query, next, theEmployee.getSalary(), theEmployee.getDepartment(), theEmployee.getFirstName(), theEmployee.getLastName(), theEmployee.getExperience(),next);


        List<Course> tempList = new ArrayList<>();
        List<Course> courses = theEmployee.getCourseList();
        for (Course temp1 : courses) {
            String courseQuery = "select count(course_name) from course where course_name=?";
            int count = jdbcTemplate.queryForObject(courseQuery, Integer.class, temp1.getCourseName());

            if (count >= 1) {
                String courseId = "select id from course where course_name=?";
                int cId = jdbcTemplate.queryForObject(courseId, Integer.class, temp1.getCourseName());
                String updatequery = "INSERT into employee_course(employee_id,course_id) values(?,?) ";
                jdbcTemplate.update(updatequery, next,cId);

            } else {
                int totalCourses = jdbcTemplate.queryForObject("select count(id) from course", Integer.class);
                String countQuery = "INSERT INTO course (id, course_name) VALUES (?, ?)";
                jdbcTemplate.update(countQuery, totalCourses + 1, temp1.getCourseName());
                String courseId = "select id from course where course_name=?";
                int cId = jdbcTemplate.queryForObject(courseId, Integer.class, temp1.getCourseName());
                String updatequery = "INSERT into employee_course(employee_id,course_id) values(?,?) ";
                jdbcTemplate.update(updatequery, next,cId);
            }
        }
        return theEmployee;
    }

    @Override
    public List<Employee> saveAll(List<Employee> employeeList) {
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : employeeList) {
            employees.add(save(employee));
        }
        return employees;
    }

    @Override
    public Employee update(Employee theEmployee) {

//        return entityManager.merge(theEmployee);
        String query = "update employees e,address a,employee_course ec set e.salary=?,e.department=?,e.first_name=?,e.last_name=?,e.experience=? where e.id=?";

        jdbcTemplate.update(query, theEmployee.getSalary(), theEmployee.getDepartment(), theEmployee.getFirstName(), theEmployee.getLastName(), theEmployee.getExperience(), theEmployee.getId());
        Address newAddress = theEmployee.getAddress();

        String sql = "UPDATE address a SET a.city=?, a.pincode=?, a.street=? WHERE a.id=?";

        jdbcTemplate.update(sql, newAddress.getCity(), newAddress.getPin(), newAddress.getStreet(), theEmployee.getAddress().getId());
        List<Course> courses = theEmployee.getCourseList();
        List<Course> updatedList = new ArrayList<>();
        for (Course course : courses) {
            String courseQuery = "select count(course_name) from course where course_name=?";
            int count = jdbcTemplate.queryForObject(courseQuery, Integer.class, course.getCourseName());
            if (count > 0) {
                Course course1 = jdbcTemplate.queryForObject("select * from course c where c.course_name=" + "\"" + course.getCourseName() + "\"", courseRowMapper);
                updatedList.add(course1);
            } else {
                int totalCourses = jdbcTemplate.queryForObject("select count(id) from course", Integer.class);
                String countQuery = "INSERT INTO course (id, course_name) VALUES (?, ?)";
                jdbcTemplate.update(countQuery, totalCourses + 1, course.getCourseName());
                Course course1 = jdbcTemplate.queryForObject("select * from course c where c.course_name=" + "\"" + course.getCourseName() + "\"", courseRowMapper);
                String updatequery = "INSERT into employee_course(employee_id,course_id) values(?,?) ";
                jdbcTemplate.update(updatequery, theEmployee.getId(), totalCourses + 1);
                updatedList.add(course1);

            }

        }

        theEmployee.setCourseList(updatedList);

        return findById(theEmployee.getId());
    }


    @Override
    public int maxSalary() {

        int max = jdbcTemplate.queryForObject("select max(salary) from employees ", Integer.class);
        return max;

    }

    @Override
    public List<Employee> findByDepartment(String dept) {
        TypedQuery<Employee> empList = entityManager.createQuery("From Employee where department=:department", Employee.class);
        empList.setParameter("department", dept);
        return empList.getResultList();
    }

    @Override
    public int maxInDept(String dept) {
        int max = jdbcTemplate.queryForObject("select max(salary) from employees e where e.department= " + "\"" + dept + "\"", Integer.class);
        return max;

    }

    @Override
    public Employee updateSalaryById(int id, int salary) {
        jdbcTemplate.update("update employees e set e.salary=" + salary + " where e.id=" + id);
        return findById(id);
    }

    @Override
    public List<Employee> getEmployeesByCourseName(String name) {
        String query = "SELECT * FROM employees e JOIN employee_course ec ON e.id = ec.employee_id "
                +
                " JOIN Course c ON ec.course_id = c.id WHERE c.course_name = " + "\"" + name + "\"";
        List<Employee> employeeList = jdbcTemplate.query(query, rowMapper);
        for (Employee employee : employeeList) {
            new Address();
            Address address;
            List<Course> courses;
            address = jdbcTemplate.queryForObject("select * from address a where a.id =" + employee.getId(), addressRowMapper);
            employee.setAddress(address);
            courses = jdbcTemplate.query("select c.* from course c, employee_course ec where ec.employee_id= " + employee.getId() + " and ec.course_id=c.id", courseRowMapper);
            employee.setCourseList(courses);
        }
        return employeeList;
    }
}
