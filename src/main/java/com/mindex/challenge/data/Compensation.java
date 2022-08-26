package com.mindex.challenge.data;

public class Compensation {
    private String employeeId;
    private double salary;
    private String effectiveDate;

    public Compensation() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
