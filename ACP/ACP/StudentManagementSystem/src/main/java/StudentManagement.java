/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.studentmanagementsystem;
import java.util.Scanner;

public class StudentManagement {
    static class Student {
        String name;
        int rollNo;
        int[] marks = new int[3];
        int total;
        double average;

        public Student(String name, int rollNo, int[] marks) {
            this.name = name;
            this.rollNo = rollNo;
            this.marks = marks;
            calculateResult();
        }

        public void calculateResult() {
            total = marks[0] + marks[1] + marks[2];
            average = total / 3.0;
        }
    }

    private static Student[] students = new Student[50];
    private static int count = 0;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Update Marks");
            System.out.println("3. Remove Student");
            System.out.println("4. View All Students");
            System.out.println("5. Search Student");
            System.out.println("6. Highest Scorer");
            System.out.println("7. Class Average");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: updateMarks(); break;
                case 3: removeStudent(); break;
                case 4: viewAllStudents(); break;
                case 5: searchStudent(); break;
                case 6: highestScorer(); break;
                case 7: classAverage(); break;
                case 8: 
                    System.out.println("Exiting... Total Students: " + count);
                    classAverage();
                    break;
                default: System.out.println("Invalid choice!");
            }
        } while (choice != 8);
    }

    private static void addStudent() {
        if (count >= 50) {
            System.out.println("Cannot add more students. Limit reached!");
            return;
        }
        System.out.print("Enter Roll Number: ");
        int roll = sc.nextInt();
        if (findStudentIndex(roll) != -1) {
            System.out.println("Roll number already exists!");
            return;
        }
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        int[] marks = new int[3];
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter marks for subject " + (i+1) + ": ");
            marks[i] = sc.nextInt();
            if (marks[i] < 0 || marks[i] > 100) {
                System.out.println("Invalid marks! Must be 0-100.");
                return;
            }
        }
        students[count++] = new Student(name, roll, marks);
        System.out.println("Student added successfully!");
    }

    private static void updateMarks() {
        System.out.print("Enter Roll Number: ");
        int roll = sc.nextInt();
        int idx = findStudentIndex(roll);
        if (idx == -1) {
            System.out.println("Student not found!");
            return;
        }
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter new marks for subject " + (i+1) + ": ");
            students[idx].marks[i] = sc.nextInt();
        }
        students[idx].calculateResult();
        System.out.println("Marks updated successfully!");
    }

    private static void removeStudent() {
        System.out.print("Enter Roll Number: ");
        int roll = sc.nextInt();
        int idx = findStudentIndex(roll);
        if (idx == -1) {
            System.out.println("Student not found!");
            return;
        }
        for (int i = idx; i < count - 1; i++) {
            students[i] = students[i+1];
        }
        students[--count] = null;
        System.out.println("Student removed successfully!");
    }

    private static void viewAllStudents() {
        if (count == 0) {
            System.out.println("No students to display!");
            return;
        }
        System.out.printf("%-10s %-20s %-10s %-10s\n", "Roll No", "Name", "Total", "Average");
        for (int i = 0; i < count; i++) {
            System.out.printf("%-10d %-20s %-10d %-10.2f\n", 
                students[i].rollNo, students[i].name, students[i].total, students[i].average);
        }
    }

    private static void searchStudent() {
        System.out.print("Enter Roll Number: ");
        int roll = sc.nextInt();
        int idx = findStudentIndex(roll);
        if (idx == -1) {
            System.out.println("Student not found!");
            return;
        }
        Student s = students[idx];
        System.out.println("Name: " + s.name);
        System.out.println("Roll No: " + s.rollNo);
        for (int i = 0; i < 3; i++) {
            System.out.println("Subject " + (i+1) + " Marks: " + s.marks[i]);
        }
        System.out.println("Total: " + s.total + " | Average: " + s.average);
    }

    private static void highestScorer() {
        if (count == 0) {
            System.out.println("No students available!");
            return;
        }
        Student top = students[0];
        for (int i = 1; i < count; i++) {
            if (students[i].total > top.total) {
                top = students[i];
            }
        }
        System.out.println("Highest Scorer: " + top.name + " (Roll " + top.rollNo + ") with " + top.total + " marks.");
    }

    private static void classAverage() {
        if (count == 0) {
            System.out.println("No students to calculate average!");
            return;
        }
        double totalSum = 0;
        for (int i = 0; i < count; i++) {
            totalSum += students[i].average;
        }
        System.out.println("Class Average: " + (totalSum / count));
    }

    private static int findStudentIndex(int roll) {
        for (int i = 0; i < count; i++) {
            if (students[i].rollNo == roll) {
                return i;
            }
        }
        return -1;
    }
}
