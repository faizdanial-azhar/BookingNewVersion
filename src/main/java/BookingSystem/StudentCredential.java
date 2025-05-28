package BookingSystem;

import java.io.*;

public class StudentCredential {
    private final String filename = "src/main/java/BookingSystem/student.txt";

    public boolean registerStudent(Student student) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {

            String csvLine = String.format("%s,%s,%s,%s,%s,%s",
                    student.getStudentId(),
                    student.getPassword(),
                    student.getName(),
                    student.getEmail(),
                    student.getPhone(),
                    student.getFaculty());
            bw.write(csvLine);
            bw.newLine();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Student loginStudent(String matric, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromCSV(line);
                if (s != null && s.getStudentId().equals(matric) && s.getPassword().equals(password)) {
                    return s;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
