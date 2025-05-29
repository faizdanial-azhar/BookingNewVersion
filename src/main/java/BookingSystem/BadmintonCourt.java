package BookingSystem;

public class BadmintonCourt extends Facility {//amirin

    public BadmintonCourt() {
        super("Badminton Court", 15.0);
    }

    @Override
    public String getFacilityDetails() {
        return "Badminton Court"+" ("+getRatePerHour()+" per hour)";
    }
}
