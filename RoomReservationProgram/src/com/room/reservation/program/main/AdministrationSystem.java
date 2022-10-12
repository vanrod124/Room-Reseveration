package com.room.reservation.program.main;

import com.room.reservation.program.exception.*;
import com.room.reservation.program.model.Employee;
import com.room.reservation.program.model.JuniorRoom;
import com.room.reservation.program.model.Room;
import com.room.reservation.program.repository.EmployeeRepository;
import com.room.reservation.program.service.*;
import com.room.reservation.program.enums.RoomType;
import com.room.reservation.program.utils.StringUtils;
import com.room.reservation.program.utils.SystemUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.room.reservation.program.utils.SystemUtils.getIntegerInput;

public class AdministrationSystem {

    private static final String CUSTOMER_MENU = "=================================== \n" +
            "| Welcome to the Hotel California |\n" +
            "|   What would you like to do     |\n" +
            "| [1] Reserve Room                |\n" +
            "| [2] Check Availability          |\n" +
            "| [3] Cancel Reservation          |\n" +
            "| [0] Exit                        |\n" +
            "===================================\n";

    private String ADMIN_MENU = "===================================\n" +
                                "| Welcome $owner                  | \n" +
                                "| What would you like to do       |\n" +
                                "| [1] Hire Employee               |\n" +
                                "| [2] Fire Employee               |\n" +
                                "| [3] Add Room                    |\n" +
                                "| [4] Subtract Room               |\n" +
                                "| [5] Check Employee List         |\n" +
                                "| [6] Check Room List             |\n" +
                                "===================================\n";

    private final RoomReservationService roomReservationService;

    private final EmployeeService employeeService;

    private final AdminService adminService;

    private final RoomService roomService;

    private final EmployeeRepository employeeRepository;

    private Scanner sc;

    private String adminName;

    public static void main(String[] args) {
        try {
            new AdministrationSystem().run();

        } catch (IOException e) {
            System.err.println("An unexpected error occurred.");
        }
    }


    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public AdministrationSystem(  ) {
        sc = new Scanner(System.in);
        this.roomReservationService = new RoomReservationServiceImpl();
        this.adminService = new AdminServiceImpl();
        employeeService = new EmployeeServiceImpl();
        roomService = new RoomServiceImpl();
        employeeRepository = new EmployeeRepository();
    }

