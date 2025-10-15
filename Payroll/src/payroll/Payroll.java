/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package payroll;

/**
 *
 * @author szama
 */
import java.util.*;

// ========== INTERFACE ==========
interface Payable {
    double calculateNetSalary();
    void generatePayslip();
}

// ========== ABSTRACT CLASS ==========
abstract class Employee {
    private String empId;
    private String name;
    private double basicSalary;

    public Employee(String empId, String name, double basicSalary) {
        this.empId = empId;
        this.name = name;
        this.basicSalary = basicSalary;
    }

    // Encapsulation (getters & setters)
    public String getEmpId() { return empId; }
    public String getName() { return name; }
    public double getBasicSalary() { return basicSalary; }
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }

    // Abstraction
    public abstract double calculateTax();
}

// ========== PERMANENT EMPLOYEE CLASS ==========
class PermanentEmployee extends Employee implements Payable {
    private double bonus;

    public PermanentEmployee(String empId, String name, double basicSalary, double bonus) {
        super(empId, name, basicSalary);
        this.bonus = bonus;
    }

    public double getBonus() { return bonus; }

    @Override
    public double calculateTax() {
        return 0.10 * (getBasicSalary() + bonus);
    }

    @Override
    public double calculateNetSalary() {
        return (getBasicSalary() + bonus) - calculateTax();
    }

    @Override
    public void generatePayslip() {
        System.out.println("===== Payslip =====");
        System.out.println("Employee ID: " + getEmpId());
        System.out.println("Name: " + getName());
        System.out.println("Type: Permanent");
        System.out.printf("Basic Salary: %.2f%n", getBasicSalary());
        System.out.printf("Bonus: %.2f%n", bonus);
        System.out.printf("Tax: %.2f%n", calculateTax());
        System.out.printf("Net Salary: %.2f%n", calculateNetSalary());
        System.out.println("===================");
    }
}

// ========== CONTRACT EMPLOYEE CLASS ==========
class ContractEmployee extends Employee implements Payable {
    private int contractDuration; // in months

    public ContractEmployee(String empId, String name, double basicSalary, int contractDuration) {
        super(empId, name, basicSalary);
        this.contractDuration = contractDuration;
    }

    public int getContractDuration() { return contractDuration; }

    @Override
    public double calculateTax() {
        return 0.05 * getBasicSalary();
    }

    @Override
    public double calculateNetSalary() {
        return getBasicSalary() - calculateTax();
    }

    @Override
    public void generatePayslip() {
        System.out.println("===== Payslip =====");
        System.out.println("Employee ID: " + getEmpId());
        System.out.println("Name: " + getName());
        System.out.println("Type: Contract");
        System.out.printf("Basic Salary: %.2f%n", getBasicSalary());
        System.out.println("Contract Duration: " + contractDuration + " months");
        System.out.printf("Tax: %.2f%n", calculateTax());
        System.out.printf("Net Salary: %.2f%n", calculateNetSalary());
        System.out.println("===================");
    }
}

