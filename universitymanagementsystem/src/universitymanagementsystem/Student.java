/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package universitymanagementsystem;

import java.sql.SQLException;
import java.util.Scanner;

public class Student extends Department {
    private String name;
    private int rollNo, semester;
    private float gpa, cgpa;

    public Student(String uniID, String uniName, String location, String email, String contactNo,
                   String deptName, String name, int rollNo) {
        super(uniID, uniName, location, email, contactNo, deptName);
        this.name = name;
        this.rollNo = rollNo;
    }

    public String getName() { return name; }
    public int getRollNo() { return rollNo; }
    public int getSemester() { return semester; }
    public float getGpa() { return gpa; }
    public float getCgpa() { return cgpa; }

    public void setName(String name) { this.name = name; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setGpa(float gpa) { this.gpa = gpa; }
    public void setCgpa(float cgpa) { this.cgpa = cgpa; }

    @Override
    public void displayInfo() {
        System.out.println("Roll: " + rollNo + " | Name: " + name + " | Uni: " + getUniName());
    }

    // Console Menu Method
    public static void mainMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Add Student  2. View All");
        int choice = sc.nextInt(); sc.nextLine();

        try {
            if (choice == 1) {
                System.out.print("Uni ID: "); String uid = sc.nextLine();
                System.out.print("Name: "); String name = sc.nextLine();
                System.out.print("Roll No: "); int roll = sc.nextInt();
                Student s = new Student(uid, "Unknown", "", "", "", "Dept", name, roll);
                DBManager.saveStudent(s);
                System.out.println("Saved to DB!");
            } else if (choice == 2) {
                for (Student s : DBManager.loadAllStudents()) s.displayInfo();
            }
        } catch (SQLException e) { System.out.println("DB Error: " + e.getMessage()); }
    }
}