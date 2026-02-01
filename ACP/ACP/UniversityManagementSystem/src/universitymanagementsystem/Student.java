package semesterproject.universitymanagementsystem;

public class Student {
    private String uniID, uniName, location, email, contactNo;
    private String deptName, name;
    private int rollNo, semester;
    private float gpa, cgpa;

    public Student(String uniID, String uniName, String location, String email, String contactNo,
                   String deptName, String name, int rollNo) {
        this.uniID = uniID;
        this.uniName = uniName;
        this.location = location;
        this.email = email;
        this.contactNo = contactNo;
        this.deptName = deptName;
        this.name = name;
        this.rollNo = rollNo;
    }

    // Getters & setters
    public String getUniID() { return uniID; }
    public void setUniID(String uniID) { this.uniID = uniID; }

    public String getUniName() { return uniName; }
    public void setUniName(String uniName) { this.uniName = uniName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getRollNo() { return rollNo; }
    public void setRollNo(int rollNo) { this.rollNo = rollNo; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public float getGpa() { return gpa; }
    public void setGpa(float gpa) { this.gpa = gpa; }

    public float getCgpa() { return cgpa; }
    public void setCgpa(float cgpa) { this.cgpa = cgpa; }
}