// ========== MAIN CLASS ==========
public class Payroll {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Employee> employees = new ArrayList<>();
    static final int MAX_EMPLOYEES = 5;

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewAllEmployees();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 4:
                    highestNetSalary();
                    break;
                case 5:
                    averageSalary();
                    break;
                case 6:
                    generatePayslip();
                    break;
                case 7:
                    exitSummary();
                    return;
                default:
                    System.out.println("Invalid option! Try again.");
            }
        }
    }

    // Utility methods for safe input
    private static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            String s = readLine(prompt);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            String s = readLine(prompt);
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Employee Payroll Management System ---");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Search Employee");
        System.out.println("4. Highest Net Salary");
        System.out.println("5. Average Salary");
        System.out.println("6. Generate Payslip");
        System.out.println("7. Exit");
    }

    // Add Employee
    public static void addEmployee() {
        if (employees.size() >= MAX_EMPLOYEES) {
            System.out.println("Cannot add more employees (limit reached).");
            return;
        }

        String type = readLine("Enter Employee Type (Permanent/Contract): ");
        String id = readLine("Enter ID: ");

        // Check unique ID
        for (Employee e : employees) {
            if (e.getEmpId().equalsIgnoreCase(id)) {
                System.out.println("Employee ID already exists!");
                return;
            }
        }

        String name = readLine("Enter Name: ");
        double salary = readDouble("Enter Basic Salary: ");
        if (salary <= 0) {
            System.out.println("Salary must be positive!");
            return;
        }

        if (type.equalsIgnoreCase("Permanent")) {
            double bonus = readDouble("Enter Bonus: ");
            if (bonus < 0) {
                System.out.println("Bonus must be non-negative!");
                return;
            }
            employees.add(new PermanentEmployee(id, name, salary, bonus));
            System.out.println("Employee added successfully!");
        } else if (type.equalsIgnoreCase("Contract")) {
            int duration = readInt("Enter Contract Duration (months): ");
            if (duration <= 0) {
                System.out.println("Duration must be positive!");
                return;
            }
            employees.add(new ContractEmployee(id, name, salary, duration));
            System.out.println("Employee added successfully!");
        } else {
            System.out.println("Invalid employee type!");
        }
    }

    // View All Employees
    public static void viewAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found!");
            return;
        }

        System.out.printf("%-10s %-20s %-10s %-12s %-12s %-10s %-12s%n",
                "ID", "Name", "Type", "BasicSalary", "Bonus/Dur", "Tax", "NetSalary");
        System.out.println("--------------------------------------------------------------------------------");

        for (Employee e : employees) {
            if (e instanceof PermanentEmployee) {
                PermanentEmployee p = (PermanentEmployee) e;
                System.out.printf("%-10s %-20s %-10s %-12.2f %-12.2f %-10.2f %-12.2f%n",
                        p.getEmpId(), p.getName(), "Permanent",
                        p.getBasicSalary(), p.getBonus(), p.calculateTax(), p.calculateNetSalary());
            } else if (e instanceof ContractEmployee) {
                ContractEmployee c = (ContractEmployee) e;
                System.out.printf("%-10s %-20s %-10s %-12.2f %-12d %-10.2f %-12.2f%n",
                        c.getEmpId(), c.getName(), "Contract",
                        c.getBasicSalary(), c.getContractDuration(), c.calculateTax(), c.calculateNetSalary());
            }
        }
    }

    // Search Employee
    public static void searchEmployee() {
        String id = readLine("Enter Employee ID to search: ");
        for (Employee e : employees) {
            if (e.getEmpId().equalsIgnoreCase(id)) {
                System.out.println("Employee found!");
                Payable p = (Payable) e;  // cast to Payable (both subclasses implement it)
                p.generatePayslip();
                return;
            }
        }
        System.out.println("Employee not found!");
    }

    // Highest Net Salary
    public static void highestNetSalary() {
        if (employees.isEmpty()) {
            System.out.println("No employees found!");
            return;
        }
        Employee highest = employees.get(0);
        double max = ((Payable) highest).calculateNetSalary();

        for (Employee e : employees) {
            double net = ((Payable) e).calculateNetSalary();
            if (net > max) {
                max = net;
                highest = e;
            }
        }

        System.out.println("Employee with Highest Net Salary:");
        ((Payable) highest).generatePayslip();
    }

    // Average Salary
    public static void averageSalary() {
        if (employees.isEmpty()) {
            System.out.println("No employees found!");
            return;
        }

        double total = 0;
        for (Employee e : employees) {
            total += ((Payable) e).calculateNetSalary();
        }

        System.out.printf("Average Net Salary: %.2f%n", (total / employees.size()));
    }

    // Generate Payslip
    public static void generatePayslip() {
        String id = readLine("Enter Employee ID to generate payslip: ");
        for (Employee e : employees) {
            if (e.getEmpId().equalsIgnoreCase(id)) {
                ((Payable) e).generatePayslip();
                return;
            }
        }
        System.out.println("Employee not found!");
    }

    // Exit Summary
    public static void exitSummary() {
        System.out.println("\nTotal Employees: " + employees.size());
        if (!employees.isEmpty()) {
            double total = 0;
            for (Employee e : employees) {
                total += ((Payable) e).calculateNetSalary();
            }
            System.out.printf("Overall Average Net Salary: %.2f%n", (total / employees.size()));
        }
        System.out.println("Thank you for using Payroll System!");
    }
}

