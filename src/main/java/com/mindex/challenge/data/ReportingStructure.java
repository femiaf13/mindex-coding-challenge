package com.mindex.challenge.data;

public class ReportingStructure {
    private Employee employee;
    private int numberofReports;

    public ReportingStructure(Employee employee, int numberofReports) {
        this.employee = employee;
        this.numberofReports = numberofReports;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public int getnumberofReports() {
        return this.numberofReports;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setnumberofReports(int numberofReports) {
        this.numberofReports = numberofReports;
    }
}
