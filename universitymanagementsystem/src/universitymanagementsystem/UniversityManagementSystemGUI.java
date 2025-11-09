/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package universitymanagementsystem;

import javax.swing.*;
import java.awt.*;

public class UniversityManagementSystemGUI extends JFrame {

    public UniversityManagementSystemGUI() {
        setTitle("University Management System");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(240, 240, 240));

        JLabel titleLabel = new JLabel("University Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(25, 25, 112));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 12, 12));
        buttonPanel.setBackground(new Color(224, 235, 255));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton studentMenuButton = createStyledButton("Student Menu");
        JButton universityMenuButton = createStyledButton("University Menu");
        JButton exitButton = createStyledButton("Exit");

        buttonPanel.add(studentMenuButton);
        buttonPanel.add(universityMenuButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);

        studentMenuButton.addActionListener(e -> new StudentGUI());
        universityMenuButton.addActionListener(e -> new UniversityGUI());
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to exit?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(25, 25, 112));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    public static void main(String[] args) {
        new UniversityManagementSystemGUI();
    }
}
