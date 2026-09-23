package ai.shoppingapp.repository.entity;

import java.time.LocalDateTime;

public class EmailVerification {
    private String email;
    private String otp;
    private String name;
    private String phone;
    private String password;
    private String address;
    private LocalDateTime expiresAt;
    private int attemptCount;

    public EmailVerification() {}

    public EmailVerification(String email, String otp, String name, String phone,
                             String password, String address, LocalDateTime expiresAt, int attemptCount) {
        this.email = email;
        this.otp = otp;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.expiresAt = expiresAt;
        this.attemptCount = attemptCount;
    }

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public int getAttemptCount() { return attemptCount; }
    public void setAttemptCount(int attemptCount) { this.attemptCount = attemptCount; }
}