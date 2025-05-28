module com.example.bookingnewversion {
    requires javafx.controls;
    requires javafx.fxml;

    opens BookingSystem to javafx.graphics;
}
