package semesterproject.universitymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class StudentGUI extends JFrame {

    private JTextField rollField, nameField, universityField, departmentField, semesterField, gpaField, cgpaField;
    private JTextArea outputArea;

    public StudentGUI() {
        setTitle("Student Management");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font textFont = new Font("SansSerif", Font.PLAIN, 13);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 13);

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBackground(new Color(224, 235, 255));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        inputPanel.add(createLabel("Roll No:", labelFont));
        rollField = createTextField(textFont);
        inputPanel.add(rollField);

        inputPanel.add(createLabel("Name:", labelFont));
        nameField = createTextField(textFont);
        inputPanel.add(nameField);

        inputPanel.add(createLabel("University:", labelFont));
        universityField = createTextField(textFont);
        inputPanel.add(universityField);

        inputPanel.add(createLabel("Department:", labelFont));
        departmentField = createTextField(textFont);
        inputPanel.add(departmentField);

        inputPanel.add(createLabel("Semester:", labelFont));
        semesterField = createTextField(textFont);
        inputPanel.add(semesterField);

        inputPanel.add(createLabel("GPA:", labelFont));
        gpaField = createTextField(textFont);
        inputPanel.add(gpaField);

        inputPanel.add(createLabel("CGPA:", labelFont));
        cgpaField = createTextField(textFont);
        inputPanel.add(cgpaField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        addButton(buttonPanel, "Add Student", buttonFont, this::addStudent, new Color(0, 120, 215));
        addButton(buttonPanel, "View Students", buttonFont, this::viewStudents, new Color(46, 139, 87));
        addButton(buttonPanel, "Delete Student", buttonFont, this::deleteStudent, new Color(255, 99, 71));
        addButton(buttonPanel, "Edit Student", buttonFont, this::editStudent, new Color(255, 165, 0));
        addButton(buttonPanel, "Back", buttonFont, e -> dispose(), new Color(128, 128, 128));

        add(buttonPanel, BorderLayout.CENTER);

        outputArea = new JTextArea(10, 40);
        outputArea.setFont(textFont);
        outputArea.setBackground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JTextField createTextField(Font font) {
        JTextField textField = new JTextField();
        textField.setFont(font);
        return textField;
    }

    private void addButton(JPanel panel, String text, Font font, java.awt.event.ActionListener listener, Color color) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.addActionListener(listener);
        panel.add(button);
    }

    private void addStudent(java.awt.event.ActionEvent e) {
        String roll = rollField.getText();
        String name = nameField.getText();
        String university = universityField.getText();
        String department = departmentField.getText();
        String semester = semesterField.getText();
        String gpa = gpaField.getText();
        String cgpa = cgpaField.getText();

        outputArea.append("Student Added:\n");
        outputArea.append("Roll No: " + roll + "\n");
        outputArea.append("Name: " + name + "\n");
        outputArea.append("University: " + university + "\n");
        outputArea.append("Department: " + department + "\n");
        outputArea.append("Semester: " + semester + "\n");
        outputArea.append("GPA: " + gpa + " | CGPA: " + cgpa + "\n");
        outputArea.append("--------------------------------------\n");

        try {
            Socket socket = new Socket("", 5000);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            pw.println("SAVE_STUDENT " + roll + "," + name + "," + university + "," + department + "," + semester + "," + gpa + "," + cgpa);

            pw.close();
            socket.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Server not found!");
        }

        JOptionPane.showMessageDialog(null, "Student added successfully!");
        clearFields();
    }

    private void viewStudents(java.awt.event.ActionEvent e) {
        try {
            Socket socket = new Socket("localhost", 5000);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            pw.println("LOAD_STUDENT");

            outputArea.append("----- Students From Server -----\n");

            String line;
            while (!(line = br.readLine()).equals("END")) {
                if (line.equals("NO_DATA")) {
                    JOptionPane.showMessageDialog(null, "No student records on server.");
                    break;
                }
                outputArea.append(line + "\n");
            }

            socket.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "❌ Error: Cannot connect to server!\nStart server first.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent(java.awt.event.ActionEvent e) {
        String rollNo = rollField.getText();
        if (rollNo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a roll number to delete.");
            return;
        }

        try {
            Socket socket = new Socket("localhost", 5000);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            pw.println("DELETE_STUDENT " + rollNo);

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = br.readLine();

            if (response.equals("DELETED")) {
                JOptionPane.showMessageDialog(null, "Student record deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Student record not found.");
            }

            socket.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to the server.");
        }
    }

    private void editStudent(java.awt.event.ActionEvent e) {
        String rollNo = rollField.getText();
        if (rollNo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a roll number to edit.");
            return;
        }

        String updatedUniversity = universityField.getText();
        String updatedDepartment = departmentField.getText();
        String updatedName = nameField.getText();
        String updatedSemester = semesterField.getText();
        String updatedGpa = gpaField.getText();
        String updatedCgpa = cgpaField.getText();

        try {
            Socket socket = new Socket("localhost", 5000);
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            pw.println("EDIT_STUDENT " + rollNo + "," + updatedUniversity + "," + updatedDepartment + "," + updatedName + "," + updatedSemester + "," + updatedGpa + "," + updatedCgpa);

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = br.readLine();

            if (response.equals("EDITED")) {
                JOptionPane.showMessageDialog(null, "Student record updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Student record not found.");
            }

            socket.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to the server.");
        }
    }

    private void clearFields() {
        rollField.setText("");
        nameField.setText("");
        universityField.setText("");
        departmentField.setText("");
        semesterField.setText("");
        gpaField.setText("");
        cgpaField.setText("");
    }
}