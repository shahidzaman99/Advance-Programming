package semesterproject.universitymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StudentGUI extends JFrame {

    private JTextField rollField, nameField, universityField, locationField, emailField, contactField, departmentField, semesterField, gpaField, cgpaField;
    private JTextArea outputArea;

    public StudentGUI() {
        setTitle("Student Management");
        setSize(800, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font textFont = new Font("SansSerif", Font.PLAIN, 13);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 13);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(10, 2, 10, 10));
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

        inputPanel.add(createLabel("Location:", labelFont));
        locationField = createTextField(textFont);
        inputPanel.add(locationField);

        inputPanel.add(createLabel("Email:", labelFont));
        emailField = createTextField(textFont);
        inputPanel.add(emailField);

        inputPanel.add(createLabel("Contact No:", labelFont));
        contactField = createTextField(textFont);
        inputPanel.add(contactField);

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

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        addButton(buttonPanel, "Add Student", buttonFont, e -> addStudent(), new Color(0, 120, 215));
        addButton(buttonPanel, "View Students", buttonFont, e -> viewStudents(), new Color(46, 139, 87));
        addButton(buttonPanel, "Delete Student", buttonFont, e -> deleteStudent(), new Color(255, 99, 71));
        addButton(buttonPanel, "Edit Student", buttonFont, e -> editStudent(), new Color(255, 165, 0));
        addButton(buttonPanel, "Back", buttonFont, e -> dispose(), new Color(128, 128, 128));

        add(buttonPanel, BorderLayout.CENTER);

        // Output Area
        outputArea = new JTextArea(12, 40);
        outputArea.setFont(textFont);
        outputArea.setBackground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        outputArea.setEditable(false);
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

    // ---------------- CRUD ----------------
    private void addStudent() {
        try {
            int roll = Integer.parseInt(rollField.getText());
            String name = nameField.getText();
            String university = universityField.getText();
            String location = locationField.getText();
            String email = emailField.getText();
            String contact = contactField.getText();
            String department = departmentField.getText();
            int semester = Integer.parseInt(semesterField.getText());
            float gpa = Float.parseFloat(gpaField.getText());
            float cgpa = Float.parseFloat(cgpaField.getText());

            Student s = new Student(
                    "U" + roll,
                    university,
                    location,
                    email,
                    contact,
                    department,
                    name,
                    roll
            );
            s.setSemester(semester);
            s.setGpa(gpa);
            s.setCgpa(cgpa);

            DBManager.saveStudent(s);

            outputArea.append("Student Added:\nRoll No: " + roll + ", Name: " + name + "\n--------------------------------------\n");
            JOptionPane.showMessageDialog(null, "Student added successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewStudents() {
        try {
            List<Student> students = DBManager.loadAllStudents();
            outputArea.append("----- Students From Database -----\n");
            for (Student s : students) {
                outputArea.append("Roll No: " + s.getRollNo() +
                        ", Name: " + s.getName() +
                        ", University: " + s.getUniName() +
                        ", Dept: " + s.getDeptName() +
                        ", Semester: " + s.getSemester() +
                        ", GPA: " + s.getGpa() +
                        ", CGPA: " + s.getCgpa() + "\n");
            }
            outputArea.append("--------------------------------------\n");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        try {
            int rollNo = Integer.parseInt(rollField.getText());
            DBManager.deleteStudent(rollNo);
            JOptionPane.showMessageDialog(null, "Student record deleted successfully.");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editStudent() {
        try {
            int rollNo = Integer.parseInt(rollField.getText());
            Student s = DBManager.getStudentByRoll(rollNo);
            if (s == null) {
                JOptionPane.showMessageDialog(null, "Student not found.");
                return;
            }

            s.setName(nameField.getText());
            s.setUniName(universityField.getText());
            s.setLocation(locationField.getText());
            s.setEmail(emailField.getText());
            s.setContactNo(contactField.getText());
            s.setDeptName(departmentField.getText());
            s.setSemester(Integer.parseInt(semesterField.getText()));
            s.setGpa(Float.parseFloat(gpaField.getText()));
            s.setCgpa(Float.parseFloat(cgpaField.getText()));

            DBManager.saveStudent(s);
            JOptionPane.showMessageDialog(null, "Student updated successfully.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        rollField.setText("");
        nameField.setText("");
        universityField.setText("");
        locationField.setText("");
        emailField.setText("");
        contactField.setText("");
        departmentField.setText("");
        semesterField.setText("");
        gpaField.setText("");
        cgpaField.setText("");
    }

    // ---------------- Main ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGUI::new);
    }
}
