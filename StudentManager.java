import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentManager {

    // ---------- INNER CLASS ----------
    static class Student implements Serializable {
        private static final long serialVersionUID = 1L;
        int rollNo;
        String name;
        String branch;
        int year;

        Student(int rollNo, String name, String branch, int year) {
            this.rollNo = rollNo;
            this.name = name;
            this.branch = branch;
            this.year = year;
        }

        @Override
        public String toString() {
            return rollNo + " - " + name + " (" + branch + ", Year " + year + ")";
        }
    }

    // ---------- FIELDS ----------
    private static final String DATA_FILE = "students.dat";
    private static List<Student> students = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    // ---------- MAIN ----------
    public static void main(String[] args) {
        loadData();

        int choice;
        do {
            System.out.println("\n==== STUDENT RESOURCE MANAGER ====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Save & Exit");
            System.out.print("Enter choice: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    saveData();
                    System.out.println("Data saved. Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (choice != 3);
    }

    // ---------- OPERATIONS ----------
    private static void addStudent() {
        System.out.println("\n-- Add New Student --");
        System.out.print("Roll No: ");
        int roll = Integer.parseInt(scanner.nextLine());

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Branch (e.g., CSE, IT): ");
        String branch = scanner.nextLine();

        System.out.print("Year (1–4): ");
        int year = Integer.parseInt(scanner.nextLine());

        Student s = new Student(roll, name, branch, year);
        students.add(s);
        System.out.println("✔ Student added successfully!");
    }

    private static void viewStudents() {
        System.out.println("\n-- All Students --");
        if (students.isEmpty()) {
            System.out.println("No students added yet.");
            return;
        }
        for (Student s : students) {
            System.out.println("• " + s);
        }
    }

    // ---------- FILE HANDLING ----------
    @SuppressWarnings("unchecked")
    private static void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return; // No data yet
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            students = (List<Student>) ois.readObject();
            System.out.println("Loaded " + students.size() + " students from file.");
        } catch (Exception e) {
            System.out.println("Could not load saved data (starting fresh).");
        }
    }

    private static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}
