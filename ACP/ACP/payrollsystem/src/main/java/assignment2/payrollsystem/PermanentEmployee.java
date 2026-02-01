/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.payrollsystem;


public class PermanentEmployee extends Employee implements Payable {
    private double bonus;

    public PermanentEmployee(int empId, String name, double basicSalary, double bonus) {
        super(empId, name, basicSalary);
        this.bonus = bonus;
    }

    public double getBonus() { return bonus; }

    @Override
    public double calculateTax() {
        return 0.10 * (basicSalary + bonus);
    }

    @Override
    public double calculateNetSalary() {
        return (basicSalary + bonus) - calculateTax();
    }

    @Override
    public void generatePayslip() {
        System.out.println("\n------ PAYSLIP (Permanent Employee) ------");
        System.out.println("Employee ID: " + empId);
        System.out.println("Name       : " + name);
        System.out.println("Basic Pay  : " + basicSalary);
        System.out.println("Bonus      : " + bonus);
        System.out.println("Tax (10%)  : " + calculateTax());
        System.out.println("Net Salary : " + calculateNetSalary());
        System.out.println("------------------------------------------\n");
    }
}

