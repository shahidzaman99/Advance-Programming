/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment2.payrollsystem;


public abstract class Employee {
    protected int empId;
    protected String name;
    protected double basicSalary;

    public Employee(int empId, String name, double basicSalary) {
        this.empId = empId;
        this.name = name;
        this.basicSalary = basicSalary;
    }

    public int getEmpId() { return empId; }
    public String getName() { return name; }
    public double getBasicSalary() { return basicSalary; }

    public void setEmpId(int empId) { this.empId = empId;}
    public void setName(String name) { this.name = name; }
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }

    public abstract double calculateTax();
}

