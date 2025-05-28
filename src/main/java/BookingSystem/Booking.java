package BookingSystem;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Booking {

    private Student student;
    private Facility facility;
    private LocalDateTime dateTime;
    private int duration;
    private int totalPrice;
    private String startTime;
    private String endTime;
    String paymentMethod;
    String paymentStatus;


    public Booking(Student student, Facility facility, LocalDate value, String startTime, String endTime, int duration, String paymentMethod,boolean isPaymentStatus) {
        this.paymentMethod = paymentMethod;
        this.student = student;
        this.facility = facility;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;

        if(isPaymentStatus) this.paymentStatus = isPaymentStatus ? "Completed" : "Incomplete";


    }

    public double totalPrice() {
        return facility.getRatePerHour() * duration;
    }

    public String getBookingDetails() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String studentName = (student != null) ? student.getName() : "Unknown";
        return String.format("Student: %s (%s)\nFacility: %s\nDate & Time: %s\nDuration: %d hour(s)\nTotal Cost: RM %.2f\nPayment Method: %s\nPayment Status: %s",
                studentName,
                student.getStudentId(),
                facility.getFacilityName(),
                startTime + " - " + endTime,
                duration,
                totalPrice(),
                paymentMethod,paymentStatus);
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Student getStudent() {
        return student;
    }
    public Facility getFacility() {
        return facility;
    }
    public int getDuration() {
        return duration;
    }



}

