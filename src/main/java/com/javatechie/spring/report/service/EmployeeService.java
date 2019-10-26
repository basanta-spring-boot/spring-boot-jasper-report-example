package com.javatechie.spring.report.service;

import com.javatechie.spring.report.entity.Employee;
import com.javatechie.spring.report.repository.EmployeeRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @PostConstruct
    public void init() {
        List<Employee> employees = Stream.of(
                new Employee(101, "Basant", "SE", 500000, "08/08/2015"),
                new Employee(102, "Santosh", "SSE", 800000, "14/04/2012"),
                new Employee(103, "Arun", "Architect", 1500000, "02/11/2008"),
                new Employee(104, "Kousik", "Manager", 2800000, "18/07/2011"),
                new Employee(105, "Swat", "Team Lead", 2100000, "12/11/2010"),
                new Employee(106, "Amit", "SSE", 1200000, "17/03/2012"),
                new Employee(107, "Charan", "Module Lead", 500000, "21/10/2018"),
                new Employee(108, "Krupa", "SSE", 2000000, "09/04/2015"),
                new Employee(109, "Anurag", "SSE", 900000, "14/12/2017"),
                new Employee(110, "Dileep", "SE", 100000, "15/05/2015"))
                .collect(Collectors.toList());
        repository.saveAll(employees);
    }


    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        List<Employee> employees = repository.findAll();
        String reportPath = "C:\\Users\\basan\\Desktop\\Report";

        //compile Jasper Report
        File file = ResourceUtils.getFile("classpath:employees.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        //Get your data source
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);

        //Add Parameters
        Map<String, Object> param = new HashMap<>();
        param.put("createdBy", "Java Techie");

        //Fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, param, dataSource);

        //Export Report
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "\\employees.pdf");
        }
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, reportPath + "\\employees.html");
        }
        return "Report Successfully Generated @path : " + reportPath;
    }
}
