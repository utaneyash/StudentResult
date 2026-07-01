package service;

import dao.StudentDAO;
import model.Student;
import util.GradeCalculator;

import java.util.List;

public class ResultService {

    private final StudentDAO dao = new StudentDAO();

    public boolean addStudent(String name, String rollNo, int math, int science, int english) {
        if (name.isEmpty() || rollNo.isEmpty()) {
            System.err.println("Name and Roll No cannot be empty.");
            return false;
        }
        if (!isValidMarks(math) || !isValidMarks(science) || !isValidMarks(english)) {
            System.err.println("Marks must be between 0 and 100.");
            return false;
        }
        Student student = new Student(name, rollNo, math, science, english);
        return dao.addStudent(student);
    }

    public boolean updateStudent(int id, String name, String rollNo, int math, int science, int english) {
        if (!isValidMarks(math) || !isValidMarks(science) || !isValidMarks(english)) {
            System.err.println("Marks must be between 0 and 100.");
            return false;
        }
        int total = math + science + english;
        String grade = GradeCalculator.calculate(total, 300);

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setRollNo(rollNo);
        student.setMathMarks(math);
        student.setScienceMarks(science);
        student.setEnglishMarks(english);
        student.setTotalMarks(total);
        student.setGrade(grade);
        return dao.updateStudent(student);
    }

    public boolean deleteStudent(int id) {
        return dao.deleteStudent(id);
    }

    public List<Student> getAllStudents() {
        return dao.getAllStudents();
    }

    public List<Student> searchStudents(String keyword) {
        return dao.searchStudents(keyword);
    }

    public List<Student> filterByGrade(String grade) {
        return dao.filterByGrade(grade);
    }

    public Student getStudentById(int id) {
        return dao.getStudentById(id);
    }

    private boolean isValidMarks(int marks) {
        return marks >= 0 && marks <= 100;
    }
}
