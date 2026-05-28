package hotelPackage;

public abstract class Room implements Comparable<Room> {
    private int roomNumber;
    private double pricePerNight;
    private boolean isAvailable;

    public Room(int roomNumber, double pricePerNight) {
        try {
            if (roomNumber <= 0 ) {
                throw new IllegalArgumentException();
            }if (pricePerNight <= 0){
                throw new IllegalStateException();
            }
            this.roomNumber = roomNumber;
            this.pricePerNight = pricePerNight;
            this.isAvailable = true;
        }catch(IllegalArgumentException e ){
            System.out.println("Room number must be positive");
        }catch (IllegalStateException e){
            System.out.println("Price per night must be positive");
        }
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }


    public boolean book() {
        if (isAvailable) {
            isAvailable = false;
            return true;
        }
        return false;
    }


    public void release() {
        try {
            if (isAvailable){
                throw new IllegalStateException();
            }
            isAvailable = true;
        } catch (IllegalStateException e) {
            System.out.println("Room already available.");
        }
    }

    public abstract String getRoomType();


    public abstract double calculateCost(int nights);

    @Override
    public int compareTo(Room other) {
        return Double.compare(this.pricePerNight, other.pricePerNight);
    }

    @Override
    public abstract String toString();
}
