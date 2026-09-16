package ai.shoppingapp.repository;

import ai.shoppingapp.repository.entity.EmailVerification;
import ai.shoppingapp.repository.mapper.EmailVerificationMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailVerificationRepository {

    private final JdbcTemplate jdbcTemplate;

    public EmailVerificationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public EmailVerification findByEmail(String email) {
        String sql = "SELECT * FROM email_verifications WHERE email = ?";
        List<EmailVerification> results = jdbcTemplate.query(sql, new EmailVerificationMapper(), email);
        return results.isEmpty() ? null : results.get(0);
    }

    public int saveOrUpdate(EmailVerification ev) {
        String sql = "INSERT INTO email_verifications (email, otp, name, phone, password, address, expires_at, attempt_count) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE "
                   + "otp = VALUES(otp), name = VALUES(name), phone = VALUES(phone), "
                   + "password = VALUES(password), address = VALUES(address), "
                   + "expires_at = VALUES(expires_at), attempt_count = 0";

        return jdbcTemplate.update(sql,
                ev.getEmail(),
                ev.getOtp(),
                ev.getName(),
                ev.getPhone(),
                ev.getPassword(),
                ev.getAddress(),
                ev.getExpiresAt(),
                ev.getAttemptCount()
        );
    }

    public int incrementAttemptCount(String email) {
        String sql = "UPDATE email_verifications SET attempt_count = attempt_count + 1 WHERE email = ?";
        return jdbcTemplate.update(sql, email);
    }

    public int deleteByEmail(String email) {
        String sql = "DELETE FROM email_verifications WHERE email = ?";
        return jdbcTemplate.update(sql, email);
    }
}