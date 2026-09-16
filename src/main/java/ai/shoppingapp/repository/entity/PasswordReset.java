package ai.shoppingapp.repository.entity;

import java.time.LocalDateTime;

public class PasswordReset {
    private String email;
    private String otp;
    private LocalDateTime expiresAt;
    private int attemptCount;

    public PasswordReset() {}

    public PasswordReset(String email, String otp, LocalDateTime expiresAt, int attemptCount) {
        this.email = email;
        this.otp = otp;
        this.expiresAt = expiresAt;
        this.attemptCount = attemptCount;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public int getAttemptCount() { return attemptCount; }
    public void setAttemptCount(int attemptCount) { this.attemptCount = attemptCount; }
}