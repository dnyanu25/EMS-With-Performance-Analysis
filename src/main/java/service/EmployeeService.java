package service;

import dao.EmployeeDao;
import model.Employee;

import java.util.List;

public class EmployeeService {

    private EmployeeDao employeeDao = new EmployeeDao();

    public boolean registerEmployee(Employee emp) {
        try {
            // your insert code
                    return employeeDao.addEmployee(emp);

        } catch (Exception e) {
            e.printStackTrace(); // 👈 shows the real error in console
            return false;
        }
    }

    public Employee getEmployeeDetails(int id) {
        return employeeDao.getEmployeeById(id);
    }
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    public boolean updateEmployeeInfo(Employee emp) {
        return employeeDao.updateEmployee(emp);
    }

    public boolean removeEmployee(int id) {
        return employeeDao.deleteEmployee(id);
    }

}