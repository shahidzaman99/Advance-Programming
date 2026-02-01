package semesterproject.universitymanagementsystem;

public class University {
    private String uniID, uniName, location, email, contactNo;

    public University(String uniID, String uniName, String location, String email, String contactNo) {
        this.uniID = uniID;
        this.uniName = uniName;
        this.location = location;
        this.email = email;
        this.contactNo = contactNo;
    }

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
}
