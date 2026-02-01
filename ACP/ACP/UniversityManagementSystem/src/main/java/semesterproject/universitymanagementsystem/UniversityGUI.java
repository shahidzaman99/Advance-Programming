package semesterproject.universitymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class UniversityGUI extends JFrame {

    private JTextField idField, nameField, locationField, emailField, contactField;
    private JTextArea outputArea;

    public UniversityGUI() {
        setTitle("University Management");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240));

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);
        Font textFont = new Font("SansSerif", Font.PLAIN, 13);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 13);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 12, 12));
        inputPanel.setBackground(new Color(224, 235, 255));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel idLabel = new JLabel("University ID:");
        idLabel.setFont(labelFont);
        inputPanel.add(idLabel);
        idField = new JTextField();
        idField.setFont(textFont);
        inputPanel.add(idField);

        JLabel nameLabel = new JLabel("University Name:");
        nameLabel.setFont(labelFont);
        inputPanel.add(nameLabel);
        nameField = new JTextField();
        nameField.setFont(textFont);
        inputPanel.add(nameField);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(labelFont);
        inputPanel.add(locationLabel);
        locationField = new JTextField();
        locationField.setFont(textFont);
        inputPanel.add(locationField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        inputPanel.add(emailLabel);
        emailField = new JTextField();
        emailField.setFont(textFont);
        inputPanel.add(emailField);

        JLabel contactLabel = new JLabel("Contact No:");
        contactLabel.setFont(labelFont);
        inputPanel.add(contactLabel);
        contactField = new JTextField();
        contactField.setFont(textFont);
        inputPanel.add(contactField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("Add University");
        addButton.setFont(buttonFont);
        addButton.setBackground(new Color(0, 120, 215));
        addButton.setForeground(Color.WHITE);

        JButton viewButton = new JButton("View Universities");
        viewButton.setFont(buttonFont);
        viewButton.setBackground(new Color(46, 139, 87));
        viewButton.setForeground(Color.WHITE);

        JButton deleteButton = new JButton("Delete University");
        deleteButton.setFont(buttonFont);
        deleteButton.setBackground(new Color(255, 99, 71));
        deleteButton.setForeground(Color.WHITE);

        JButton editButton = new JButton("Edit University");
        editButton.setFont(buttonFont);
        editButton.setBackground(new Color(255, 165, 0));
        editButton.setForeground(Color.WHITE);

        JButton backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setBackground(new Color(128, 128, 128));
        backButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.CENTER);

        outputArea = new JTextArea(10, 30);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        outputArea.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String location = locationField.getText();
            String email = emailField.getText();
            String contact = contactField.getText();

            outputArea.append("University Added:\n");
            outputArea.append("ID: " + id + "\n");
            outputArea.append("Name: " + name + "\n");
            outputArea.append("Location: " + location + "\n");
            outputArea.append("Email: " + email + "\n");
            outputArea.append("Contact No: " + contact + "\n");
            outputArea.append("--------------------------------------\n\n");

            try {
                Socket socket = new Socket("localhost", 5000);
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

                pw.println("SAVE_UNIVERSITY " + id + "," + name + "," + location + "," + email + "," + contact);

                pw.close();
                socket.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Server not found!");
            }

            JOptionPane.showMessageDialog(null, "University added successfully!");

            idField.setText("");
            nameField.setText("");
            locationField.setText("");
            emailField.setText("");
            contactField.setText("");
        });

        viewButton.addActionListener(e -> {
            try {
                Socket socket = new Socket("localhost", 5000);
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                pw.println("LOAD_UNIVERSITY");

                outputArea.append("----- Universities From Server -----\n");

                String line;
                while (!(line = br.readLine()).equals("END")) {
                    if (line.equals("NO_DATA")) {
                        JOptionPane.showMessageDialog(null, "No university records on server.");
                        break;
                    }
                    outputArea.append(line + "\n");
                }

                socket.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        "❌ Cannot connect to server!\nPlease start the server first.",
                        "Server Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            String id = idField.getText();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter University ID to delete.");
                return;
            }

            try {
                Socket socket = new Socket("localhost", 5000);
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                pw.println("DELETE_UNIVERSITY " + id);

                String response = br.readLine();

                if (response.equals("DELETED")) {
                    JOptionPane.showMessageDialog(null, "University deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Record not found!");
                }

                socket.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error connecting to server!");
            }
        });

        editButton.addActionListener(e -> {
            String id = idField.getText();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter University ID to edit.");
                return;
            }

            String newName = nameField.getText();
            String newLocation = locationField.getText();
            String newEmail = emailField.getText();
            String newContact = contactField.getText();

            try {
                Socket socket = new Socket("localhost", 5000);
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                pw.println("EDIT_UNIVERSITY " + id + "," + newName + "," + newLocation + "," + newEmail + "," + newContact);

                String response = br.readLine();

                if (response.equals("EDITED")) {
                    JOptionPane.showMessageDialog(null, "University updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Record not found!");
                }

                socket.close();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error connecting to server!");
            }
        });

        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    public static void main(String[] args) {
        new UniversityGUI();
    }
}
