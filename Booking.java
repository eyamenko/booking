/**
 * Created by Eugene Yamenko (s0247060) on 18/04/2014.
 */
public final class Booking {
    private int ID;
    private String fullName;
    private double flightCost;
    private double accommodationCost;
    private double mealCost;
    private double totalCost;

    public Booking(int ID, String fullName, double flightCost, double accommodationCost, double mealCost,
            double totalCost) {
        setID(ID);
        setFullName(fullName);
        setFlightCost(flightCost);
        setAccommodationCost(accommodationCost);
        setMealCost(mealCost);
        setTotalCost(totalCost);
    }

    /**
     * Checks whether the full name is correct.
     *
     * @param fullName a full name (1 <= fullName length <= 15)
     * @return true if full name is correct and false otherwise
     */
    public static boolean isNameCorrect(String fullName) {
        return fullName != null && fullName.length() >= 1 && fullName.length() <= 15; // 1 <= fullName <= 15
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        if (!isNameCorrect(fullName)) { // if fullName is not correct
            throw new IllegalArgumentException("Incorrect full name");
        }

        this.fullName = fullName;
    }

    public double getFlightCost() {
        return flightCost;
    }

    public void setFlightCost(double flightCost) {
        this.flightCost = flightCost;
    }

    public double getAccommodationCost() {
        return accommodationCost;
    }

    public void setAccommodationCost(double accommodationCost) {
        this.accommodationCost = accommodationCost;
    }

    public double getMealCost() {
        return mealCost;
    }

    public void setMealCost(double mealCost) {
        this.mealCost = mealCost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}