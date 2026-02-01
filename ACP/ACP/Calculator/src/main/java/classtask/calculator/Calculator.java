package classtask.calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    private JTextField textField;
    private double num1, num2, result;
    private char operator;

    public Calculator() {
        setTitle("*** Calculator ***");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#1C1C1C"));
        
        textField = new JTextField();
        textField.setEditable(false);
        textField.setFont(new Font("Arial", Font.BOLD, 24));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        add(textField, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.addActionListener(this);
            button.setOpaque(true);
            
            if (text.equals("C")) {
                button.setBackground(Color.decode("#D4D4D2"));
                button.setForeground(Color.WHITE);
            } else if ("/*-+=".contains(text)) {
                button.setBackground(Color.decode("#FF9500"));
                button.setForeground(Color.BLACK);
            } else {
                button.setBackground(Color.decode("#505050"));
                button.setForeground(Color.BLACK);
            }

            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9')) {
            textField.setText(textField.getText() + command);
        } else if (command.charAt(0) == 'C') {
            textField.setText("");
            num1 = num2 = result = 0;
        } else if (command.charAt(0) == '=') {
            num2 = Double.parseDouble(textField.getText());
            switch (operator) {
                case '+' -> result = num1 + num2;
                case '-' -> result = num1 - num2;
                case '*' -> result = num1 * num2;
                case '/' -> {
                    if (num2 == 0) {
                        textField.setText("Error");
                        num1 = num2 = result = 0;
                        return;
                    } else {
                        result = num1 / num2;
                    }
                }
            }
            textField.setText(String.valueOf(result));
            num1 = result;
        } else {
            num1 = Double.parseDouble(textField.getText());
            operator = command.charAt(0);
            textField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }
}
