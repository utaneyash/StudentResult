package model;

public class Student {
    private int id;
    private String name;
    private String rollNo;
    private int mathMarks;
    private int scienceMarks;
    private int englishMarks;
    private int totalMarks;
    private String grade;

    public Student() {}

    public Student(String name, String rollNo, int mathMarks, int scienceMarks, int englishMarks) {
        this.name = name;
        this.rollNo = rollNo;
        this.mathMarks = mathMarks;
        this.scienceMarks = scienceMarks;
        this.englishMarks = englishMarks;
        this.totalMarks = mathMarks + scienceMarks + englishMarks;
        this.grade = calculateGrade(this.totalMarks);
    }

    private String calculateGrade(int total) {
        double percentage = (total / 300.0) * 100;
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B";
        else if (percentage >= 60) return "C";
        else if (percentage >= 50) return "D";
        else return "F";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public int getMathMarks() { return mathMarks; }
    public void setMathMarks(int mathMarks) {
        this.mathMarks = mathMarks;
        updateTotal();
    }

    public int getScienceMarks() { return scienceMarks; }
    public void setScienceMarks(int scienceMarks) {
        this.scienceMarks = scienceMarks;
        updateTotal();
    }

    public int getEnglishMarks() { return englishMarks; }
    public void setEnglishMarks(int englishMarks) {
        this.englishMarks = englishMarks;
        updateTotal();
    }

    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    private void updateTotal() {
        this.totalMarks = this.mathMarks + this.scienceMarks + this.englishMarks;
        this.grade = calculateGrade(this.totalMarks);
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', rollNo='" + rollNo +
               "', total=" + totalMarks + ", grade='" + grade + "'}";
    }
}
