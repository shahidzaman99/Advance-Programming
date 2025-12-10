/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package studentdbmanager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/*
 SIMPLE Student Database Manager
 Swing + JDBC + Basic Thread
*/

public class StudentDBManager extends JFrame {

    // === GUI Components ===
    JTextField tfFN, tfLN, tfAge, tfEmail, tfSearch;
    JTextArea status;
    JTable table;
    DefaultTableModel model;

    // === Database info ===
    String url = "jdbc:mysql://127.0.0.1:3306/shahid";
    String user = "root";
    String pass = "shahid";

    public StudentDBManager() {

        setTitle("StudentDB Manager");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- TOP PANEL ----------
        JPanel top = new JPanel(new GridLayout(5, 2, 5, 5));

        tfFN = new JTextField();
        tfLN = new JTextField();
        tfAge = new JTextField();
        tfEmail = new JTextField();

        top.add(new JLabel("First Name"));
        top.add(tfFN);

        top.add(new JLabel("Last Name"));
        top.add(tfLN);

        top.add(new JLabel("Age"));
        top.add(tfAge);

        top.add(new JLabel("Email"));
        top.add(tfEmail);

        JButton addBtn = new JButton("Add Student");
        JButton viewBtn = new JButton("View Students");

        top.add(addBtn);
        top.add(viewBtn);

        add(top, BorderLayout.NORTH);

        // ---------- TABLE ----------
        model = new DefaultTableModel(
                new String[]{"ID", "First Name", "Last Name", "Age", "Email"}, 0);

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ---------- BOTTOM PANEL ----------
        JPanel bottom = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel();
        tfSearch = new JTextField(5);
        JButton searchBtn = new JButton("Search by ID");

        searchPanel.add(new JLabel("ID"));
        searchPanel.add(tfSearch);
        searchPanel.add(searchBtn);

        status = new JTextArea(3, 20);
        status.setEditable(false);

        bottom.add(searchPanel, BorderLayout.NORTH);
        bottom.add(new JScrollPane(status), BorderLayout.CENTER);

        add(bottom, BorderLayout.SOUTH);

        // ---------- BUTTON EVENTS ----------
        addBtn.addActionListener(e -> addStudent());
        viewBtn.addActionListener(e -> viewStudents());
        searchBtn.addActionListener(e -> searchStudent());

        setVisible(true);
    }

    // ================= ADD STUDENT =================
    void addStudent() {

        new Thread(() -> {
            try {
                Connection con = DriverManager.getConnection(url, user, pass);
                String sql = "INSERT INTO students(first_name,last_name,age,email) VALUES (?,?,?,?)";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, tfFN.getText());
                ps.setString(2, tfLN.getText());
                ps.setInt(3, Integer.parseInt(tfAge.getText()));
                ps.setString(4, tfEmail.getText());

                ps.executeUpdate();
                status.setText("✅ Student Added Successfully");

                con.close();
            } catch (Exception e) {
                status.setText("❌ Error: " + e.getMessage());
            }
        }).start();
    }

    // ================= VIEW STUDENTS =================
    void viewStudents() {

        new Thread(() -> {
            try {
                model.setRowCount(0);

                Connection con = DriverManager.getConnection(url, user, pass);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM students");

                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getString("email")
                    });
                }

                status.setText("✅ Data Loaded");
                con.close();

            } catch (Exception e) {
                status.setText("❌ Error loading data");
            }
        }).start();
    }

    // ================= SEARCH STUDENT =================
    void searchStudent() {

        new Thread(() -> {
            try {
                int id = Integer.parseInt(tfSearch.getText());

                Connection con = DriverManager.getConnection(url, user, pass);
                String sql = "SELECT * FROM students WHERE id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    status.setText("✅ Found: " +
                            rs.getString("first_name") + " " +
                            rs.getString("last_name"));
                } else {
                    status.setText("❌ Student not found");
                }

                con.close();

            } catch (Exception e) {
                status.setText("❌ Error searching");
            }
        }).start();
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {
        new StudentDBManager();
    }
}
