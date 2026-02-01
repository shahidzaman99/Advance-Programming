package assignment2.payrollsystem;

import java.util.*;

public class Payrollsystem {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n=== Employee Payroll Management System ===");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Highest Salary");
            System.out.println("5. Average Salary");
            System.out.println("6. Generate Payslip");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewEmployees();
                case 3 -> searchEmployee();
                case 4 -> showHighestSalary();
                case 5 -> showAverageSalary();
                case 6 -> generatePayslip();
                case 7 -> exitSummary();
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 7);
    }

    // Add Employee
    static void addEmployee() {
        if (employees.size() >= 5) {
            System.out.println("Limit reached! (Max 5 employees)");
            return;
        }

        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        for (Employee e : employees) {
            if (e.getEmpId() == id) {
                System.out.println("Employee ID already exists!");
                return;
            }
        }

        sc.nextLine(); // clear buffer
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Basic Salary: ");
        double salary = sc.nextDouble();

        System.out.print("Type (1 - Permanent / 2 - Contract): ");
        int type = sc.nextInt();

        if (type == 1) {
            System.out.print("Enter Bonus: ");
            double bonus = sc.nextDouble();
            employees.add(new PermanentEmployee(id, name, salary, bonus));
        } else if (type == 2) {
            System.out.print("Enter Contract Duration (months): ");
            int duration = sc.nextInt();
            employees.add(new ContractEmployee(id, name, salary, duration));
        } else {
            System.out.println("Invalid type!");
        }

        System.out.println("Employee added successfully!\n");
    }

    // View Employees
    static void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found!");
            return;
        }

        System.out.printf("%-5s %-15s %-10s %-15s %-15s %-10s %-12s\n",
                "ID", "Name", "Type", "Basic Salary", "Bonus/Duration", "Tax", "Net Salary");
        System.out.println("--------------------------------------------------------------------------");

        for (Employee e : employees) {
            String type = "", extra = "";
            double tax = 0, net = 0;

            if (e instanceof PermanentEmployee p) {
                type = "Permanent";
                extra = "" + p.getBonus();
                tax = p.calculateTax();
                net = p.calculateNetSalary();
            } else if (e instanceof ContractEmployee c) {
                type = "Contract";
                extra = c.getContractDuration() + " months";
                tax = c.calculateTax();
                net = c.calculateNetSalary();
            }

            System.out.printf("%-5d %-15s %-10s %-15.2f %-15s %-10.2f %-12.2f\n",
                    e.getEmpId(), e.getName(), type, e.getBasicSalary(), extra, tax, net);
        }
        System.out.println();
    }

    // Search Employee
    static void searchEmployee() {
        System.out.print("Enter Employee ID to search: ");
        int id = sc.nextInt();

        for (Employee e : employees) {
            if (e.getEmpId() == id && e instanceof Payable p) {
                p.generatePayslip();
                return;
            }
        }
        System.out.println("Employee not found!\n");
    }

    // Highest Salary
    static void showHighestSalary() {
        if (employees.isEmpty()) {
            System.out.println("No employees found!");
            return;
        }

        Employee highest = null;
        double max = 0;

        for (Employee e : employees) {
            if (e instanceof Payable p) {
                double net = p.calculateNetSalary();
                if (net > max) {
                    max = net;
                    highest = e;
                }
            }
        }

        if (highest instanceof Payable p) {
            System.out.println("\nEmployee with Highest Net Salary:");
            p.generatePayslip();
        }
    }

    // Average Salary
    static void showAverageSalary() {
        if (employees.isEmpty()) {
            System.out.println("No employees found!");
            return;
        }

        double total = 0;
        for (Employee e : employees) {
            if (e instanceof Payable p)
                total += p.calculateNetSalary();
        }

        System.out.println("Average Net Salary: " + total / employees.size() + "\n");
    }

    // Generate Payslip
    static void generatePayslip() {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();

        for (Employee e : employees) {
            if (e.getEmpId() == id && e instanceof Payable p) {
                p.generatePayslip();
                return;
            }
        }
        System.out.println("Employee not found!\n");
    }

    // Exit Summary
    static void exitSummary() {
        System.out.println("\nProgram Ended!");
        System.out.println("Total Employees: " + employees.size());
        showAverageSalary();
        System.out.println("Goodbye!");
    }
}