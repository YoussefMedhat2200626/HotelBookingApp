package hotelPackage;

public class singleRoom extends Room implements displayable {

    public singleRoom(int roomNumber, double pricePerNight) {
        super(roomNumber, pricePerNight);
    }

    @Override
    public void displayInfo() {
        System.out.println(this);
    }

    @Override
    public String getRoomType() {
        return "Single Room";
    }

    @Override
    public double calculateCost(int nights) {
        return getPricePerNight() * nights;
    }

    @Override
    public String toString() {
        return "SingleRoom{" +
                "roomNumber=" + getRoomNumber() +
                ", pricePerNight=" + getPricePerNight() +
                ", isAvailable=" + isAvailable() +
                '}';
    }
}