    public void run() throws IOException {
        boolean isRunning = true;
        do {
            System.out.println(ADMIN_MENU.replace("$owner", this.adminName));
            System.out.print("Enter choice: ");
            int opt = sc.nextInt();
            switch(opt) {
                case 1:
                    hireEmployee();
                    break;
                case 2:
                    fireEmployee();
                    break;
                case 3:
                    addRoom();
                    break;
                case 4:
                    try {
                        decommissionRoom();
                    } catch (NoRoomLeftToDecommissionException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    checkEmployeeList();
                    break;
                case 6:
                    checkRoomList();
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.err.println("Please select from 0-6 only.");
                    break;
            }

//            SystemUtils.clearScreen();
        }while( isRunning );
    }

    public void hireEmployee() {
        System.out.println("=================================\n" +
                           "|Input Employee Full Name:      |\n" +
                           "=================================");
        Scanner sc = new Scanner(System.in);
        String fullName = sc.nextLine();
        String password = SystemUtils.generatePassword();
        String idNumber = SystemUtils.generateGUID();

        employeeService.createEmployee( new Employee(Long.parseLong(idNumber), fullName, password) );

        String message = "=================================\n" +
                "|Employee ID Number is: $idNumber|\n" +
                "|Password: $password             |\n" +
                "=================================\n";

        System.out.println( message.replace("$idNumber", idNumber).replace("$password", password) );

    }

    public void fireEmployee(){
        System.out.println("=================================\n" +
                           "|Input Employee ID Number: |\n" +
                            "=================================");
        Scanner sc = new Scanner(System.in);
        String employeeNumber = sc.nextLine();

        System.out.println("=================================================\n" +
                           "|Are you sure you want to delete his data?(Y/N) |\n" +
                           "=================================================");

        if( "Y".equalsIgnoreCase(sc.nextLine().trim()) ) {
            try {
                employeeService.deleteEmployee( new Employee(Long.parseLong(employeeNumber.trim())) );

                System.out.println("=================================\n" +
                        "|Employee fired    |\n" +
                        "=================================");
            } catch (IOException e) {
                System.err.println("An unexpected error occurred.");
            } catch (EmployeeNotFoundException e) {
                System.out.println("=================================\n" +
                                   "|Employee ID number not found    |\n" +
                                    "=================================");
            }
        }else {
            System.out.println("=================================\n" +
                               "|Firing cancelled               |\n" +
                               "=================================");
        }

    }

    public void addRoom() throws IOException {
        System.out.println("=================================\n" +
                           "|Which Room will be added?       |\n" +
                           "|[1] Normal Room                 |\n" +
                           "|[2] Junior Room                 |\n" +
                           "|[3]Queen Room                   |\n" +
                           "|[4]King Room                    |\n" +
                           "|[0]Cancel                       |\n" +
                           "=================================\n");
        int choice = SystemUtils.getIntegerInput("Enter input: ");
        System.out.println("=================================\n" +
                           "|How many will be added?         |\n" +
                            "=================================");
        int toAdd = new Scanner(System.in).nextInt();
        String type = "Normal";

        switch(choice) {
            case 1:
                roomService.addRoom( RoomType.NORMAL, toAdd );
                break;
            case 2:
                roomService.addRoom( RoomType.JUNIOR, toAdd );
                type = StringUtils.toTitleCase(RoomType.JUNIOR.name());
                break;
            case 3:
                roomService.addRoom( RoomType.QUEEN, toAdd );
                type = StringUtils.toTitleCase(RoomType.QUEEN.name());
                break;
            case 4:
                roomService.addRoom( RoomType.KING, toAdd );
                type = StringUtils.toTitleCase(RoomType.KING.name());
                break;
            case 0:
                break;
            default:
                System.out.println("Enter only 0-4 options");
                break;
        }

        String result ="=================================\n" +
                "|$num $type Rooms is added          |\n" +
                "=================================";

        System.out.println( result.replace("$num", toAdd+"").replace("$type", type) );
    }

    public void decommissionRoom() throws IOException, NoRoomLeftToDecommissionException {
        System.out.println("=================================\n" +
                "|Which Room will be decommissioned?        |\n" +
                "|[1] Normal Room                           |\n" +
                "|[2] Junior Room                           |\n" +
                "|[3] Queen Room                            |\n" +
                "|[4] King Room                             |\n" +
                "|[0] Cancel                                |\n" +
                "============================================\n");
        int choice = SystemUtils.getIntegerInput("Enter input: ");
        System.out.println("=================================\n" +
                "|How many will be decommissioned?          |\n" +
                "===========================================");
        int toRemove = new Scanner(System.in).nextInt();
        String type = "Normal";

        switch(choice) {
            case 1:
                roomService.decommissionRoom( RoomType.NORMAL, toRemove );
                break;
            case 2:
                roomService.decommissionRoom( RoomType.JUNIOR, toRemove );
                type = StringUtils.toTitleCase(RoomType.JUNIOR.name());
                break;
            case 3:
                roomService.decommissionRoom( RoomType.QUEEN, toRemove );
                type = StringUtils.toTitleCase(RoomType.QUEEN.name());
                break;
            case 4:
                roomService.decommissionRoom( RoomType.KING, toRemove );
                type = StringUtils.toTitleCase(RoomType.KING.name());
                break;
            case 0:
                break;
            default:
                System.out.println("Enter only 0-4 options");
                break;
        }

        String result ="=================================\n" +
                "|$num $type Rooms is decommissioned    |\n" +
                "===================================";

        System.out.println( result.replace("$num", toRemove+"").replace("$type", type) );

    }

    public void checkEmployeeList() throws IOException {
        System.out.println("=================================================");
        System.out.printf("|%-20s| %-15s | %-20s\n", "Name", "ID Number", "Password");
        List<Employee> employees = employeeRepository.findAll();

        for( Employee employee : employees ) {
            System.out.printf("|%-20s| %-15s | %-20s\n", employee.getEmployeeFullName(), employee.getEmployeeNumber(), employee.getEmployeePassword());
        }

        System.out.println("=================================================");
    }

    public void checkRoomList() throws IOException {
        Map<RoomType, Integer> roomListCountMap = roomService.countRoomsPerRoomType();

        System.out.println("=================================\n" +
                           "|Rooms         || Max Rooms      |\n"+
                           "|Normal Room   || "+roomListCountMap.get(RoomType.NORMAL)+"      |\n"+
                           "|Junior Room   || "+roomListCountMap.get(RoomType.JUNIOR)+"      |\n"+
                           "|Queen Room    || "+roomListCountMap.get(RoomType.QUEEN)+"      |\n"+
                           "|King Room     || "+roomListCountMap.get(RoomType.KING)+"      |\n"
        +"=================================\n");

    }







}
