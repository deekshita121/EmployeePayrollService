package com.capgemini.employeepayservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeePayrollFileIOService {
	public static String PAYROLL_FILE_NAME = "payroll-file.txt";

	public void writeData(List<EmployeePayrollData> employeePayrollList) {
		StringBuffer empBuffer = new StringBuffer();
		employeePayrollList.forEach(employee -> {
			String employeeDataString = employee.toString().concat("\n");
			empBuffer.append(employeeDataString);
		});

		try {
			Files.write(Paths.get(PAYROLL_FILE_NAME), empBuffer.toString().getBytes());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	public long countEntries(List<EmployeePayrollData> employeePayrollList) {
		long entries = 0;
		try {
			entries = Files.lines(new File(PAYROLL_FILE_NAME).toPath()).count();
		}
		catch(IOException e) {
	}
		return entries;
	}
	
	public void printEntries() {
		try {
			Files.lines(new File(PAYROLL_FILE_NAME).toPath()).forEach(System.out::println);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<EmployeePayrollData> readEntries(){
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		try {
			employeePayrollList =  Files.lines(new File(PAYROLL_FILE_NAME).toPath())
					.map(line -> line.trim())
					.map(line -> {
						String[] payrollArray = line.split(", ");
						return new EmployeePayrollData(Integer.parseInt(payrollArray[0]),
								payrollArray[1], Double.parseDouble(payrollArray[2]));}
							).collect(Collectors.toList());
		}			
		catch(IOException e) {
			e.printStackTrace();
		}
		return employeePayrollList;
	}
	
}