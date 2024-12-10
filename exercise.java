package Array;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class exercise {
    private Seat[][] seats;
    private ArrayList<Booking> bookingHistory;
    private Scanner scanner;

    private static class Seat {
        private String code;
        private String status;
        private Date bookingDate;

        public Seat(String code) {
            this.code = code;
            this.status = "AV";
        }

        public String getCode() { return code; }
        public String getStatus() { return status; }
        public Date getBookingDate() { return bookingDate; }

        public void book() {
            this.status = "BO";
            this.bookingDate = new Date();
        }

        public void cancel() {
            this.status = "AV";
            this.bookingDate = null;
        }
    }

    private static class Booking {
        private String seatCode;
        private Date bookingDate;

        public Booking(String seatCode, Date bookingDate) {
            this.seatCode = seatCode;
            this.bookingDate = bookingDate;
        }

        public String getSeatCode() { return seatCode; }
        public Date getBookingDate() { return bookingDate; }
    }

    public exercise() {
        this.bookingHistory = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void setupHall() {
        System.out.print("Enter number of rows: ");
        int rows = scanner.nextInt();
        System.out.print("Enter number of columns: ");
        int cols = scanner.nextInt();

        seats = new Seat[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                seats[i][j] = new Seat(String.format("%c%d", (char)('A' + i), j + 1));
            }
        }
        System.out.println("\nCinema hall setup complete!");
        System.out.println("Total seats: " + (rows * cols));
    }

    public void displaySeats() {
        System.out.println("\nCurrent Seat Status:");
        System.out.print("   ");
        for (int j = 0; j < seats[0].length; j++) {
            System.out.printf("%5d", j + 1);
        }
        System.out.println("\n   " + "-----".repeat(seats[0].length));

        for (int i = 0; i < seats.length; i++) {
            System.out.print((char)('A' + i) + " |");
            for (int j = 0; j < seats[i].length; j++) {
                System.out.printf("%5s", seats[i][j].getStatus());
            }
            System.out.println();
        }
    }

    public void bookSeat() {
        System.out.print("\nEnter seat code (e.g., A1): ");
        String code = scanner.next().toUpperCase();

        int row = code.charAt(0) - 'A';
        int col = Integer.parseInt(code.substring(1)) - 1;

        if (row < 0 || row >= seats.length || col < 0 || col >= seats[0].length) {
            System.out.println("Invalid seat code!");
            return;
        }

        Seat seat = seats[row][col];
        if (seat.getStatus().equals("BO")) {
            System.out.println("Seat already booked!");
            return;
        }

        seat.book();
        bookingHistory.add(new Booking(seat.getCode(), seat.getBookingDate()));
        System.out.println("Seat booked successfully!");
    }

    public void cancelBooking() {
        System.out.print("\nEnter seat code to cancel (e.g., A1): ");
        String code = scanner.next().toUpperCase();

        int row = code.charAt(0) - 'A';
        int col = Integer.parseInt(code.substring(1)) - 1;

        if (row < 0 || row >= seats.length || col < 0 || col >= seats[0].length) {
            System.out.println("Invalid seat code!");
            return;
        }

        Seat seat = seats[row][col];
        if (seat.getStatus().equals("AV")) {
            System.out.println("Seat is not booked!");
            return;
        }

        seat.cancel();
        System.out.println("Booking cancelled successfully!");
    }

    public void viewBookingHistory() {
        if (bookingHistory.isEmpty()) {
            System.out.println("\nNo booking history available.");
            return;
        }

        System.out.println("\nBooking History:");
        System.out.println("Seat Code | Booking Date");
        System.out.println("-".repeat(40));

        for (Booking booking : bookingHistory) {
            System.out.printf("%-9s | %s%n",
                    booking.getSeatCode(),
                    booking.getBookingDate().toString());
        }
        System.out.println("\nTotal bookings: " + bookingHistory.size());
    }

    public void run() {
        setupHall();
        int choice;

        do {
            System.out.println("\n=== Cinema Hall Booking System ===");
            System.out.println("1. View Seats");
            System.out.println("2. Book Seat");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Booking History");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    displaySeats();
                    break;
                case 2:
                    bookSeat();
                    break;
                case 3:
                    cancelBooking();
                    break;
                case 4:
                    viewBookingHistory();
                    break;
                case 5:
                    System.out.println("Thank you for using Cinema Hall Booking System!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);
        scanner.close();
    }
    public static void main(String[] args) {
        new exercise().run();
    }
}