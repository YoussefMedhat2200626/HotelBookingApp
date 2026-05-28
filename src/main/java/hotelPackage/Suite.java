package hotelPackage;

public class Suite extends Room implements displayable {
    private boolean hasJacuzzi;

    public Suite(int roomNumber, double pricePerNight, boolean hasJacuzzi) {
        super(roomNumber, pricePerNight);
        this.hasJacuzzi = hasJacuzzi;
    }

    public boolean hasJacuzzi() {
        return hasJacuzzi;
    }

    @Override
    public void displayInfo() {
        System.out.println(this);
    }

    @Override
    public String getRoomType() {
        return "Suite";
    }

    @Override
    public double calculateCost(int nights) {
        return getPricePerNight() * nights;
    }

    @Override
    public String toString() {
        return "Suite{" +
                "roomNumber=" + getRoomNumber() +
                ", pricePerNight=" + getPricePerNight() +
                ", hasJacuzzi=" + hasJacuzzi +
                ", isAvailable=" + isAvailable() +
                '}';
    }
}
