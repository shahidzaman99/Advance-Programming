/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package studentgrademanagementsystem;

/**
 *
 * @author szama
 */
import java.util.Scanner;

class Student {
    int rollNo;
    String name;
    int[] marks = new int[3];
    int total;
    double average;

    void calculateResult() {
        total = marks[0] + marks[1] + marks[2];
        average = total / 3.0;
    }
}

public class StudentGradeManagementSystem {
    int MAX_STUDENTS = 50;   // not final, can be changed if needed
    Student[] students = new Student[MAX_STUDENTS];
    int count = 0; // number of students
    Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        StudentGradeManagementSystem system = new StudentGradeManagementSystem();
        system.runMenu();
    }

    // Main menu
    void runMenu() {
        int choice;
        System.out.println("Welcome to Student Grade Management System");

        do {
            System.out.println("\n1. Add Student");
            System.out.println("2. Update Marks");
            System.out.println("3. Remove Student");
            System.out.println("4. View All Students");
            System.out.println("5. Search Student");
            System.out.println("6. Highest Scorer");
            System.out.println("7. Class Average");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            choice = input.nextInt();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: updateMarks(); break;
                case 3: removeStudent(); break;
                case 4: viewAllStudents(); break;
                case 5: searchStudent(); break;
                case 6: highestScorer(); break;
                case 7: classAverage(); break;
                case 8: exitProgram(); break;
                default: System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 8);
    }

    // Add Student
    void addStudent() {
        if (count >= MAX_STUDENTS) {
            System.out.println("Cannot add more students. Limit reached!");
            return;
        }

        Student s = new Student();
        System.out.print("Enter Roll No: ");
        int roll = input.nextInt();

        for (int i = 0; i < count; i++) {
            if (students[i].rollNo == roll) {
                System.out.println("Roll number already exists!");
                return;
            }
        }

        s.rollNo = roll;
        System.out.print("Enter Name: ");
        s.name = input.next();

        for (int i = 0; i < 3; i++) {
            System.out.print("Enter Marks in Subject " + (i + 1) + ": ");
            int m = input.nextInt();
            if (m < 0 || m > 100) {
                System.out.println("Invalid marks! Enter between 0–100.");
                i--; 
                continue;
            }
            s.marks[i] = m;
        }

        s.calculateResult();
        students[count++] = s;
        System.out.println("Student added successfully!");
    }

    // Update Marks
    void updateMarks() {
        System.out.print("Enter Roll No to update: ");
        int roll = input.nextInt();

        for (int i = 0; i < count; i++) {
            if (students[i].rollNo == roll) {
                for (int j = 0; j < 3; j++) {
                    System.out.print("Enter new Marks in Subject " + (j + 1) + ": ");
                    int m = input.nextInt();
                    if (m < 0 || m > 100) {
                        System.out.println("Invalid marks! Enter between 0–100.");
                        j--;
                        continue;
                    }
                    students[i].marks[j] = m;
                }
                students[i].calculateResult();
                System.out.println("Marks updated successfully!");
                return;
            }
        }
        System.out.println("Roll number not found!");
    }

    // Remove Student
    void removeStudent() {
        System.out.print("Enter Roll No to remove: ");
        int roll = input.nextInt();

        for (int i = 0; i < count; i++) {
            if (students[i].rollNo == roll) {
                for (int j = i; j < count - 1; j++) {
                    students[j] = students[j + 1];
                }
                students[--count] = null;
                System.out.println("Student removed successfully!");
                return;
            }
        }
        System.out.println("Roll number not found!");
    }

    // View All Students
    void viewAllStudents() {
        if (count == 0) {
            System.out.println("No students to display.");
            return;
        }
        System.out.printf("%-10s %-15s %-10s %-10s %-10s %-10s %-10s%n",
                "Roll No", "Name", "Sub1", "Sub2", "Sub3", "Total", "Average");
        for (int i = 0; i < count; i++) {
            Student s = students[i];
            System.out.printf("%-10d %-15s %-10d %-10d %-10d %-10d %-10.2f%n",
                    s.rollNo, s.name, s.marks[0], s.marks[1], s.marks[2], s.total, s.average);
        }
    }

    // Search Student
    void searchStudent() {
        System.out.print("Enter Roll No to search: ");
        int roll = input.nextInt();

        for (int i = 0; i < count; i++) {
            if (students[i].rollNo == roll) {
                Student s = students[i];
                System.out.println("Student Found:");
                System.out.printf("Roll No: %d, Name: %s, Marks: %d, %d, %d, Total: %d, Average: %.2f%n",
                        s.rollNo, s.name, s.marks[0], s.marks[1], s.marks[2], s.total, s.average);
                return;
            }
        }
        System.out.println("Roll number not found!");
    }

    // Highest Scorer
    void highestScorer() {
        if (count == 0) {
            System.out.println("No students available.");
            return;
        }

        Student top = students[0];
        for (int i = 1; i < count; i++) {
            if (students[i].total > top.total) {
                top = students[i];
            }
        }

        System.out.println("Highest Scorer: " + top.name + " (Roll No: " + top.rollNo + ")");
        System.out.println("Total Marks: " + top.total + ", Average: " + top.average);
    }

    // Class Average
    void classAverage() {
        if (count == 0) {
            System.out.println("No students available.");
            return;
        }
        int sum = 0;
        for (int i = 0; i < count; i++) {
            sum += students[i].total;
        }
        double avg = sum / (count * 3.0);
        System.out.printf("Class Average Marks: %.2f%n", avg);
    }

    // Exit
    void exitProgram() {
        System.out.println("Exiting program...");
        System.out.println("Total Students: " + count);
        classAverage();
    }
}
