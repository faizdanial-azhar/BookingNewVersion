package BookingSystem;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Facility {//amirin
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
        // Check if inputs are empty or not numeric
        if (startTime == null || startTime.isEmpty() || endTime == null || endTime.isEmpty()) {
            // Handle empty input - don't change duration
            return;
        }

        try {
            int start = Integer.parseInt(startTime);
            int end = Integer.parseInt(endTime);

            // Only allow times between 8:00 (8am) and 00:00 (midnight)
            if ((start >= 8 && start <= 24) && (end >= 0 && end <= 24)) {
                if (end == 0) {
                    end = 24; //
                }

                if (end <= start) {
                    this.duration = 0;
                } else {
                    this.duration = end - start;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

}
