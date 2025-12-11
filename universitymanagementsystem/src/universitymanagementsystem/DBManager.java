/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package universitymanagementsystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    // *** UPDATE THESE WITH YOUR DATABASE CREDENTIALS ***
     private static final String URL = "jdbc:mysql://127.0.0.1:3306/university";

    private static final String USER = "root"; // CHANGE THIS TO YOUR MYSQL USERNAME

    private static final String PASSWORD = "shahid"; // CHANGE THIS TO YOUR MYSQL PASSWORD

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // --- UNIVERSITY OPERATIONS ---
    public static void saveUniversity(University u) throws SQLException {
        String sql = "INSERT INTO University (uniID, uniName, location, email, contactNo) VALUES (?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE uniName=?, location=?, email=?, contactNo=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getUniID());
            stmt.setString(2, u.getUniName());
            stmt.setString(3, u.getLocation());
            stmt.setString(4, u.getEmail());
            stmt.setString(5, u.getContactNo());
            stmt.setString(6, u.getUniName());
            stmt.setString(7, u.getLocation());
            stmt.setString(8, u.getEmail());
            stmt.setString(9, u.getContactNo());
            stmt.executeUpdate();
        }
    }

    public static List<University> loadAllUniversities() throws SQLException {
        List<University> list = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM University")) {
            while (rs.next()) {
                list.add(new University(
                    rs.getString("uniID"), rs.getString("uniName"), rs.getString("location"),
                    rs.getString("email"), rs.getString("contactNo")
                ));
            }
        }
        return list;
    }

    // --- STUDENT OPERATIONS ---
    public static void saveStudent(Student s) throws SQLException {
        // Ensure University exists first (Foreign Key)
        saveUniversity(s); 

        String sql = "INSERT INTO Student (rollNo, name, semester, gpa, cgpa, uniID, deptName) VALUES (?, ?, ?, ?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE name=?, semester=?, gpa=?, cgpa=?, uniID=?, deptName=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.getRollNo());
            stmt.setString(2, s.getName());
            stmt.setInt(3, s.getSemester());
            stmt.setFloat(4, s.getGpa());
            stmt.setFloat(5, s.getCgpa());
            stmt.setString(6, s.getUniID());
            stmt.setString(7, s.getDeptName());
            
            stmt.setString(8, s.getName());
            stmt.setInt(9, s.getSemester());
            stmt.setFloat(10, s.getGpa());
            stmt.setFloat(11, s.getCgpa());
            stmt.setString(12, s.getUniID());
            stmt.setString(13, s.getDeptName());
            stmt.executeUpdate();
        }
    }

    public static List<Student> loadAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT s.*, u.uniName, u.location, u.email, u.contactNo FROM Student s JOIN University u ON s.uniID = u.uniID";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Student s = new Student(
                    rs.getString("uniID"), rs.getString("uniName"), rs.getString("location"),
                    rs.getString("email"), rs.getString("contactNo"), rs.getString("deptName"),
                    rs.getString("name"), rs.getInt("rollNo")
                );
                s.setSemester(rs.getInt("semester"));
                s.setGpa(rs.getFloat("gpa"));
                s.setCgpa(rs.getFloat("cgpa"));
                list.add(s);
            }
        }
        return list;
    }
}