package BookingSystem;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Payment {
    private Booking booking;
    private double amountPaid;


    public Payment(Booking booking) {
        this.booking = booking;
    }

    public String isPaymentComplete() {
        return booking.getPaymentStatus();
    }


    public double getAmountPaid() {
        return booking.totalPrice();
    }

    public String generateReceipt() {

        return booking.getBookingDetails() +
                String.format("\nAmount Paid: RM %.2f\nPayment Status: %s\n",
                        getAmountPaid(),
                        isPaymentComplete());
    }


    public boolean writeBookingIntoFile(Student student, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename + student.getStudentId() + ".txt", true))) {
            bw.newLine();
            bw.write("---------------------------------------");
            bw.newLine();
            bw.write("Booking Details:");
            bw.newLine();
            bw.write(booking.getBookingDetails());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

