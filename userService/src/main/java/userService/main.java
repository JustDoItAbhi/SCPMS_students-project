package userService;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class main {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        int randomOtp = random.nextInt(1000000);
        String otp = String.format("%06d", randomOtp);

        LocalDateTime generatedTime = LocalDateTime.now();
        LocalDateTime expiryTime = generatedTime.plusMinutes(5);

        // Create formatter for human-readable time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

        // Format the times
        String generatedTimeReadable = generatedTime.format(formatter);
        String expiryTimeReadable = expiryTime.format(formatter);

        System.out.println("OTP: " + otp +
                " | Generated at: " + generatedTimeReadable +
                " | Expires at: " + expiryTimeReadable);
    }
}
