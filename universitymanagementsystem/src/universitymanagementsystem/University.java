/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package universitymanagementsystem;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class University {
    private String uniID, uniName, location, email, contactNo;

    public University(String uniID, String uniName, String location, String email, String contactNo) {
        this.uniID = uniID;
        this.uniName = uniName;
        this.location = location;
        this.email = email;
        this.contactNo = contactNo;
    }

    public String getUniID() { return uniID; }
    public String getUniName() { return uniName; }
    public String getLocation() { return location; }
    public String getEmail() { return email; }
    public String getContactNo() { return contactNo; }

    public void displayInfo() {
        System.out.println(uniID + " | " + uniName + " | " + location);
    }

    // Console Menu Method
    public static void universityMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Add Uni  2. View All");
        int choice = sc.nextInt(); sc.nextLine();
        
        try {
            if (choice == 1) {
                System.out.print("ID: "); String id = sc.nextLine();
                System.out.print("Name: "); String name = sc.nextLine();
                DBManager.saveUniversity(new University(id, name, "N/A", "N/A", "N/A"));
                System.out.println("Saved to DB!");
            } else if (choice == 2) {
                for (University u : DBManager.loadAllUniversities()) u.displayInfo();
            }
        } catch (SQLException e) { System.out.println("DB Error: " + e.getMessage()); }
    }
}