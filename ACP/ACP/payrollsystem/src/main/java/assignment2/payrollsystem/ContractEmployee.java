/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.payrollsystem;

public class ContractEmployee extends Employee implements Payable {
    private int contractDuration; 
    
    public ContractEmployee(int empId, String name, double basicSalary, int contractDuration) {
        super(empId, name, basicSalary);
        this.contractDuration = contractDuration;
    }

    public int getContractDuration() { return contractDuration; }

    @Override
    public double calculateTax() {
        return 0.05 * basicSalary;
    }

    @Override
    public double calculateNetSalary() {
        return basicSalary - calculateTax();
    }

    @Override
    public void generatePayslip() {
        System.out.println("\n------ PAYSLIP (Contract Employee) ------");
        System.out.println("Employee ID      : " + empId);
        System.out.println("Name             : " + name);
        System.out.println("Basic Pay        : " + basicSalary);
        System.out.println("Contract Duration: " + contractDuration + " months");
        System.out.println("Tax (5%)         : " + calculateTax());
        System.out.println("Net Salary       : " + calculateNetSalary());
        System.out.println("------------------------------------------\n");
    }
}
