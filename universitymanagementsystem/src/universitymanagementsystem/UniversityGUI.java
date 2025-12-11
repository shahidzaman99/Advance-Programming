/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package universitymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UniversityGUI extends JFrame {
    private JTextField idField, nameField, locationField, emailField, contactField;
    private JTextArea outputArea;

    public UniversityGUI() {
        setTitle("University Management (DB)");
        setSize(600, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("University ID:")); idField = new JTextField(); inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:")); nameField = new JTextField(); inputPanel.add(nameField);
        inputPanel.add(new JLabel("Location:")); locationField = new JTextField(); inputPanel.add(locationField);
        inputPanel.add(new JLabel("Email:")); emailField = new JTextField(); inputPanel.add(emailField);
        inputPanel.add(new JLabel("Contact:")); contactField = new JTextField(); inputPanel.add(contactField);
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
                University u = new University(idField.getText(), nameField.getText(), locationField.getText(), emailField.getText(), contactField.getText());
                DBManager.saveUniversity(u);
                JOptionPane.showMessageDialog(this, "University Saved to Database!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        viewBtn.addActionListener(e -> {
            outputArea.setText("");
            try {
                for (University u : DBManager.loadAllUniversities()) {
                    outputArea.append(u.getUniID() + " - " + u.getUniName() + "\n");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}