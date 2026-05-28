package hotelPackage;

public class doubleRoom extends Room implements displayable {

    public doubleRoom(int roomNumber, double pricePerNight) {
        super(roomNumber, pricePerNight);
    }

    @Override
    public void displayInfo() {
        System.out.println(this);
    }

    @Override
    public String getRoomType() {
        return "Double Room";
    }

    @Override
    public double calculateCost(int nights) {
        return getPricePerNight() * nights;
    }

    @Override
    public String toString() {
        return "DoubleRoom{" +
                "roomNumber=" + getRoomNumber() +
                ", pricePerNight=" + getPricePerNight() +
                ", isAvailable=" + isAvailable() +
                '}';
    }
}
