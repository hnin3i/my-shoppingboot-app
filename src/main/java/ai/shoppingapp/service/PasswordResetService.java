package ai.shoppingapp.service;

import ai.shoppingapp.repository.PasswordResetRepository;
import ai.shoppingapp.repository.UserRepository;
import ai.shoppingapp.repository.entity.PasswordReset;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetRepository resetRepo;
    private final JavaMailSender mailSender;
    private final PasswordService passwordService;

    public PasswordResetService(UserRepository userRepository, 
                                PasswordResetRepository resetRepo,
                                JavaMailSender mailSender, 
                                PasswordService passwordService) {
        this.userRepository = userRepository;
        this.resetRepo = resetRepo;
        this.mailSender = mailSender;
        this.passwordService = passwordService;
    }

    public boolean sendForgotPasswordOtp(String email) {
        var user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        SecureRandom random = new SecureRandom();
        String otp = String.format("%06d", random.nextInt(1_000_000));

        PasswordReset reset = new PasswordReset();
        reset.setEmail(email);
        reset.setOtp(otp);
        reset.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        reset.setAttemptCount(0);

        resetRepo.saveOrUpdate(reset);

        sendHtmlEmail(
            email,
            "Reset Your Password — Shopping App",
            "Reset Password",
            "Hello " + (user.getName() != null ? user.getName() : "Member") + ",",
            "We received a request to reset your password. Use the verification code below to set a new password:",
            otp
        );

        return true;
    }

    public String verifyResetOtpAndChangePassword(String email, String enteredOtp, String newPassword) {
        PasswordReset reset = resetRepo.findByEmail(email);
        if (reset == null) {
            return "Reset session expired or invalid. Please request a new code.";
        }

        if (reset.getExpiresAt().isBefore(LocalDateTime.now())) {
            resetRepo.deleteByEmail(email);
            return "Verification code has expired. Please request a new one.";
        }

        if (!reset.getOtp().equals(enteredOtp)) {
            reset.setAttemptCount(reset.getAttemptCount() + 1);
            resetRepo.saveOrUpdate(reset);
            return "Invalid verification code.";
        }

        var user = userRepository.findByEmail(email);
        if (user != null) {
            String encodedPassword = passwordService.encode(newPassword);
            userRepository.changePasswordByEmail(email, encodedPassword);
        }

        resetRepo.deleteByEmail(email);
        return "SUCCESS";
    }

    private void sendHtmlEmail(String to, String subject, String title, String greeting, String bodyText, String otp) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);

            String htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                  <meta charset="UTF-8">
                  <style>
                    body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; background-color: #222222; margin: 0; padding: 20px; color: #f5f5f5; }
                    .email-card { max-width: 500px; margin: 0 auto; background: #1a1a1a; border-radius: 0; padding: 36px; border: 1px solid #383838; }
                    .brand-header { font-size: 18px; font-weight: 700; color: #ffffff; text-transform: uppercase; letter-spacing: 2px; margin-bottom: 24px; }
                    .title { font-size: 16px; font-weight: 600; color: #ffffff; margin-bottom: 12px; text-transform: uppercase; letter-spacing: 1px; }
                    .message { font-size: 14px; line-height: 1.6; color: #cccccc; margin-bottom: 24px; }
                    .otp-box { background: #2a2a2a; border: 1px solid #383838; text-align: center; padding: 20px 0; margin: 24px 0; }
                    .otp-code { font-size: 32px; font-weight: 800; letter-spacing: 8px; color: #ffffff; }
                    .expiry-note { font-size: 12px; color: #888888; text-align: center; margin-top: 8px; }
                    .footer { font-size: 11px; color: #666666; margin-top: 32px; border-top: 1px solid #2a2a2a; padding-top: 16px; text-align: center; text-transform: uppercase; letter-spacing: 1px; }
                  </style>
                </head>
                <body>
                  <div class="email-card">
                    <div class="brand-header">Shopping App</div>
                    <div class="title">%s</div>
                    <p class="message">%s<br><br>%s</p>
                    <div class="otp-box">
                      <div class="otp-code">%s</div>
                      <div class="expiry-note">Code expires in 5 minutes.</div>
                    </div>
                    <p class="message">If you did not request this, please disregard this email.</p>
                    <div class="footer">&copy; Shopping App. All rights reserved.</div>
                  </div>
                </body>
                </html>
                """.formatted(title, greeting, bodyText, otp);

            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }
}