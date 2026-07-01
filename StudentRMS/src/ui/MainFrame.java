package ui;

import model.Student;
import service.ResultService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private final ResultService service = new ResultService();
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField searchField;
    private JComboBox<String> gradeFilter;

    public MainFrame() {
        setTitle("Student Result Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildTablePanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        loadTable(service.getAllStudents());
        setVisible(true);
    }

    // ---- TOP: Search & Filter ----
    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Search & Filter"));

        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> loadTable(service.searchStudents(searchField.getText().trim())));

        gradeFilter = new JComboBox<>(new String[]{"All", "A+", "A", "B", "C", "D", "F"});
        JButton filterBtn = new JButton("Filter by Grade");
        filterBtn.addActionListener(e -> loadTable(service.filterByGrade((String) gradeFilter.getSelectedItem())));

        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> {
            searchField.setText("");
            gradeFilter.setSelectedIndex(0);
            loadTable(service.getAllStudents());
        });

        panel.add(new JLabel("Search:"));
        panel.add(searchField);
        panel.add(searchBtn);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(new JLabel("Grade:"));
        panel.add(gradeFilter);
        panel.add(filterBtn);
        panel.add(resetBtn);
        return panel;
    }

    // ---- CENTER: JTable ----
    private JScrollPane buildTablePanel() {
        String[] columns = {"ID", "Name", "Roll No", "Math", "Science", "English", "Total", "Grade"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        return new JScrollPane(table);
    }

    // ---- BOTTOM: Action Buttons ----
    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton addBtn = new JButton("Add Student");
        addBtn.setBackground(new Color(70, 130, 180));
        addBtn.setForeground(Color.WHITE);
        addBtn.setOpaque(true);
        addBtn.setBorderPainted(false);
        addBtn.addActionListener(e -> {
            new AddStudentForm(this, service, null);
            loadTable(service.getAllStudents());
        });

        JButton editBtn = new JButton("Edit Student");
        editBtn.setBackground(new Color(255, 165, 0));
        editBtn.setForeground(Color.BLACK);
        editBtn.setOpaque(true);
        editBtn.setBorderPainted(false);
        editBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { showWarning("Please select a student to edit."); return; }
            int id = (int) tableModel.getValueAt(row, 0);
            new AddStudentForm(this, service, service.getStudentById(id));
            loadTable(service.getAllStudents());
        });

        JButton deleteBtn = new JButton("Delete Student");
        deleteBtn.setBackground(new Color(220, 53, 69));
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setOpaque(true);
        deleteBtn.setBorderPainted(false);
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { showWarning("Please select a student to delete."); return; }
            int id = (int) tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteStudent(id);
                loadTable(service.getAllStudents());
            }
        });

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadTable(service.getAllStudents()));

        panel.add(addBtn);
        panel.add(editBtn);
        panel.add(deleteBtn);
        panel.add(refreshBtn);
        return panel;
    }

    // ---- Load data into JTable ----
    public void loadTable(List<Student> students) {
        tableModel.setRowCount(0);
        for (Student s : students) {
            tableModel.addRow(new Object[]{
                s.getId(), s.getName(), s.getRollNo(),
                s.getMathMarks(), s.getScienceMarks(), s.getEnglishMarks(),
                s.getTotalMarks(), s.getGrade()
            });
        }
    }

    private void showWarning(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Warning", JOptionPane.WARNING_MESSAGE);
    }
}