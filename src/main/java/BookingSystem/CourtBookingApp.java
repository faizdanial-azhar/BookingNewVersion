package BookingSystem;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourtBookingApp extends Application {
    private TextField regMatricField, regNameField, regEmailField, regPhoneField, regFacultyField, loginMatricField;
    private PasswordField regPasswordField, loginPasswordField;
    private final Label selectedCourtLabel = new Label("Selected Facility: None");
    private final List<Student> studentsList = new ArrayList<>();
    private final ArrayList<Booking> bookings = new ArrayList<>();
    private final List<Facility> facilities = new ArrayList<>();
    private Student currentUser, student;
    private final StudentCredential manager = new StudentCredential();
    private final String studentBookingFile = "src/main/java/BookingList/student_booking_";
    private String paymentMethod;
    private boolean paymentStatus;
    private Facility selectedFacility;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Court Booking System");
        showLoginStage(primaryStage);
    }

    private void showLoginStage(Stage primaryStage) {//aznil
        primaryStage.setTitle("Student Login & Registration");
        // Create a TabPane
        TabPane tabPane = new TabPane();
        // --- Register Tab ---
        Tab registerTab = new Tab("Register");
        registerTab.setClosable(false);

        GridPane registerGrid = new GridPane();
        registerGrid.setPadding(new Insets(10, 10, 10, 10));
        registerGrid.setVgap(8);
        registerGrid.setHgap(10);

        // Register fields
        regMatricField = new TextField();
        regPasswordField = new PasswordField();
        regNameField = new TextField();
        regEmailField = new TextField();
        regPhoneField = new TextField();
        regFacultyField = new TextField();
        Button registerBtn = new Button("Register");

        // Add components to grid
        registerGrid.add(new Label("Matric No:"), 0, 0);
        registerGrid.add(regMatricField, 1, 0);
        registerGrid.add(new Label("Password:"), 0, 1);
        registerGrid.add(regPasswordField, 1, 1);
        registerGrid.add(new Label("Name:"), 0, 2);
        registerGrid.add(regNameField, 1, 2);
        registerGrid.add(new Label("Email:"), 0, 3);
        registerGrid.add(regEmailField, 1, 3);
        registerGrid.add(new Label("Phone:"), 0, 4);
        registerGrid.add(regPhoneField, 1, 4);
        registerGrid.add(new Label("Faculty Name:"), 0, 5);
        registerGrid.add(regFacultyField, 1, 5);
        registerGrid.add(registerBtn, 1, 6);

        registerTab.setContent(registerGrid);

        // --- Login Tab ---
        Tab loginTab = new Tab("Login");
        loginTab.setClosable(false);

        GridPane loginGrid = new GridPane();
        loginGrid.setPadding(new Insets(10, 10, 10, 10));
        loginGrid.setVgap(8);
        loginGrid.setHgap(10);

        // Login fields
        loginMatricField = new TextField();
        loginPasswordField = new PasswordField();
        Button loginBtn = new Button("Login");

        // Add components to grid
        loginGrid.add(new Label("Matric No:"), 0, 0);
        loginGrid.add(loginMatricField, 1, 0);
        loginGrid.add(new Label("Password:"), 0, 1);
        loginGrid.add(loginPasswordField, 1, 1);
        loginGrid.add(loginBtn, 1, 2);

        loginTab.setContent(loginGrid);

        // Add tabs to tabPane
        tabPane.getTabs().addAll(registerTab, loginTab);

        // Create the main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(tabPane);


        Scene scene = new Scene(mainLayout, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Register button action
        registerBtn.setOnAction(e -> {
            String matric = regMatricField.getText().trim();
            String password = regPasswordField.getText().trim();
            String name = regNameField.getText().trim();
            String email = regEmailField.getText().trim();
            String phone = regPhoneField.getText().trim();
            String faculty = regFacultyField.getText().trim();

            if (matric.isEmpty() || password.isEmpty() || name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Error");
                alert.setHeaderText(null);
                alert.setContentText("Matric, Password, and Name are required.");
                alert.showAndWait();
                return;
            }

            student = new Student(matric, password, name, email, phone, faculty);
            studentsList.add(student);
            if (manager.registerStudent(student)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Success");
                alert.setHeaderText(null);
                alert.setContentText("Registration successful!");
                alert.showAndWait();
                clearRegisterFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registration Error");
                alert.setHeaderText(null);
                alert.setContentText("Registration failed.");
                alert.showAndWait();
            }
        });

        // Login button action
        loginBtn.setOnAction(e -> {
            String matric = loginMatricField.getText().trim();
            String password = loginPasswordField.getText().trim();

            Student student = manager.loginStudent(matric, password);
            if (student != null) {
                currentUser = student;
                String successMessage = "Login successful! Welcome, " + student.getName();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Success");
                alert.setHeaderText(null);
                alert.setContentText(successMessage);
                alert.showAndWait();

                // Open booking page
                try {
                    // Close this window
                    primaryStage.close();
                    mainMenuStage(primaryStage);
                    // Create and show the JavaFX BookingPage

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid credentials.");
                alert.showAndWait();
            }
        });
    }


    private void mainMenuStage(Stage stage) {//faiz danial

        VBox menu = new VBox(10);
        Button makeBookingBtn = new Button("Make Booking");
        Button viewBookingBtn = new Button("View Booking History");
        Button logoutBtn = new Button("Logout");

        makeBookingBtn.setOnAction(e -> {

            facilitySelectionStage(new Stage());
        });
        viewBookingBtn.setOnAction(e -> bookingHistoryStage(new Stage()));
        logoutBtn.setOnAction(e -> {
            currentUser = null;
            showLoginStage(new Stage());
            stage.close();
        });

        menu.getChildren().addAll(makeBookingBtn, viewBookingBtn, logoutBtn);
        Scene scene = new Scene(menu, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void bookingHistoryStage(Stage stage) {//amirin
        VBox historyView = new VBox(10);
        ListView<String> bookingList = new ListView<>();

        try (BufferedReader br = new BufferedReader(new FileReader(studentBookingFile + currentUser.getStudentId() + ".txt"))) {
            String line;
            String currentBooking = "";

            while ((line = br.readLine()) != null) {
                if (line.contains("---------------------------------------")) {
                    if (!currentBooking.isEmpty()) {
                        bookingList.getItems().add(currentBooking.trim());
                        currentBooking = "";
                    }
                } else {
                    currentBooking += line + "\n";
                }
            }
            // Add the last booking if exists
            if (!currentBooking.isEmpty()) {
                bookingList.getItems().add(currentBooking.trim());
            }
            if (bookingList.getItems().isEmpty()) {
                bookingList.getItems().add("No bookings found");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Button closeBtn = new Button("Close");
        closeBtn.setOnAction(e -> stage.close());

        historyView.getChildren().addAll(bookingList, closeBtn);
        Scene scene = new Scene(historyView, 500, 400);
        stage.setTitle("Booking History");
        stage.setScene(scene);
        stage.show();
    }

    private void facilitySelectionStage(Stage stage) {//aznil

        stage.setTitle("Facility Booking");
        initializeFacilities();
        BorderPane mainLayout = new BorderPane();

        Label titleLabel = new Label("ClickSport");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10));
        titleBox.setStyle("-fx-background-color: lightgray;");
        mainLayout.setTop(titleBox);

        Label infoLabel = new Label("Select a facility to view details.");
        VBox facilityListBox = new VBox(10);
        VBox rootPane = new VBox();

        facilityListBox.setPadding(new Insets(10));

        for (Facility facility : facilities) {

            facilityListBox.getChildren().add(createFacilityCard(facility));
        }
        Button bookBtn = new Button("Book");

        bookBtn.setOnAction(e -> {
            Stage bookingStage = bookingStage(new Stage());
            bookingStage.show();
            stage.close();
        });

        rootPane.getChildren().addAll(infoLabel, selectedCourtLabel, facilityListBox, bookBtn);
        mainLayout.setCenter(rootPane);

        Scene scene = new Scene(mainLayout, 700, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void initializeFacilities() {//aznil
        facilities.clear();
        facilities.add(new BadmintonCourt());
        facilities.add(new FutsalCourt());
    }

    private BorderPane createFacilityCard(Facility facility) {//aznil
        BorderPane cardPane = new BorderPane();
        cardPane.setPadding(new Insets(10));
        cardPane.setStyle("-fx-background-color: white; -fx-border-color: lightgray; -fx-border-width: 1;");

        // Image display logic (based on facility type)
        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        String imagePath = "";
        if (facility instanceof BadmintonCourt) {
            imagePath = "images/badminton.jpg";
        } else if (facility instanceof FutsalCourt) {
            imagePath = "images/futsal.jpg";
        }

        try {
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                Image image = new Image(new FileInputStream(imgFile));
                imageView.setImage(image);
                cardPane.setLeft(imageView);
            } else {
                cardPane.setLeft(createPlaceholder("No Image"));
            }
        } catch (FileNotFoundException e) {
            cardPane.setLeft(createPlaceholder("Error Loading Image"));
        }

        VBox infoBox = new VBox(5);
        infoBox.setPadding(new Insets(0, 0, 0, 10));
        infoBox.setStyle("-fx-background-color: #ADD8E6;");

        infoBox.getChildren().addAll(
                new Label("Facility Name: " + facility.getFacilityName()),
                new Label("Rate: RM" + facility.getRatePerHour() + "/hour"),
                new Label("Details: " + facility.getFacilityDetails())
        );

        infoBox.setOnMouseClicked(e -> {
            selectedFacility = facility;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Facility Selected");
            alert.setHeaderText(null);
            alert.setContentText(facility.getFacilityName() + " selected!");
            alert.showAndWait();
            selectedCourtLabel.setText("Selected Facility: " + facility.getFacilityName());
        });
        infoBox.setOnMouseEntered(e -> infoBox.setStyle("-fx-background-color: #87CEFA;"));
        infoBox.setOnMouseExited(e -> infoBox.setStyle("-fx-background-color: #ADD8E6;"));
        cardPane.setCenter(infoBox);
        return cardPane;
    }


    private Stage bookingStage(Stage stage) {//faiz danial

        // Create Time Selection components
        Label timeLabel = new Label("Select Time:");
        TextField startTimeField = new TextField();
        startTimeField.setPromptText("Start Time (e.g., 14)");
        startTimeField.setPrefWidth(150);

        TextField endTimeField = new TextField();
        endTimeField.setPromptText("End Time (e.g., 16)");
        endTimeField.setPrefWidth(150);

        // Create UI components - Title and Facility Details
        Label titleLabel = new Label("Booking Details");
        Label priceLabel = new Label("Price/Hour: RM" + selectedFacility.getRatePerHour());
        Label courtLabel = new Label("Court: " + selectedFacility.getFacilityName());
        Label totalPriceLabel = new Label("Total Price: RM" + selectedFacility.getTotalCost());
        totalPriceLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;");

        Label durationLabel = new Label("Duration: 0 hour(s)");

//fix this one
        // Add listeners to update duration when time fields change
        startTimeField.textProperty().addListener((observable, oldValue, newValue) -> {
            selectedFacility.setDuration(newValue, endTimeField.getText());
            durationLabel.setText("Duration: " + selectedFacility.getDuration() + " hour(s)");
            totalPriceLabel.setText("Total Price: RM" + selectedFacility.getTotalCost());
        });

        endTimeField.textProperty().addListener((observable, oldValue, newValue) -> {
            selectedFacility.setDuration(startTimeField.getText(), newValue);
            durationLabel.setText("Duration: " + selectedFacility.getDuration() + " hour(s)");
            totalPriceLabel.setText("Total Price: RM" + selectedFacility.getTotalCost());
        });

        // Create Date Selection components
        DatePicker datePicker = new DatePicker(LocalDate.now());
        Label dateLabel = new Label("Booking for: " + LocalDate.now().toString());

        // Set up date picker action
        datePicker.setOnAction(e -> {
            LocalDate selectedDate = datePicker.getValue();
            dateLabel.setText("Booking for: " + selectedDate.toString());
        });


        Button onlineBankingButton = new Button("Online Banking");
        onlineBankingButton.setPrefWidth(100);
        onlineBankingButton.setOnAction(e -> {
            paymentMethod = "Online Banking";


        });

        //  Book Now button
        Button bookButton = new Button("Book Now");
        bookButton.setPrefWidth(100);


        // Set button action (placeholder for now)
        bookButton.setOnAction(e -> {


            // Update duration one more time to ensure it's correct
            String startTime = startTimeField.getText();
            String endTime = endTimeField.getText();
            String date = datePicker.getValue().toString();
            int durationBooking = selectedFacility.getDuration();
            selectedFacility.setDuration(startTime, endTime);
            durationLabel.setText("Duration: " + durationBooking + " hour(s)");


            paymentStatus = paymentConfirmation();

            Booking newBooking = new Booking(currentUser, selectedFacility, date, startTime, endTime, durationBooking, paymentMethod, paymentStatus);
            bookings.add(newBooking);
            if (!paymentStatus) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Payment Cancelled");
                alert.setHeaderText(null);
                alert.setContentText("You have cancelled the payment.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Booking Created");
                alert.setHeaderText(null);
                alert.setContentText("Redirecting to payment gateway...");
                alert.showAndWait();


            }


            Stage receiptStage = receiptStage(newBooking);
            receiptStage.show();
            stage.close();

            System.out.println(newBooking.getBookingDetails());


        });


        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(100);

        cancelButton.setOnAction(e -> {
            stage.close();
        });

        // Create layout - Facility Details Box (Left)
        VBox detailsBox = new VBox(10); // 10px spacing between elements
        detailsBox.setStyle("-fx-padding: 15px;");
        detailsBox.getChildren().addAll(
                titleLabel,
                priceLabel,
                courtLabel,
                durationLabel, totalPriceLabel, onlineBankingButton
        );

        // Create layout - Date Selection Box (Top-Center)
        VBox dateBox = new VBox(10);
        dateBox.getChildren().addAll(datePicker, dateLabel);

        // Create layout - Time Selection Box (Center)
        VBox timeBox = new VBox(10);

        timeBox.getChildren().addAll(timeLabel, startTimeField, endTimeField);

        // Create layout - Button Box (Bottom-Center)
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(bookButton, cancelButton);

        // Combine Time and Button boxes
        VBox centerBox = new VBox(10);
        centerBox.getChildren().addAll(dateBox, timeBox, buttonBox);

        // Set up the main layout
        BorderPane rootPane = new BorderPane();
        rootPane.setLeft(detailsBox);
        rootPane.setCenter(centerBox);

        Scene scene = new Scene(rootPane, 500, 400);
        stage.setTitle("Booking");
        stage.setScene(scene);
        return stage;
    }

    private StackPane createPlaceholder(String message) {
        StackPane placeholder = new StackPane(new Label(message));
        placeholder.setStyle("-fx-background-color: gray;");
        placeholder.setPrefSize(150, 100);
        return placeholder;
    }


    private void clearRegisterFields() {
        regMatricField.setText("");
        regPasswordField.setText("");
        regNameField.setText("");
        regEmailField.setText("");
        regPhoneField.setText("");
        regFacultyField.setText("");
    }

    public boolean paymentConfirmation() {//amirin
        Stage stage = new Stage();

        // Create title label
        Label titleLabel = new Label("Please confirm your payment");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Create buttons with consistent width
        Button payButton = new Button("Pay");
        Button cancelButton = new Button("Cancel");
        Button submitButton = new Button("Submit");

        payButton.setPrefWidth(100);
        cancelButton.setPrefWidth(100);
        submitButton.setPrefWidth(100);

        // Initially disable submit button until a choice is made
        submitButton.setDisable(true);

        // Set button actions
        payButton.setOnAction(e -> {
            paymentStatus = true;
            cancelButton.setDisable(false);
            payButton.setDisable(true);
            submitButton.setDisable(false);
        });

        cancelButton.setOnAction(e -> {
            paymentStatus = false;
            payButton.setDisable(false);
            cancelButton.setDisable(true);
            submitButton.setDisable(false);
        });

        submitButton.setOnAction(e -> {
            stage.close();
        });

        // Create button layout with spacing
        HBox buttonBox = new HBox(15); // 15px spacing between buttons
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));
        buttonBox.getChildren().addAll(payButton, cancelButton);

        // Create main layout
        VBox mainLayout = new VBox(20); // 20px spacing between elements
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(30));
        mainLayout.getChildren().addAll(titleLabel, buttonBox, submitButton);

        // Create scene and show stage
        Scene scene = new Scene(mainLayout, 500, 300);
        stage.setTitle("Payment Confirmation");
        stage.setScene(scene);
        stage.showAndWait();

        return paymentStatus;
    }

    public Stage receiptStage(Booking booking) {//entah
        Stage stage = new Stage();


        Payment payment = new Payment(booking);
        Text text = new Text(payment.generateReceipt());



        VBox vbox = new VBox(10);
        Scene scene = new Scene(vbox,500,200);
        vbox.getChildren().add(text);

        stage.setScene(scene);
        stage.setTitle("Receipt");
        payment.writeBookingIntoFile(currentUser, studentBookingFile);
        return stage;
    }


}
