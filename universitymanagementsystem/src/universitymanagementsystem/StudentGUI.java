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

public class StudentGUI extends JFrame {

    private JTextField universityField, departmentField, nameField, rollField, semesterField, gpaField, cgpaField;
    private JTextArea outputArea;

    public StudentGUI() {
        setTitle("Student Management");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240));

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font textFont = new Font("SansSerif", Font.PLAIN, 13);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 13);

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBackground(new Color(224, 235, 255));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel uniLabel = new JLabel("University:");
        uniLabel.setFont(labelFont);
        inputPanel.add(uniLabel);
        universityField = new JTextField();
        universityField.setFont(textFont);
        inputPanel.add(universityField);

        JLabel deptLabel = new JLabel("Department:");
        deptLabel.setFont(labelFont);
        inputPanel.add(deptLabel);
        departmentField = new JTextField();
        departmentField.setFont(textFont);
        inputPanel.add(departmentField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        inputPanel.add(nameLabel);
        nameField = new JTextField();
        nameField.setFont(textFont);
        inputPanel.add(nameField);

        JLabel rollLabel = new JLabel("Roll No:");
        rollLabel.setFont(labelFont);
        inputPanel.add(rollLabel);
        rollField = new JTextField();
        rollField.setFont(textFont);
        inputPanel.add(rollField);

        JLabel semLabel = new JLabel("Semester:");
        semLabel.setFont(labelFont);
        inputPanel.add(semLabel);
        semesterField = new JTextField();
        semesterField.setFont(textFont);
        inputPanel.add(semesterField);

        JLabel gpaLabel = new JLabel("GPA:");
        gpaLabel.setFont(labelFont);
        inputPanel.add(gpaLabel);
        gpaField = new JTextField();
        gpaField.setFont(textFont);
        inputPanel.add(gpaField);

        JLabel cgpaLabel = new JLabel("CGPA:");
        cgpaLabel.setFont(labelFont);
        inputPanel.add(cgpaLabel);
        cgpaField = new JTextField();
        cgpaField.setFont(textFont);
        inputPanel.add(cgpaField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton addButton = new JButton("Add Student");
        addButton.setFont(buttonFont);
        addButton.setBackground(new Color(0, 120, 215));
        addButton.setForeground(Color.WHITE);

        JButton viewButton = new JButton("View Students");
        viewButton.setFont(buttonFont);
        viewButton.setBackground(new Color(46, 139, 87));
        viewButton.setForeground(Color.WHITE);

        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(128, 128, 128));
        backButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.CENTER);

        outputArea = new JTextArea(10, 40);
        outputArea.setFont(textFont);
        outputArea.setBackground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String university = universityField.getText();
            String department = departmentField.getText();
            String name = nameField.getText();
            String roll = rollField.getText();
            String semester = semesterField.getText();
            String gpa = gpaField.getText();
            String cgpa = cgpaField.getText();

            outputArea.append("Student Added:\n");
            outputArea.append("University: " + university + "\n");
            outputArea.append("Department: " + department + "\n");
            outputArea.append("Name: " + name + "\n");
            outputArea.append("Roll No: " + roll + "\n");
            outputArea.append("Semester: " + semester + "\n");
            outputArea.append("GPA: " + gpa + " | CGPA: " + cgpa + "\n");
            outputArea.append("--------------------------------------\n");

            JOptionPane.showMessageDialog(null, "Student added successfully!");
            universityField.setText("");
            departmentField.setText("");
            nameField.setText("");
            rollField.setText("");
            semesterField.setText("");
            gpaField.setText("");
            cgpaField.setText("");
        });

        viewButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Displaying all students.");
            outputArea.append("Viewing all students...\n");
        });

        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}
