package com.mindex.challenge.data;

public class Compensation {
    private Employee employee;
    private double salary;
    private String effectiveDate;

    public Compensation() {
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public double getSalary() {
        return this.salary;
    }

    public String getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
