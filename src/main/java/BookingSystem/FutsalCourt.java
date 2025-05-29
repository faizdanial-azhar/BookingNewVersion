package BookingSystem;

public class FutsalCourt extends Facility {//amirin
private double ratePerHourFutsal;
    public FutsalCourt() {
        super("Futsal Court", 30.0);
        this.ratePerHourFutsal = 30.0;
    }

    @Override
    public String getFacilityDetails() {
        return "Futsal"+" ("+getRatePerHour()+" per hour)";
    }

}

