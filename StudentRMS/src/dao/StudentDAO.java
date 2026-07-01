package dao;

import model.Student;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // INSERT
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, roll_no, math_marks, science_marks, english_marks, total_marks, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getRollNo());
            ps.setInt(3, student.getMathMarks());
            ps.setInt(4, student.getScienceMarks());
            ps.setInt(5, student.getEnglishMarks());
            ps.setInt(6, student.getTotalMarks());
            ps.setString(7, student.getGrade());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding student: " + e.getMessage());
            return false;
        }
    }

    // SELECT ALL
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching students: " + e.getMessage());
        }
        return list;
    }

    // SELECT BY ID
    public Student getStudentById(int id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.err.println("Error fetching student: " + e.getMessage());
        }
        return null;
    }

    // SEARCH BY NAME OR ROLL NO
    public List<Student> searchStudents(String keyword) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students WHERE name LIKE ? OR roll_no LIKE ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Error searching students: " + e.getMessage());
        }
        return list;
    }

    // FILTER BY GRADE
    public List<Student> filterByGrade(String grade) {
        List<Student> list = new ArrayList<>();
        String sql = grade.equals("All") ? "SELECT * FROM students" : "SELECT * FROM students WHERE grade = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (!grade.equals("All")) ps.setString(1, grade);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.err.println("Error filtering students: " + e.getMessage());
        }
        return list;
    }

    // UPDATE
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name=?, roll_no=?, math_marks=?, science_marks=?, english_marks=?, total_marks=?, grade=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getRollNo());
            ps.setInt(3, student.getMathMarks());
            ps.setInt(4, student.getScienceMarks());
            ps.setInt(5, student.getEnglishMarks());
            ps.setInt(6, student.getTotalMarks());
            ps.setString(7, student.getGrade());
            ps.setInt(8, student.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting student: " + e.getMessage());
            return false;
        }
    }

    // MAP ResultSet row to Student object
    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setName(rs.getString("name"));
        s.setRollNo(rs.getString("roll_no"));
        s.setMathMarks(rs.getInt("math_marks"));
        s.setScienceMarks(rs.getInt("science_marks"));
        s.setEnglishMarks(rs.getInt("english_marks"));
        s.setTotalMarks(rs.getInt("total_marks"));
        s.setGrade(rs.getString("grade"));
        return s;
    }
}
