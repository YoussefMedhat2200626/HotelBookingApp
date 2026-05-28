package hotelapp;

import hotelPackage.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HotelBookingApp extends Application {

    private TextField nameField, emailField, balanceField;
    private ListView<String> roomsListView;
    private ListView<String> bookedRoomsListView;  // New ListView for booked rooms
    private TextField roomNumberField, nightsField;
    private TextArea customerInfoArea;
    private Hotel hotel;
    private Customer currentCustomer;

    @Override
    public void start(Stage primaryStage) {
        hotel = new Hotel("Grand Hotel");
        hotel.addRoom(new singleRoom(101, 100));
        hotel.addRoom(new doubleRoom(102, 150));
        hotel.addRoom(new Suite(103, 300, true));

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // Customer creation area
        Label createCustLabel = new Label("Create Customer:");
        nameField = new TextField();
        nameField.setPromptText("Name");
        emailField = new TextField();
        emailField.setPromptText("Email");
        balanceField = new TextField();
        balanceField.setPromptText("Initial Balance");
        Button createCustomerBtn = new Button("Create Customer");
        createCustomerBtn.setOnAction(e -> createCustomer());

        HBox customerBox = new HBox(10, nameField, emailField, balanceField, createCustomerBtn);

        // Rooms list
        Label roomsLabel = new Label("Available Rooms:");
        roomsListView = new ListView<>();
        updateRoomList();

        // Add listener to auto-fill roomNumberField on room selection
        roomsListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                int hashIndex = newVal.indexOf('#');
                int dashIndex = newVal.indexOf('-', hashIndex);
                if (hashIndex != -1 && dashIndex != -1) {
                    String roomNumStr = newVal.substring(hashIndex + 1, dashIndex).trim();
                    roomNumberField.setText(roomNumStr);
                }
            }
        });

        // Booking controls
        Label bookingLabel = new Label("Book Room:");
        roomNumberField = new TextField();
        roomNumberField.setPromptText("Room Number");
        nightsField = new TextField();
        nightsField.setPromptText("Nights");
        Button bookRoomBtn = new Button("Book Room");
        bookRoomBtn.setOnAction(e -> bookRoom());

        HBox bookingBox = new HBox(10, roomNumberField, nightsField, bookRoomBtn);

        // Customer info area
        Label customerInfoLabel = new Label("Customer Info:");
        customerInfoArea = new TextArea();
        customerInfoArea.setEditable(false);
        customerInfoArea.setPrefRowCount(5);

        // New booked rooms ListView and label
        Label bookedRoomsLabel = new Label("Booked Rooms:");
        bookedRoomsListView = new ListView<>();

        root.getChildren().addAll(createCustLabel, customerBox,
                roomsLabel, roomsListView,
                bookingLabel, bookingBox,
                customerInfoLabel, customerInfoArea,
                bookedRoomsLabel, bookedRoomsListView);

        Scene scene = new Scene(root, 600, 550);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hotel Booking System");
        primaryStage.show();
    }

    private void createCustomer() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String balText = balanceField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || balText.isEmpty()) {
            showAlert("Error", "Please fill all customer fields.");
            return;
        }

        double balance;
        try {
            balance = Double.parseDouble(balText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Balance must be a number.");
            return;
        }
        if (balance <= 0) {
            showAlert("Error", "Balance must be positive.");
            return;
        }

        currentCustomer = new Customer(name, email, new BankAccount(balance));
        updateCustomerInfo();
        updateBookedRoomsList();
        showAlert("Success", "Customer created.");
    }

    private void bookRoom() {
        if (currentCustomer == null) {
            showAlert("Error", "Create a customer first.");
            return;
        }

        String roomText = roomNumberField.getText().trim();
        String nightsText = nightsField.getText().trim();
        int roomNum, nights;

        try {
            roomNum = Integer.parseInt(roomText);
            nights = Integer.parseInt(nightsText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Room number and nights must be integers.");
            return;
        }

        if (nights <= 0) {
            showAlert("Error", "Nights must be positive.");
            return;
        }

        boolean booked = hotel.bookRoom(roomNum, currentCustomer, nights);
        if (booked) {
            showAlert("Success", "Room booked successfully!");
            updateRoomList();
            updateCustomerInfo();
            updateBookedRoomsList();
        } else {
            showAlert("Failed", "Booking failed. Check availability or balance.");
        }
    }

    private void updateRoomList() {
        roomsListView.getItems().clear();
        for (Room room : hotel.getRooms()) {
            String status = room.isAvailable() ? "Available" : "Booked";
            roomsListView.getItems().add(room.getRoomType() + " #" + room.getRoomNumber()
                    + " - $" + room.getPricePerNight() + "/night (" + status + ")");
        }
    }

    private void updateCustomerInfo() {
        if (currentCustomer == null) {
            customerInfoArea.clear();
            return;
        }
        String info = "Name: " + currentCustomer.getName() + "\n"
                + "Email: " + currentCustomer.getEmail() + "\n"
                + "Balance: $" + String.format("%.2f", currentCustomer.getBankAccount().getBalance());
        customerInfoArea.setText(info);
    }

    private void updateBookedRoomsList() {
        bookedRoomsListView.getItems().clear();
        if (currentCustomer == null) return;

        // Iterate all bookings of hotel, find those for current customer
        for (Booking booking : hotel.getBookings()) {
            if (booking.getCustomer().equals(currentCustomer)) {
                Room room = booking.getRoom();
                String bookedInfo = room.getRoomType() + " #" + room.getRoomNumber()
                        + " for " + booking.getNights() + " night(s)";
                bookedRoomsListView.getItems().add(bookedInfo);
            }
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
