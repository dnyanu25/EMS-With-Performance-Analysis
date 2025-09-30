package service;

import dao.DepartmentDao;
import model.Department;

public class DepartmentService {

    private DepartmentDao departmentDAO = new DepartmentDao();

    public boolean addDepartment(Department dept) {
        return departmentDAO.addDepartment(dept);
    }

    public Department getDepartmentDetails(int id) {
        return departmentDAO.getDepartmentById(id);
    }

    public boolean updateDepartmentInfo(Department dept) {
        return departmentDAO.updateDepartment(dept);
    }

    public boolean removeDepartment(int id) {
        return departmentDAO.deleteDepartment(id);
    }
}
