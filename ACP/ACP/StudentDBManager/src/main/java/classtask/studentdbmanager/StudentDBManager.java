package classtask.studentdbmanager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class StudentDBManager extends JFrame {

    private JTextField txtFirst, txtLast, txtAge, txtEmail, txtSearchID;
    private JButton btnAdd, btnView, btnSearch;
    private JTable table;
    private JTextArea statusArea;

    // Database Credentials
    private final String URL = "jdbc:mysql://localhost:3306/StudentDB?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASS = "root";

    public StudentDBManager() {

        setTitle("StudentDB Manager — Modern UI");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
       
        // ========= COLORS =========
        Color gradientStart = new Color(123, 67, 151);
        Color gradientEnd   = new Color(220, 36, 48);

        Color cardColor = new Color(255, 255, 255);
        Color tableBg = new Color(245, 245, 245);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 15);

        // ========= GRADIENT HEADER PANEL =========
        JPanel header = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, gradientStart,
                        getWidth(), getHeight(), gradientEnd
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        header.setPreferredSize(new Dimension(950, 130));
        header.setLayout(null);

        JLabel title = new JLabel("Student Database Manager");
        title.setFont(new Font("Segoe UI Black", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setBounds(30, 20, 600, 50);
        header.add(title);

        add(header, BorderLayout.NORTH);

        // ========= FORM CARD PANEL =========
        JPanel formCard = new JPanel();
        formCard.setBackground(cardColor);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        formCard.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblFirst = new JLabel("First Name:");
        lblFirst.setFont(labelFont);
        txtFirst = new JTextField(18);
        txtFirst.setFont(inputFont);
        txtFirst.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160), 2));

        JLabel lblLast = new JLabel("Last Name:");
        lblLast.setFont(labelFont);
        txtLast = new JTextField(18);
        txtLast.setFont(inputFont);
        txtLast.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160), 2));

        JLabel lblAge = new JLabel("Age:");
        lblAge.setFont(labelFont);
        txtAge = new JTextField(18);
        txtAge.setFont(inputFont);
        txtAge.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160), 2));

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(labelFont);
        txtEmail = new JTextField(18);
        txtEmail.setFont(inputFont);
        txtEmail.setBorder(BorderFactory.createLineBorder(new Color(160, 160, 160), 2));

        // add fields
        gbc.gridx=0; gbc.gridy=0; formCard.add(lblFirst, gbc);
        gbc.gridx=1; formCard.add(txtFirst, gbc);

        gbc.gridx=0; gbc.gridy=1; formCard.add(lblLast, gbc);
        gbc.gridx=1; formCard.add(txtLast, gbc);

        gbc.gridx=0; gbc.gridy=2; formCard.add(lblAge, gbc);
        gbc.gridx=1; formCard.add(txtAge, gbc);

        gbc.gridx=0; gbc.gridy=3; formCard.add(lblEmail, gbc);
        gbc.gridx=1; formCard.add(txtEmail, gbc);

        // ========= GLOW BUTTON =========
        btnAdd = new JButton("Add Student");
        btnAdd.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setBackground(new Color(34, 166, 179));
        btnAdd.setFocusPainted(false);
        btnAdd.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // hover animation
        btnAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAdd.setBackground(new Color(21, 101, 192));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAdd.setBackground(new Color(34, 166, 179));
            }
        });

        gbc.gridx=1; gbc.gridy=4;
        gbc.anchor = GridBagConstraints.EAST;
        formCard.add(btnAdd, gbc);

        add(formCard, BorderLayout.WEST);

        // ========= TABLE PANEL =========
        table = new JTable();
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setBackground(tableBg);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // ========= STATUS AREA =========
        statusArea = new JTextArea(5, 30);
        statusArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusArea.setEditable(false);
        statusArea.setLineWrap(true);
        statusArea.setWrapStyleWord(true);
        JScrollPane statusScroll = new JScrollPane(statusArea);
        statusScroll.setBorder(BorderFactory.createTitledBorder("Status / Output"));

        // Combine table and status area
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(tableScroll, BorderLayout.CENTER);
        centerPanel.add(statusScroll, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        // ========= SEARCH PANEL =========
        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);

        txtSearchID = new JTextField(10);
        txtSearchID.setFont(inputFont);

        btnSearch = new JButton("Search");
        btnSearch.setFont(labelFont);
        btnSearch.setBackground(new Color(255, 153, 51));
        btnSearch.setForeground(Color.WHITE);

        btnView = new JButton("View All");
        btnView.setFont(labelFont);
        btnView.setBackground(new Color(52, 152, 219));
        btnView.setForeground(Color.WHITE);

        bottom.add(new JLabel("Search ID: "));
        bottom.add(txtSearchID);
        bottom.add(btnSearch);
        bottom.add(btnView);

        add(bottom, BorderLayout.SOUTH);

        // Events
        btnAdd.addActionListener(e -> addStudent());
        btnView.addActionListener(e -> viewStudents());
        btnSearch.addActionListener(e -> searchStudent());

        setVisible(true);
    }

    // ---------------------------------------------------
    //  ADD STUDENT
    // ---------------------------------------------------
    private void addStudent() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            protected Void doInBackground() throws Exception {
                try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
                    String sql = "INSERT INTO students(first_name,last_name,age,email) VALUES (?,?,?,?)";
                    PreparedStatement stmt = con.prepareStatement(sql);

                    stmt.setString(1, txtFirst.getText());
                    stmt.setString(2, txtLast.getText());
                    stmt.setInt(3, Integer.parseInt(txtAge.getText()));
                    stmt.setString(4, txtEmail.getText());

                    stmt.executeUpdate();
                    statusArea.append("Student Added Successfully!\n");
                } catch (Exception ex) {
                    statusArea.append("Error: " + ex.getMessage() + "\n");
                }
                return null;
            }
        };
        worker.execute();
    }

    // ---------------------------------------------------
    //  VIEW STUDENTS
    // ---------------------------------------------------
    private void viewStudents() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            protected Void doInBackground() throws Exception {
                try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
                    String sql = "SELECT * FROM students";
                    PreparedStatement stmt = con.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery();

                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    if (model.getColumnCount() == 0) {
                        model.setColumnIdentifiers(new Object[]{"ID", "First Name", "Last Name", "Age", "Email"});
                    }
                    model.setRowCount(0); // refresh table

                    while (rs.next()) {
                        model.addRow(new Object[]{
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getInt("age"),
                                rs.getString("email")
                        });
                    }

                    statusArea.append("Loaded all students.\n");

                } catch (Exception ex) {
                    statusArea.append("Error: " + ex.getMessage() + "\n");
                }

                return null;
            }
        };
        worker.execute();
    }

    // ---------------------------------------------------
    //  SEARCH STUDENT
    // ---------------------------------------------------
    private void searchStudent() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            protected Void doInBackground() throws Exception {
                try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
                    String sql = "SELECT * FROM students WHERE id=?";
                    PreparedStatement stmt = con.prepareStatement(sql);

                    stmt.setInt(1, Integer.parseInt(txtSearchID.getText()));
                    ResultSet rs = stmt.executeQuery();

                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    if (model.getColumnCount() == 0) {
                        model.setColumnIdentifiers(new Object[]{"ID", "First Name", "Last Name", "Age", "Email"});
                    }
                    model.setRowCount(0);

                    if (rs.next()) {
                        model.addRow(new Object[]{
                                rs.getInt("id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getInt("age"),
                                rs.getString("email")
                        });
                        statusArea.append("Student Found.\n");
                    } else {
                        statusArea.append("No student found.\n");
                    }

                } catch (Exception ex) {
                    statusArea.append("Error: " + ex.getMessage() + "\n");
                }

                return null;
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentDBManager::new);
    }
}
