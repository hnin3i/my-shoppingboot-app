package ai.shoppingapp.repository;

import ai.shoppingapp.repository.entity.PasswordReset;
import ai.shoppingapp.repository.mapper.PasswordResetMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PasswordResetRepository {

    private final JdbcTemplate jdbcTemplate;

    public PasswordResetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PasswordReset findByEmail(String email) {
        String sql = "SELECT * FROM password_resets WHERE email = ?";
        List<PasswordReset> results = jdbcTemplate.query(sql, new PasswordResetMapper(), email);
        return results.isEmpty() ? null : results.get(0);
    }

    public int saveOrUpdate(PasswordReset reset) {
        String sql = "INSERT INTO password_resets (email, otp, expires_at, attempt_count) "
                   + "VALUES (?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE "
                   + "otp = VALUES(otp), expires_at = VALUES(expires_at), attempt_count = 0";

        return jdbcTemplate.update(sql,
                reset.getEmail(),
                reset.getOtp(),
                reset.getExpiresAt(),
                reset.getAttemptCount()
        );
    }

    public int incrementAttemptCount(String email) {
        String sql = "UPDATE password_resets SET attempt_count = attempt_count + 1 WHERE email = ?";
        return jdbcTemplate.update(sql, email);
    }

    public int deleteByEmail(String email) {
        String sql = "DELETE FROM password_resets WHERE email = ?";
        return jdbcTemplate.update(sql, email);
    }
}