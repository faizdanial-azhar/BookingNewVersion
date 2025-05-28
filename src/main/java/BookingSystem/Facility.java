package BookingSystem;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Facility {
    private String facilityName;
    private double ratePerHour;

    // Booking-related attributes inside Facility
    private Student student;
    private LocalDateTime dateTime;
    private int duration;

    public Facility(String facilityName, double ratePerHour) {
        this.facilityName = facilityName;
        this.ratePerHour = ratePerHour;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    // Book the facility


    // Get booking details
    public String getBookingDetails() {
        if (student == null || dateTime == null || duration == 0) {
            return "No booking has been made yet.";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return String.format("Student: %s (%s)\nFacility: %s\nDate & Time: %s\nDuration: %d hour(s)\nTotal Cost: RM %.2f",
                student.getName(),
                student.getStudentId(),
                facilityName,
                dateTime.format(formatter),
                duration,
                getTotalCost());
    }

    public double getTotalCost() {
        return ratePerHour * duration;
    }

    public abstract String getFacilityDetails();
    public LocalDateTime getBookingDateTime() {
        return dateTime;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(String startTime, String endTime) {
        if( Integer.parseInt(endTime)<=24 && Integer.parseInt(startTime)<=24&& Integer.parseInt(endTime)>=0&& Integer.parseInt(startTime)>=0) {
            if(Integer.parseInt(endTime)<=Integer.parseInt(startTime)) {
                this.duration = 24 - Integer.parseInt(startTime) + Integer.parseInt(endTime);
            }else {
                this.duration = Integer.parseInt(endTime) - Integer.parseInt(startTime);
            }
        }
    }

}
