package com.room.reservation.program.main;

import com.room.reservation.program.exception.ReservationNotFoundException;
import com.room.reservation.program.exception.UnAvailableRoomException;
import com.room.reservation.program.model.Room;
import com.room.reservation.program.service.RoomReservationService;
import com.room.reservation.program.service.RoomReservationServiceImpl;
import com.room.reservation.program.enums.RoomType;
import com.room.reservation.program.utils.StringUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static com.room.reservation.program.utils.SystemUtils.getIntegerInput;

public class RoomReservationSystem {

    private static final String CUSTOMER_MENU = "=================================== \n" +
            "| Welcome to the Hotel California |\n" +
            "|   What would you like to do     |\n" +
            "| [1] Reserve Room                |\n" +
            "| [2] Check Availability          |\n" +
            "| [3] Cancel Reservation          |\n" +
            "| [0] Exit                        |\n" +
            "===================================\n";

    private static final String EMPLOYEE_MENU = "=================================== \n" +
            "| Welcome $nameOfEmployee         |\n" +
            "|   What would you like to do     |\n" +
            "| [1] Reserve Room                |\n" +
            "| [2] Check Availability          |\n" +
            "| [3] Cancel Reservation          |\n" +
            "| [4] Check Reservation List      |\n"+
            "| [0] Exit                        |\n" +
            "===================================\n";

    private static final String RESERVE_MENU = "===================================\n" +
            "|  Where would $entity like to stay   |\n" +
            "| [1] Normal Room || PHP 500.00   |\n" +
            "| [2] Junior Room || PHP 1000.00  |\n" +
            "| [3] Queen Room  || PHP 5000.00  |  \n" +
            "| [4] King Room   || PHP 2000.00  |\n" +
            "| [0] Cancel                      |\n" +
            "===================================\n";

    private static final String ALL_ROOMS_FULL =
            "===========================================\n" +
            "| Sorry all of our rooms are already full |\n" +
            "===========================================\n";

    private final RoomReservationService roomReservationService;

    public static void main(String[] args) {
        RoomReservationSystem cms = new RoomReservationSystem(true);
        try {
            cms.run();

        } catch (IOException e) {
            System.err.println("An unexpected error occurred.");
        }
    }

    private Scanner sc;

    private boolean isCustomer;

    private String nameOfEmployee;

    public RoomReservationSystem(boolean isCustomer) {
        sc = new Scanner(System.in);
        roomReservationService = new RoomReservationServiceImpl();
        this.isCustomer = isCustomer;
    }

    public void setNameOfEmployee(String nameOfEmployee) {
        this.nameOfEmployee = nameOfEmployee;
    }

    public void run() throws IOException {
        boolean isRunning = true;
        do {
            System.out.println( isCustomer ? CUSTOMER_MENU : EMPLOYEE_MENU.replace("$nameOfEmployee", nameOfEmployee));
            System.out.print("Enter choice: ");
            int opt = sc.nextInt();
            switch(opt) {
                case 1:
                    reserveRoom();
                    break;
                case 2:
                    checkAvailability();
                    break;
                case 3:
                    cancelReservation();
                    break;
                case 4:
                    if( isCustomer ) {
                        System.err.println("Please select from 0-3 only.");
                        break;
                    }
                    checkReservationList();
                    break;
                case 0:
                    isRunning = false;
                    break;
                default:
                    System.err.println(isCustomer ? "Please select from 0-3 only." : "Please select from 0-4 only.");
                    break;
            }

//            SystemUtils.clearScreen();
        }while( isRunning );
    }

    public void reserveRoom() throws IOException {
        Scanner sc = new Scanner(System.in);

        if( roomReservationService.areAllRoomsFull() ) {
            System.out.println(ALL_ROOMS_FULL);
            return;
        }

        int choice = getIntegerInput(isCustomer ? RESERVE_MENU.replace("$entity", "you") :
                RESERVE_MENU.replace("$entity", "the customer"));

        switch(choice) {
            case 1:
                reserve(RoomType.NORMAL);
                break;
            case 2:
                reserve(RoomType.JUNIOR);
                break;
            case 3:
                reserve(RoomType.QUEEN);
                break;
            case 4:
                reserve(RoomType.KING);
                break;
            case 0:
                return;
            default:
                break;
        }
    }

    public void checkAvailability() throws IOException {
        System.out.println("=================================\n" +
                           "| Normal Room : "+isAvailable(RoomType.NORMAL)+"       |\n" +
                           "| Junior Room : "+isAvailable(RoomType.JUNIOR)+"       |\n" +
                           "| Queen Room  : "+isAvailable(RoomType.QUEEN)+"       | \n" +
                           "| King Room   : "+isAvailable(RoomType.KING)+"       |\n" +
                           "=================================\n");
    }

    public String isAvailable(RoomType roomType) throws IOException {
        return roomReservationService.isAvailable( roomType ) ? "Available" : "Full";
    }

