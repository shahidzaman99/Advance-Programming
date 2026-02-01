package semesterproject.universitymanagementsystem;

public class Department extends University {
    private String deptName;

    public Department(String uniID, String uniName, String location, String email, String contactNo, String deptName) {
        super(uniID, uniName, location, email, contactNo);
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Department: " + deptName);
    }
}
