package com.room.reservation.program.repository;

import com.room.reservation.program.constants.FileConstants;
import com.room.reservation.program.mapper.EmployeeMapper;
import com.room.reservation.program.model.Employee;
import com.room.reservation.program.model.Room;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeRepository {

    public List<Employee> findAll() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(FileConstants.EMPLOYEE_LIST_FILE)));
        List<Employee> employees = new ArrayList<>();

        String line="";
        while( (line = reader.readLine()) != null ) {
            employees.add( EmployeeMapper.mapFromText(line) );
        }

        reader.close();

        return employees;
    }

    public Employee getEmployee( final Employee potentialEmployee ) throws IOException {
        List<Employee> employees = findAll();

        for( Employee employee : employees ) {
            if( potentialEmployee.equals(employee) ) return employee;
        }

        return null;
    }

    public Employee getEmployee( final Long employeeNumber ) throws IOException {
        List<Employee> employees = findAll();

        for( Employee employee : employees ) {
            if( employeeNumber.compareTo( employee.getEmployeeNumber() ) == 0 ) return employee;
        }

        return null;
    }

    public void save(Employee employee) throws IOException {
        List<Employee> currentEmployees = findAll();
        BufferedWriter writer = new BufferedWriter( new FileWriter( new File( FileConstants.EMPLOYEE_LIST_FILE ) ) );


        for( Employee employee1 : currentEmployees ) {
            writer.write( employee1.toString() );
            writer.newLine();
        }

        writer.write( employee.toString() );
        writer.newLine();

        writer.close();

    }

    public void delete(Employee employee) throws IOException {
        List<Employee> remainingEmployees = Files.lines(Paths.get(FileConstants.EMPLOYEE_LIST_FILE)).map(EmployeeMapper::mapFromText)
                .filter( e -> e.getEmployeeNumber().longValue() != employee.getEmployeeNumber() ).collect(Collectors.toList());
        remainingEmployees.forEach(System.out::println);
        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(FileConstants.EMPLOYEE_LIST_FILE)));
        remainingEmployees.forEach( e -> {
            try {
                bufferedWriter.write(e.toString());
                bufferedWriter.newLine();
            } catch (IOException ioException) {
                System.err.println("An unexpected error occurred.");
            }

        } );
        bufferedWriter.close();
    }
}
