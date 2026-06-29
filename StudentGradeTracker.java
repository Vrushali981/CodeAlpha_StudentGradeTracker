import java.util.*;

public class StudentGradeTracker {

    static class Student {
        String name;
        ArrayList<Double> grades;

        Student(String name) {
            this.name = name;
            this.grades = new ArrayList<>();
        }

        void addGrade(double grade) {
            grades.add(grade);
        }

        double getAverage() {
            if (grades.isEmpty()) return 0;
            double sum = 0;
            for (double g : grades) sum += g;
            return sum / grades.size();
        }

        double getHighest() {
            return grades.isEmpty() ? 0 : Collections.max(grades);
        }

        double getLowest() {
            return grades.isEmpty() ? 0 : Collections.min(grades);
        }

        String getLetterGrade() {
            double avg = getAverage();
            if (avg >= 90) return "A";
            else if (avg >= 80) return "B";
            else if (avg >= 70) return "C";
            else if (avg >= 60) return "D";
            else return "F";
        }

        @Override
        public String toString() {
            return String.format("%-20s | Avg: %6.2f | High: %6.2f | Low: %6.2f | Grade: %s",
                    name, getAverage(), getHighest(), getLowest(), getLetterGrade());
        }
    }

    private ArrayList<Student> students = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("========================================");
        System.out.println("       STUDENT GRADE TRACKER");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = getIntInput("Enter choice: ");
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> addGrade();
                case 3 -> viewStudent();
                case 4 -> printSummaryReport();
                case 5 -> { running = false; System.out.println("Goodbye!"); }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Add Student");
        System.out.println("2. Add Grade to Student");
        System.out.println("3. View Student Details");
        System.out.println("4. Print Summary Report");
        System.out.println("5. Exit");
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }
        students.add(new Student(name));
        System.out.println("Student '" + name + "' added successfully.");
    }

    private void addGrade() {
        if (students.isEmpty()) { System.out.println("No students found. Add a student first."); return; }
        listStudents();
        int idx = getIntInput("Select student number: ") - 1;
        if (idx < 0 || idx >= students.size()) { System.out.println("Invalid selection."); return; }
        double grade = getDoubleInput("Enter grade (0-100): ");
        if (grade < 0 || grade > 100) { System.out.println("Grade must be between 0 and 100."); return; }
        students.get(idx).addGrade(grade);
        System.out.println("Grade added successfully.");
    }

    private void viewStudent() {
        if (students.isEmpty()) { System.out.println("No students found."); return; }
        listStudents();
        int idx = getIntInput("Select student number: ") - 1;
        if (idx < 0 || idx >= students.size()) { System.out.println("Invalid selection."); return; }
        Student s = students.get(idx);
        System.out.println("\n--- Student Details ---");
        System.out.println("Name   : " + s.name);
        System.out.println("Grades : " + s.grades);
        System.out.printf("Average: %.2f%n", s.getAverage());
        System.out.printf("Highest: %.2f%n", s.getHighest());
        System.out.printf("Lowest : %.2f%n", s.getLowest());
        System.out.println("Letter : " + s.getLetterGrade());
    }

    private void printSummaryReport() {
        if (students.isEmpty()) { System.out.println("No students to report."); return; }
        System.out.println("\n========================================");
        System.out.println("           SUMMARY REPORT");
        System.out.println("========================================");
        System.out.printf("%-20s | %-10s | %-10s | %-10s | %s%n",
                "Name", "Average", "Highest", "Lowest", "Grade");
        System.out.println("-".repeat(70));
        for (Student s : students) System.out.println(s);
        System.out.println("-".repeat(70));

        // Class statistics
        double classAvg = students.stream().mapToDouble(Student::getAverage).average().orElse(0);
        double classHigh = students.stream().mapToDouble(Student::getHighest).max().orElse(0);
        double classLow = students.stream().mapToDouble(Student::getLowest).min().orElse(0);
        System.out.printf("%nClass Average: %.2f | Class High: %.2f | Class Low: %.2f%n",
                classAvg, classHigh, classLow);
        System.out.println("Total Students: " + students.size());
    }

    private void listStudents() {
        System.out.println("\n--- Students ---");
        for (int i = 0; i < students.size(); i++)
            System.out.println((i + 1) + ". " + students.get(i).name);
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        try { int val = Integer.parseInt(scanner.nextLine().trim()); return val; }
        catch (NumberFormatException e) { return -1; }
    }

    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        try { return Double.parseDouble(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    public static void main(String[] args) {
        new StudentGradeTracker().run();
    }
}