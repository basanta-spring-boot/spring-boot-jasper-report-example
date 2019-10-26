package com.javatechie.spring.report;

import com.javatechie.spring.report.entity.Employee;
import com.javatechie.spring.report.repository.EmployeeRepository;
import com.javatechie.spring.report.service.EmployeeService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@SpringBootApplication
@RestController
public class SpringJasperReportApplication {
    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private EmployeeService service;

    @GetMapping("/")
    public List<Employee> findAllEmployees() {
        return repository.findAll();
    }

    @GetMapping("/report/{format}")
    public String generateReport(@PathVariable String format)
			throws FileNotFoundException, JRException {
        return service.exportReport(format);
    }

    public static void main(String[] args) {
    	SpringApplication.run(SpringJasperReportApplication.class, args);
    }

}
