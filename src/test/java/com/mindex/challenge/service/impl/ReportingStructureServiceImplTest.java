package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingStructureUrl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**Employee with direct reports */
    private Employee testEmployee;
    private static final String TEST_EMPLOYEE_ID = "16a596ae-edd3-4847-99fe-c4518e82c86f";
    private static final int TEST_EMPLOYEE_NUMBER_OF_REPORTS = 4;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";
        testEmployee =  employeeRepository.findByEmployeeId(TEST_EMPLOYEE_ID);
    }

    /**
     * Similar to the compensation tests there could be many more tests here. The
     * prime example I can think of right now is a test of the cycle detection.
     * 
     * The easiest way to implement that would be using the PUT for Employee to
     * modify the employee at TEST_EMPLOYEE_ID to have a direct report of 
     * themselves. Cycle created and you check for the error response from the server.
     */

    @Test
    public void testRead() {
        ReportingStructure createdReportingStructure = new ReportingStructure(testEmployee, TEST_EMPLOYEE_NUMBER_OF_REPORTS);

        // Read checks
        ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, testEmployee.getEmployeeId()).getBody();
        assertReportingStructureEquivalence(createdReportingStructure, readReportingStructure);
    }

    // Overriding equals for ReportingStructure would remove the need for this
    // but I'm choosing to maintain the pattern already present in the tests
    private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
        Employee expectedEmployee = expected.getEmployee();
        Employee actuaEmployee = actual.getEmployee();

        // This violates DRY and given more time *should* instead be handled by both 
        // Employee and ReportingStructure overriding equals for direct comparisons
        assertEquals(expectedEmployee.getFirstName(), actuaEmployee.getFirstName());
        assertEquals(expectedEmployee.getLastName(), actuaEmployee.getLastName());
        assertEquals(expectedEmployee.getDepartment(), actuaEmployee.getDepartment());
        assertEquals(expectedEmployee.getPosition(), actuaEmployee.getPosition());

        assertEquals(expected.getnumberofReports(), actual.getnumberofReports());
    }
}
