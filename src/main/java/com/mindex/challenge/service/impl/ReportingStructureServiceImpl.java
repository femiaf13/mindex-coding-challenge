package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;

    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Reading reportingStructure for employee ID: [{}]", employeeId);

        Employee requestedEmployee = employeeService.read(employeeId);

        ArrayList<String> countedEmployees = new ArrayList<String>();
        Stack<String> employeesToCount = new Stack<String>();
        // Start with the employee that's iniatitng this count
        employeesToCount.push(employeeId);

        /**
         * Going with a DFS search here instead of a recursive solution. Recursion
         * could work but in general it's dangerous when cycles are possible.
         */

        while (!employeesToCount.isEmpty()) {
            String currentEmpoyeeId = employeesToCount.pop();
            if (countedEmployees.contains(currentEmpoyeeId)) {
                throw new RuntimeException("Encountered a cycle in reporting structre of: " + employeeId);
            }
            countedEmployees.add(currentEmpoyeeId);

            Employee currentEmployee = employeeService.read(currentEmpoyeeId);
            List<Employee> currentEmployeeReports = currentEmployee.getDirectReports();
            // Don't try to read reports for employees without any
            if (currentEmployeeReports != null) {
                for (Employee report : currentEmployeeReports) {
                    employeesToCount.push(report.getEmployeeId());
                } 
            }
        }
        // size-1 because we also count the initiating employee
        ReportingStructure reportingStructure = new ReportingStructure(requestedEmployee, countedEmployees.size()-1);
        return reportingStructure;
    }
    
}
