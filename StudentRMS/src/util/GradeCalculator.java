package util;

public class GradeCalculator {

    public static String calculate(int total, int maxMarks) {
        double percentage = ((double) total / maxMarks) * 100;
        if (percentage >= 90) return "A+";
        else if (percentage >= 80) return "A";
        else if (percentage >= 70) return "B";
        else if (percentage >= 60) return "C";
        else if (percentage >= 50) return "D";
        else return "F";
    }

    public static double getPercentage(int total, int maxMarks) {
        return ((double) total / maxMarks) * 100;
    }

    public static String getRemarks(String grade) {
        switch (grade) {
            case "A+": return "Outstanding";
            case "A":  return "Excellent";
            case "B":  return "Very Good";
            case "C":  return "Good";
            case "D":  return "Pass";
            case "F":  return "Fail";
            default:   return "Unknown";
        }
    }
}
