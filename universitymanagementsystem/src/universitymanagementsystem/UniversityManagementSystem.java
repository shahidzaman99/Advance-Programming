/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package universitymanagementsystem;

import java.util.Scanner;

public class UniversityManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== UNIVERSITY MANAGEMENT SYSTEM ===");
            System.out.println("1. Student Menu");
            System.out.println("2. University Menu");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> Student.mainMenu();
                case 2 -> University.universityMenu();
                case 3 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}
