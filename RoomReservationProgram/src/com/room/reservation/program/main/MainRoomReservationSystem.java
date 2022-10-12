package com.room.reservation.program.main;

import com.room.reservation.program.exception.AdminNotFoundException;
import com.room.reservation.program.exception.EmployeeNotFoundException;
import com.room.reservation.program.model.Admin;
import com.room.reservation.program.model.Employee;
import com.room.reservation.program.repository.AdminRepository;
import com.room.reservation.program.repository.EmployeeRepository;
import com.room.reservation.program.service.AdminService;
import com.room.reservation.program.service.AdminServiceImpl;
import com.room.reservation.program.service.EmployeeService;
import com.room.reservation.program.service.EmployeeServiceImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class MainRoomReservationSystem {

    private static final String MAIN_MENU = "=========================\n|    \t Are you\t|\n| [1] Customer\t\t|\n| [2] Employee\t\t|\n| [3] Admin\t\t\t|\n" +
                                       "| [0] Exit\t\t\t|\n=========================\n" ;

    private AdminService adminService = new AdminServiceImpl();

    private EmployeeService employeeService = new EmployeeServiceImpl();

    private EmployeeRepository employeeRepository = new EmployeeRepository();

    private AdminRepository adminRepository = new AdminRepository();


    public static void main(String[] args) {
        new MainRoomReservationSystem().run();
    }

    private  void run() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        RoomReservationSystem roomReservationSystem;
        AdministrationSystem administrationSystem = new AdministrationSystem();

        do {
            System.out.println(MAIN_MENU);
            System.out.print("Enter choice: ");
            int opt = scanner.nextInt();
            switch(opt) {
                case 1:
                    roomReservationSystem = new RoomReservationSystem(true);
                    try {
                        roomReservationSystem.run();
                    } catch (IOException e) {
                        System.err.println("An unexpected error occurred.");
                    }
                    break;
                case 2:
                    roomReservationSystem = new RoomReservationSystem(false);
                    Scanner sc1 = new Scanner(System.in);
                    System.out.println("=================================\n" +
                                       "|Enter your ID Number:          |\n" +
                                       "=================================");
                    String idNumber = sc1.nextLine();
                    System.out.println("=================================\n" +
                                       "|Enter your password:           |\n" +
                                       "=================================");
                    String password1 = sc1.nextLine();

                    try {
                        if( employeeService.isEmployeeExisting( Long.parseLong(idNumber), password1 ) ) {
                            roomReservationSystem.setNameOfEmployee( employeeRepository.getEmployee(Long.parseLong(idNumber)).getEmployeeFullName() );
                            roomReservationSystem.run();
                        }else {
                            System.out.println("=====================================\n" +
                                               "|ID Number or Password is not found |  \n" +
                                               "=====================================");
                        }
                    } catch (IOException e) {
                        System.err.println("An unexpected error occurred.");
                    }
                    break;
                case 3:
                    Scanner sc = new Scanner(System.in);
                    System.out.println("=================================\n" +
                            "|Enter your Admin Code:         |\n" +
                            "=================================");
                    String adminCode = sc.nextLine();
                    System.out.println("=================================\n" +
                            "|Enter your password:           |\n" +
                            "=================================");
                    String password = sc.nextLine();

                    try {
                        if( adminService.isExisting( adminCode, password ) ) {
                            Admin admin = adminRepository.getAdmin();
                            administrationSystem.setAdminName(admin.getName());
                            administrationSystem.run();
                        }else {
                             System.out.println("=====================================\n" +
                                                "|Admin Code or Password is not found|  \n" +
                                     "=====================================");
                        }
                    } catch (IOException e) {
                        System.err.println("An unexpected error occurred.");
                    } catch (AdminNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 0:
                    isRunning = false;
                    break;
                    // create an exc
                default:
                    System.out.println("Invalid option");
                    
            }
        }while( isRunning );
    }





}
