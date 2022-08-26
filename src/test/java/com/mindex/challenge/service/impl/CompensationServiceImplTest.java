package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // This is for creating a compensation
    private Employee testEmployee;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
        testEmployee =  employeeRepository.findByEmployeeId("62c1084e-6e34-4630-93fd-9153afb65309");
    }

    /**
     * There should be more tests here. Currently this only tests the 
     * "happy path" which is far from everything that should be tested
     * 
     * Examples: 
     * POSTing a compensation for an employee twice
     * GETing a compensation that hasn't been created
     * GETing a compensation for an employee that doesn't exist
     * 
     * In an effort to not spend *too* much time on this I will leave this note 
     * instead of implementing
     */

    @Test
    public void testCreateRead() {
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(testEmployee);
        testCompensation.setSalary(10000000);
        testCompensation.setEffectiveDate("1/1/2020");

        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        assertNotNull(createdCompensation.getEmployee());
        assertCompensationEquivalence(testCompensation, createdCompensation);


        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId()).getBody();
        assertCompensationEquivalence(createdCompensation, readCompensation);
    }

    // Overriding equals for Compensation would remove the need for this
    // but I'm choosing to maintain the pattern already present in the tests
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        Employee expectedEmployee = expected.getEmployee();
        Employee actuaEmployee = actual.getEmployee();

        // This violates DRY and given more time *should* instead be handled by both 
        // Employee and Compensation overriding equals for direct comparisons
        assertEquals(expectedEmployee.getFirstName(), actuaEmployee.getFirstName());
        assertEquals(expectedEmployee.getLastName(), actuaEmployee.getLastName());
        assertEquals(expectedEmployee.getDepartment(), actuaEmployee.getDepartment());
        assertEquals(expectedEmployee.getPosition(), actuaEmployee.getPosition());

        // For doubles you need an acceptable delta I guess?
        assertEquals(expected.getSalary(), actual.getSalary(), 0);
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
