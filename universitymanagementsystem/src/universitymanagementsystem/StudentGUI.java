/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author szama
 */
package universitymanagementsystem;


import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class StudentGUI extends JFrame {
    private JTextField uniIdField, uniNameField, deptField, nameField, rollField, semField, gpaField, cgpaField;
    private JTextArea outputArea;

    public StudentGUI() {
        setTitle("Student Management (DB)");
        setSize(700, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(new JLabel("University ID (Required):")); uniIdField = new JTextField(); inputPanel.add(uniIdField);
        inputPanel.add(new JLabel("University Name:")); uniNameField = new JTextField(); inputPanel.add(uniNameField);
        inputPanel.add(new JLabel("Department:")); deptField = new JTextField(); inputPanel.add(deptField);
        inputPanel.add(new JLabel("Student Name:")); nameField = new JTextField(); inputPanel.add(nameField);
        inputPanel.add(new JLabel("Roll No:")); rollField = new JTextField(); inputPanel.add(rollField);
        inputPanel.add(new JLabel("Semester:")); semField = new JTextField(); inputPanel.add(semField);
        inputPanel.add(new JLabel("GPA:")); gpaField = new JTextField(); inputPanel.add(gpaField);
        inputPanel.add(new JLabel("CGPA:")); cgpaField = new JTextField(); inputPanel.add(cgpaField);
        add(inputPanel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel();
        JButton addBtn = new JButton("Add/Update");
        JButton viewBtn = new JButton("View All");
        btnPanel.add(addBtn); btnPanel.add(viewBtn);
        add(btnPanel, BorderLayout.CENTER);

        outputArea = new JTextArea();
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            try {
                int roll = Integer.parseInt(rollField.getText());
                int sem = semField.getText().isEmpty() ? 1 : Integer.parseInt(semField.getText());
                float gpa = gpaField.getText().isEmpty() ? 0 : Float.parseFloat(gpaField.getText());
                float cgpa = cgpaField.getText().isEmpty() ? 0 : Float.parseFloat(cgpaField.getText());

                // Create Student Object
                Student s = new Student(
                    uniIdField.getText(), uniNameField.getText(), "", "", "", 
                    deptField.getText(), nameField.getText(), roll
                );
                s.setSemester(sem);
                s.setGpa(gpa);
                s.setCgpa(cgpa);

                // Save to Database
                DBManager.saveStudent(s);
                JOptionPane.showMessageDialog(this, "Student Saved to Database!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for Roll, Sem, GPA.");
            }
        });

        viewBtn.addActionListener(e -> {
            outputArea.setText("");
            try {
                for (Student s : DBManager.loadAllStudents()) {
                    outputArea.append(s.getRollNo() + ": " + s.getName() + " (" + s.getUniName() + ")\n");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}