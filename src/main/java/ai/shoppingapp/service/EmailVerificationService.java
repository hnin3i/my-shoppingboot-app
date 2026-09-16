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
        // 1. Generate 6-digit cryptographic OTP
        SecureRandom random = new SecureRandom();
        String otp = String.format("%06d", random.nextInt(1_000_000));

        // 2. Hash raw password
        String hashedPassword = passwordService.encode(model.getPassword());

        // 3. Stage registration with 5-minute expiry
        EmailVerification ev = new EmailVerification();
        ev.setEmail(model.getEmail());
        ev.setOtp(otp);
        ev.setName(model.getName());
        ev.setPhone(model.getPhone());
        ev.setPassword(hashedPassword);
        ev.setAddress(model.getAddress());
        ev.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        ev.setAttemptCount(0);

        verificationRepo.saveOrUpdate(ev);

        // 4. Dispatch Email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(model.getEmail());
        message.setSubject("Shop Account Verification Code");
        message.setText("Hello " + model.getName() + ",\n\n"
                + "Your account verification code is: " + otp + "\n"
                + "This code will expire in 5 minutes.\n\n"
                + "If you did not request this code, please ignore this email.");

        mailSender.send(message);
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
    
}