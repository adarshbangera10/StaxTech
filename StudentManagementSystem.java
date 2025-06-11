import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class StudentManagementSystem extends JFrame {
    private JTextField nameField, rollField, gradeField, dobField, emailField, phoneField;
    private JComboBox<String> genderBox;
    private JButton addButton, clearButton, imageButton;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JLabel imageLabel;
    private String selectedImagePath = "";

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Student Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(40, 90, 160));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                "Add New Student", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14), Color.DARK_GRAY
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField();
        rollField = new JTextField();
        gradeField = new JTextField();
        dobField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});

        imageLabel = new JLabel("No Image", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(140, 160));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imageButton = new JButton("Choose Image");

        // Image chooser logic
        imageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select Student Image");
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                selectedImagePath = selectedFile.getAbsolutePath();
                ImageIcon icon = new ImageIcon(new ImageIcon(selectedImagePath).getImage().getScaledInstance(140, 160, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
                imageLabel.setText("");
            }
        });

        // Row 1
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Name :"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Usn :"), gbc);
        gbc.gridx = 3;
        inputPanel.add(rollField, gbc);

        // Row 2
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Gender :"), gbc);
        gbc.gridx = 1;
        inputPanel.add(genderBox, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("DOB (dd-mm-yyyy) :"), gbc);
        gbc.gridx = 3;
        inputPanel.add(dobField, gbc);

        // Row 3
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Email ID :"), gbc);
        gbc.gridx = 1;
        inputPanel.add(emailField, gbc);

        gbc.gridx = 2;
        inputPanel.add(new JLabel("Phone Number :"), gbc);
        gbc.gridx = 3;
        inputPanel.add(phoneField, gbc);

        // Row 4
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Branch :"), gbc);
        gbc.gridx = 1;
        inputPanel.add(gradeField, gbc);

        gbc.gridx = 2;
        addButton = new JButton("Add Student");
        addButton.setBackground(new Color(30, 160, 90));
        addButton.setForeground(Color.WHITE);
        inputPanel.add(addButton, gbc);

        gbc.gridx = 3;
        clearButton = new JButton("Clear Fields");
        clearButton.setBackground(new Color(200, 70, 60));
        clearButton.setForeground(Color.WHITE);
        inputPanel.add(clearButton, gbc);

        // Row 5: Image
        gbc.gridx = 4; gbc.gridy = 0; gbc.gridheight = 4;
        JPanel imagePanel = new JPanel(new BorderLayout(5, 5));
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.add(imageButton, BorderLayout.SOUTH);
        inputPanel.add(imagePanel, gbc);
        gbc.gridheight = 1;

        // ===== Table Panel =====
        String[] columnNames = {"Name", "USN", "Gender", "DOB", "Email", "Phone", "Grade", "Image Path"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                "Student Records", TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14), Color.DARK_GRAY
        ));

        addButton.addActionListener(e -> addStudent());
        clearButton.addActionListener(e -> clearFields());

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String roll = rollField.getText().trim();
        String gender = (String) genderBox.getSelectedItem();
        String dob = dobField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String grade = gradeField.getText().trim();

        if (name.isEmpty() || roll.isEmpty() || dob.isEmpty() || email.isEmpty() || phone.isEmpty() || grade.isEmpty() || selectedImagePath.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields including image must be filled!", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.addRow(new Object[]{name, roll, gender, dob, email, phone, grade, selectedImagePath});
        JOptionPane.showMessageDialog(this, "Student added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void clearFields() {
        nameField.setText("");
        rollField.setText("");
        dobField.setText("");
        emailField.setText("");
        phoneField.setText("");
        gradeField.setText("");
        genderBox.setSelectedIndex(0);
        imageLabel.setIcon(null);
        imageLabel.setText("No Image");
        selectedImagePath = "";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementSystem::new);
    }
}
