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
import java.sql.*;

public class UniversityGUI extends JFrame {

    private JTextField idField, nameField, locationField, emailField, contactField;
    private JTextArea outputArea;

    // ===== MYSQL DATABASE =====
    private static final String DB_URL = "jdbc:mysql://localhost:3306/university";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "shahid";

    public UniversityGUI() {
        setTitle("University Management (MySQL Database)");
        setSize(600, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ===== Input Panel =====
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        inputPanel.add(new JLabel("University ID (Required):"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("University Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Location:"));
        locationField = new JTextField();
        inputPanel.add(locationField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Contact:"));
        contactField = new JTextField();
        inputPanel.add(contactField);

        add(inputPanel, BorderLayout.NORTH);

        // ===== Buttons =====
        JPanel btnPanel = new JPanel();
        JButton addUpdateBtn = new JButton("Add / Update");
        JButton viewBtn = new JButton("View All");
        btnPanel.add(addUpdateBtn);
        btnPanel.add(viewBtn);
        add(btnPanel, BorderLayout.CENTER);

        // ===== Output =====
        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        addUpdateBtn.addActionListener(e -> saveOrUpdateUniversity());
        viewBtn.addActionListener(e -> displayAllUniversities());

        setVisible(true);
    }

    // ===== Save or Update (MySQL) =====
    private void saveOrUpdateUniversity() {

        if (idField.getText().isEmpty() || nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "University ID and Name are required!");
            return;
        }

        String sql = """
                INSERT INTO universities (uni_id, uni_name, location, email, contact)
                VALUES (?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE
                uni_name = VALUES(uni_name),
                location = VALUES(location),
                email = VALUES(email),
                contact = VALUES(contact)
                """;

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, idField.getText());
            ps.setString(2, nameField.getText());
            ps.setString(3, locationField.getText());
            ps.setString(4, emailField.getText());
            ps.setString(5, contactField.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "University saved in MySQL database!");
            clearFields();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    // ===== Load From MySQL =====
    private void displayAllUniversities() {
        outputArea.setText("");

        String sql = "SELECT * FROM universities";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                outputArea.append(
                        "ID: " + rs.getString("uni_id") +
                        ", Name: " + rs.getString("uni_name") +
                        ", Location: " + rs.getString("location") +
                        ", Email: " + rs.getString("email") +
                        ", Contact: " + rs.getString("contact") + "\n"
                );
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }

    // ===== Clear Fields =====
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        emailField.setText("");
        contactField.setText("");
    }
}
