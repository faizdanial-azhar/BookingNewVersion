package BookingSystem;

import java.util.ArrayList;

public class Student {
    private String studentId;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String faculty;
    private ArrayList<Booking> bookings;


    public Student(String studentId, String name,String password, String email, String phone, String faculty) {
        this.studentId = studentId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.faculty = faculty;
        this.bookings = new ArrayList<>(); // ← Initialize here

    }

    public String getStudentId() { return studentId; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getFaculty() { return faculty; }


    public static Student fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 6) return null;
        return new Student(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}