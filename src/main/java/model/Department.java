package model;

public class Department {
    private int id;
    private String name;
    private String manager;
    private int employeeCount;

    // Constructors
    public Department() {}

    public Department(int id, String name, String manager, int employeeCount) {
        this.id = id;
        this.name = name;
        this.manager = manager;
        this.employeeCount = employeeCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getManager() {
        return manager;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }
    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", manager='" + manager + '\'' +
                ", employeeCount=" + employeeCount +
                '}';
    }
}
