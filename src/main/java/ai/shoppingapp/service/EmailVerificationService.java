package ai.shoppingapp.service;

import ai.shoppingapp.model.usermanagement.RegisterModel;
import ai.shoppingapp.repository.EmailVerificationRepository;
import ai.shoppingapp.repository.UserRepository;
import ai.shoppingapp.repository.entity.EmailVerification;
import ai.shoppingapp.repository.entity.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EmailVerificationService {

    private final EmailVerificationRepository verificationRepo;
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final JavaMailSender mailSender;

    public EmailVerificationService(EmailVerificationRepository verificationRepo,
                                    UserRepository userRepository,
                                    PasswordService passwordService,
                                    JavaMailSender mailSender) {
        this.verificationRepo = verificationRepo;
        this.userRepository = userRepository;
        this.passwordService = passwordService;
        this.mailSender = mailSender;
    }

    public void generateAndSendOtp(RegisterModel model) {
        // Check if email is already taken in the main users table
        if (userRepository.findByEmail(model.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already registered.");
        }

        // 1. Generate secure 6-digit OTP
        SecureRandom random = new SecureRandom();
        String otp = String.format("%06d", random.nextInt(1_000_000));

        // 2. Hash password & stage pending registration entity
        EmailVerification pending = new EmailVerification();
        pending.setName(model.getName());
        pending.setEmail(model.getEmail());
        pending.setPhone(model.getPhone());
        pending.setAddress(model.getAddress());
        pending.setPassword(passwordService.encode(model.getPassword()));
        pending.setOtp(otp);
        pending.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        pending.setAttemptCount(0);

        verificationRepo.saveOrUpdate(pending);

        // 3. Dispatch styled HTML email
        sendHtmlEmail(
            model.getEmail(),
            "Verify Your Account",
            "Confirm Your Email",
            "Hello " + model.getName() + ",",
            "Thank you for signing up. Use the verification code below to activate your account:",
            otp
        );
    }
    

    @Transactional
    public String verifyOtpAndCreateUser(String email, String enteredOtp) {
        EmailVerification pending = verificationRepo.findByEmail(email);

        if (pending == null) {
            return "No registration in progress for this email. Please register again.";
        }

        // Check if expired
        if (LocalDateTime.now().isAfter(pending.getExpiresAt())) {
            verificationRepo.deleteByEmail(email);
            return "Verification code has expired. Please register again.";
        }

        // Brute-force protection: Max 5 attempts
        if (pending.getAttemptCount() >= 5) {
            verificationRepo.deleteByEmail(email);
            return "Too many invalid attempts. Verification reset. Please sign up again.";
        }

        // Check code match
        if (!pending.getOtp().equals(enteredOtp.trim())) {
            verificationRepo.incrementAttemptCount(email);
            return "Invalid OTP code. Please check your email and try again.";
        }

        // Create permanent user
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(pending.getName());
        user.setEmail(pending.getEmail());
        user.setPhone(pending.getPhone());
        user.setPassword(pending.getPassword()); // already hashed
        user.setRole("CUSTOMER");
        user.setAddress(pending.getAddress());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setProfile(null);

        userRepository.save(user);

        // Delete temporary staged data
        verificationRepo.deleteByEmail(email);

        return "SUCCESS";
    }
    
    public boolean resendOtp(String email) {
        var pending = verificationRepo.findByEmail(email);
        if (pending == null) {
            return false;
        }

        // Generate fresh 6-digit OTP
        java.security.SecureRandom random = new java.security.SecureRandom();
        String newOtp = String.format("%06d", random.nextInt(1_000_000));

        // Update pending record with new OTP, new 5-minute expiry, and reset attempts
        pending.setOtp(newOtp);
        pending.setExpiresAt(java.time.LocalDateTime.now().plusMinutes(5));
        pending.setAttemptCount(0);
        verificationRepo.saveOrUpdate(pending);

        // Send the styled email
        sendHtmlEmail(
            pending.getEmail(),
            "Verify Your Shopping App Account",
            "New Verification Code",
            "Hello " + pending.getName() + ",",
            "You requested a new verification code. Please use the code below:",
            newOtp
        );

        return true;
    }
    private void sendHtmlEmail(String to, String subject, String title, String greeting, String bodyText, String otp) {
        try {
            jakarta.mail.internet.MimeMessage mimeMessage = mailSender.createMimeMessage();
            org.springframework.mail.javamail.MimeMessageHelper helper = 
                    new org.springframework.mail.javamail.MimeMessageHelper(mimeMessage, true, "UTF-8");
            
            helper.setFrom("blackjack.clothing0@gmail.com", "BlackJack");
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