    public void cancelReservation() {
        Scanner sc = new Scanner(System.in);

        String reservationNumberPrompt = "=======================================\n" +
                "|Please input $entity reservation number:|\n" +
                "=======================================";

        System.out.println( isCustomer ? reservationNumberPrompt.replace("$entity", "your") :
                reservationNumberPrompt.replace("$entity", "the customer's")  );
        String reservationNumber = sc.nextLine();

        String namePrompt = "=========================================\n" +
                "|Please input $entity registered Name:     |\n" +
                "=========================================";

        System.out.println( isCustomer ? namePrompt.replace("$entity", "your") :
                namePrompt.replace("$entity", "the customer's") );

        String registeredName = sc.nextLine();

        String ask = "==============================================================\n" +
                "|Would you like to continue cancelling $entity reservation?(Y/N)|\n" +
                "==============================================================";

        System.out.println( isCustomer ? ask.replace("$entity", "your") : ask.replace("$entity", "the customer's") );
        String doCancel = sc.nextLine();

        if( "y".equalsIgnoreCase(doCancel) ) {
            try {
                roomReservationService.cancelReservation( reservationNumber, registeredName );
                System.out.println("==================================\n" +
                                   "|Cancelling  Reservation Complete|\n" +
                                   "==================================\n");
            } catch (IOException e) {
                System.out.println("=========================\n" +
                                   "| Cancellation failed   |\n" +
                                   "=========================\n");
            } catch (ReservationNotFoundException e) {
                System.out.println("=======================\n" +
                                   "|"+ e.getMessage() +"|\n" +
                                   "=======================");
            }
        }else {
            System.out.println("=========================\n" +
                               "| Cancellation cancelled|\n" +
                               "=========================\n");
        }


    }

    private void reserve( RoomType roomType ) throws IOException {

        if( !roomReservationService.isAvailable(roomType) ) {
            printRoomFull(roomType);
            return;
        }

        if( isGoingToReserve( roomType ) ) {
            String entityName = "=================================================\n" +
                    "|Please Input $entity name:                        |\n" +
                    "=================================================";
            System.out.println(isCustomer ? entityName.replace("$entity", "your") : entityName.replace("$entity", "the customer's"));
            Scanner sc = new Scanner(System.in);
            String name = sc.nextLine();

            try {
                Room room = roomReservationService.reserveRoom( roomType, name.trim() );

                String complete = "=================================================\n" +
                        "|Reservation Complete, that would be PHP "+ getPrice(roomType) +" |\n" +
                        "|$e Reservation Number is:"+ room.getReservationNumber() +"              |\n" +
                        "=================================================\n";

                System.out.println(isCustomer ? complete.replace("$e", "Your") : complete.replace("$e", "the customer's"));

            } catch (UnAvailableRoomException e) {
                printRoomFull( roomType );
            }catch(IOException io) {
                System.out.println("====================\n" +
                                   "|Reservation Failed|\n" +
                                    "====================\n");

            }

        }
    }

    private boolean isGoingToReserve(RoomType roomType) {
        String prompt = "====================================================\n" +
                "|There is an available "+ StringUtils.toTitleCase(roomType.name()) +" room.      |\n" +
                "| Would $e like to continue reservation (y/n)?|\n" +
                "==============================================================\n";
        System.out.println(isCustomer ? prompt.replace("$e", "you") : prompt.replace("$e", "the customer"));
        System.out.print("Enter choice: ");
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        if( "Y".equalsIgnoreCase(choice) ) {
            return true;
        }

        return false;
    }

    private  void printRoomFull( RoomType roomType ) {
        System.out.println("==========================================\n" +
                           "|Sorry, "+ StringUtils.toTitleCase(roomType.name()) +" Room is already full      |\n" +
                           "==========================================\n");
    }

    private void checkReservationList() throws IOException {
        Map<String, List<Room>> reservationMap = roomReservationService.getReservationListByReservationNumber();
        System.out.println("=============================================================================================================");
        System.out.printf("|%-20s | %-12s | %-12s | %-12s | %-12s|\n", "Name", "Normal Room", "Junior Room", "Queen Room", "King Room");
        for( Map.Entry<String, List<Room>> entry : reservationMap.entrySet() ) {
            String custName = entry.getKey();

            System.out.printf("|%-20s | %-12d | %-12d | %-12d | %-12d|\n", custName,
                                getNumberOfRoomsByRoomType(entry.getValue(), RoomType.NORMAL) ,
                                getNumberOfRoomsByRoomType(entry.getValue(), RoomType.JUNIOR) ,
                                getNumberOfRoomsByRoomType(entry.getValue(), RoomType.QUEEN) ,
                                getNumberOfRoomsByRoomType(entry.getValue(), RoomType.KING)
                    );
        }
        System.out.println("=============================================================================================================");
    }

    public static String getPrice(RoomType roomType) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        switch ( roomType ) {
            case NORMAL:
                return decimalFormat.format( 500 );
            case JUNIOR:
                return decimalFormat.format(1000);
            case QUEEN:
                return decimalFormat.format(5000);
            default:
                return decimalFormat.format(2000);
        }

    }

    public int getNumberOfRoomsByRoomType( List<Room> rooms, RoomType roomType ) {
        int count = 0;
        for(Room room : rooms) {
            if( room.getRoomType().isEqualTo(roomType) ) count++;
        }

        return count;
    }
}
