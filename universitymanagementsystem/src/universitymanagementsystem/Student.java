/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package universitymanagementsystem;

import java.io.*;
import java.util.*;

public class Student extends Department {
    private String name;
    private int rollNo, semester;
    private float gpa, cgpa;

    private static final List<Student> students = new ArrayList<>();
    private static final String FILE_NAME = "students.txt";

    // ✅ Updated constructor to match new University structure
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
    public void setRollNo(int rollNo) { this.rollNo = rollNo; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setGpa(float gpa) { this.gpa = gpa; }
    public void setCgpa(float cgpa) { this.cgpa = cgpa; }

    @Override
    public void displayInfo() {
        System.out.println("\n------------------------------");
        System.out.println("University ID: " + getUniID());
        System.out.println("University: " + getUniName());
        System.out.println("Location: " + getLocation());
        System.out.println("Email: " + getEmail());
        System.out.println("Contact No: " + getContactNo());
        System.out.println("Department: " + getDeptName());
        System.out.println("Student Name: " + name);
        System.out.println("Roll No: " + rollNo);
        System.out.println("Semester: " + semester);
        System.out.println("GPA: " + gpa);
        System.out.println("CGPA: " + cgpa);
        System.out.println("------------------------------");
    }

    // ================= MENU =================
    public static void mainMenu() {
        Scanner sc = new Scanner(System.in);
        loadFromFile();

        while (true) {
            System.out.println("\n=== STUDENT MENU ===");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student");
            System.out.println("3. View All Students");
            System.out.println("4. Search Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent(sc);
                case 2 -> updateStudent(sc);
                case 3 -> displayAll();
                case 4 -> searchStudent(sc);
                case 5 -> deleteStudent(sc);
                case 6 -> {
                    saveToFile();
                    System.out.println("Data saved. Returning...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= OPERATIONS =================
    private static void addStudent(Scanner sc) {
        System.out.print("Enter University ID: ");
        String uniID = sc.nextLine();
        System.out.print("Enter University Name: ");
        String uni = sc.nextLine();
        System.out.print("Enter Location: ");
        String location = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Contact No: ");
        String contactNo = sc.nextLine();
        System.out.print("Enter Department Name: ");
        String dept = sc.nextLine();
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        System.out.print("Enter Semester: ");
        int sem = sc.nextInt();
        System.out.print("Enter GPA: ");
        float gpa = sc.nextFloat();
        System.out.print("Enter CGPA: ");
        float cgpa = sc.nextFloat();
        sc.nextLine();

        Student s = new Student(uniID, uni, location, email, contactNo, dept, name, roll);
        s.setSemester(sem);
        s.setGpa(gpa);
        s.setCgpa(cgpa);
        students.add(s);

        saveToFile();
        System.out.println("✅ Student added and saved successfully!");
    }

    private static void updateStudent(Scanner sc) {
        System.out.print("Enter Roll No to update: ");
        int roll = sc.nextInt();
        sc.nextLine();

        Student s = findStudent(roll);
        if (s == null) {
            System.out.println("❌ Student not found!");
            return;
        }

        System.out.print("Enter new Name: ");
        s.setName(sc.nextLine());
        System.out.print("Enter new Semester: ");
        s.setSemester(sc.nextInt());
        System.out.print("Enter new GPA: ");
        s.setGpa(sc.nextFloat());
        System.out.print("Enter new CGPA: ");
        s.setCgpa(sc.nextFloat());
        sc.nextLine();

        System.out.println("✅ Record updated successfully!");
    }

    private static void displayAll() {
        if (students.isEmpty()) {
            System.out.println("No students found!");
            return;
        }
        for (Student s : students) s.displayInfo();
    }

    private static void searchStudent(Scanner sc) {
        System.out.print("Enter Roll No: ");
        int roll = sc.nextInt();
        Student s = findStudent(roll);
        if (s != null) s.displayInfo();
        else System.out.println("❌ Student not found!");
    }

    private static void deleteStudent(Scanner sc) {
        System.out.print("Enter Roll No to delete: ");
        int roll = sc.nextInt();
        sc.nextLine();

        Student s = findStudent(roll);
        if (s == null) {
            System.out.println("❌ Student not found!");
            return;
        }

        students.remove(s);
        saveToFile();
        System.out.println("✅ Student deleted successfully!");
    }

    private static Student findStudent(int roll) {
        for (Student s : students)
            if (s.getRollNo() == roll)
                return s;
        return null;
    }

    private static void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                bw.write(s.getUniID() + "," + s.getUniName() + "," + s.getLocation() + "," +
                        s.getEmail() + "," + s.getContactNo() + "," + s.getDeptName() + "," +
                        s.getName() + "," + s.getRollNo() + "," + s.getSemester() + "," +
                        s.getGpa() + "," + s.getCgpa());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner fsc = new Scanner(file)) {
            while (fsc.hasNextLine()) {
                String[] data = fsc.nextLine().split(",");
                if (data.length == 11) {
                    Student s = new Student(
                            data[0], data[1], data[2], data[3], data[4],
                            data[5], data[6], Integer.parseInt(data[7])
                    );
                    s.setSemester(Integer.parseInt(data[8]));
                    s.setGpa(Float.parseFloat(data[9]));
                    s.setCgpa(Float.parseFloat(data[10]));
                    students.add(s);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}