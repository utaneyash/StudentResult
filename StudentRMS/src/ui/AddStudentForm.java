package ui;

import model.Student;
import service.ResultService;

import javax.swing.*;
import java.awt.*;

public class AddStudentForm extends JDialog {

    private final ResultService service;
    private final Student existing;

    private JTextField nameField, rollField, mathField, scienceField, englishField;

    public AddStudentForm(JFrame parent, ResultService service, Student existing) {
        super(parent, existing == null ? "Add Student" : "Edit Student", true);
        this.service = service;
        this.existing = existing;

        setSize(400, 320);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        add(buildForm(), BorderLayout.CENTER);
        add(buildButtons(), BorderLayout.SOUTH);

        if (existing != null) prefillForm();
        setVisible(true);
    }

    private JPanel buildForm() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        nameField    = new JTextField();
        rollField    = new JTextField();
        mathField    = new JTextField();
        scienceField = new JTextField();
        englishField = new JTextField();

        panel.add(new JLabel("Student Name:"));   panel.add(nameField);
        panel.add(new JLabel("Roll Number:"));    panel.add(rollField);
        panel.add(new JLabel("Math Marks:"));     panel.add(mathField);
        panel.add(new JLabel("Science Marks:"));  panel.add(scienceField);
        panel.add(new JLabel("English Marks:"));  panel.add(englishField);
        return panel;
    }

    private JPanel buildButtons() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton saveBtn = new JButton(existing == null ? "Add" : "Update");
        saveBtn.setBackground(new Color(70, 130, 180));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setOpaque(true);
        saveBtn.setBorderPainted(false);
        saveBtn.addActionListener(e -> handleSave());

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());

        panel.add(saveBtn);
        panel.add(cancelBtn);
        return panel;
    }

    private void prefillForm() {
        nameField.setText(existing.getName());
        rollField.setText(existing.getRollNo());
        mathField.setText(String.valueOf(existing.getMathMarks()));
        scienceField.setText(String.valueOf(existing.getScienceMarks()));
        englishField.setText(String.valueOf(existing.getEnglishMarks()));
    }

    private void handleSave() {
        try {
            String name    = nameField.getText().trim();
            String rollNo  = rollField.getText().trim();
            int math       = Integer.parseInt(mathField.getText().trim());
            int science    = Integer.parseInt(scienceField.getText().trim());
            int english    = Integer.parseInt(englishField.getText().trim());

            if (name.isEmpty() || rollNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Roll No are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (math < 0 || math > 100 || science < 0 || science > 100 || english < 0 || english > 100) {
                JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success;
            if (existing == null) {
                success = service.addStudent(name, rollNo, math, science, english);
            } else {
                success = service.updateStudent(existing.getId(), name, rollNo, math, science, english);
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "Student record saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save record.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric marks.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}