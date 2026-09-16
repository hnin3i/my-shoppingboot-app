package ai.shoppingapp.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.shoppingapp.repository.PasswordResetRepository;
import ai.shoppingapp.repository.UserRepository;
import ai.shoppingapp.repository.entity.PasswordReset;

@Service
public class PasswordResetService {

    private final PasswordResetRepository passwordResetRepository;
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final JavaMailSender mailSender;
    

    public PasswordResetService(PasswordResetRepository passwordResetRepository,
                                UserRepository userRepository,
                                PasswordService passwordService,
                                JavaMailSender mailSender) {
        this.passwordResetRepository = passwordResetRepository;
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.mailSender = mailSender;
    }

    public void sendForgotPasswordOtp(String email) {
        SecureRandom random = new SecureRandom();
        String otp = String.format("%06d", random.nextInt(1_000_000));

        PasswordReset reset = new PasswordReset();
        reset.setEmail(email);
        reset.setOtp(otp);
        reset.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        reset.setAttemptCount(0);

        passwordResetRepository.saveOrUpdate(reset);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Verification Code");
        message.setText("Your password reset code is: " + otp + "\n"
                + "This code expires in 5 minutes.\n"
                + "If you did not request a password reset, you can safely ignore this email.");

        mailSender.send(message);
    }

    @Transactional
    public String verifyResetOtpAndChangePassword(String email, String enteredOtp, String newPassword) {
        PasswordReset reset = passwordResetRepository.findByEmail(email);

        if (reset == null) {
            return "No reset request found for this email.";
        }

        if (LocalDateTime.now().isAfter(reset.getExpiresAt())) {
            passwordResetRepository.deleteByEmail(email);
            return "Reset code has expired. Please request a new one.";
        }

        if (reset.getAttemptCount() >= 5) {
            passwordResetRepository.deleteByEmail(email);
            return "Too many invalid attempts. Request a new reset code.";
        }

        if (!reset.getOtp().equals(enteredOtp.trim())) {
            passwordResetRepository.incrementAttemptCount(email);
            return "Invalid code. Please try again.";
        }

        String hashedPassword = passwordService.encode(newPassword);
        userRepository.changePasswordByEmail(email, hashedPassword);

        passwordResetRepository.deleteByEmail(email);

        return "SUCCESS";
    }
}