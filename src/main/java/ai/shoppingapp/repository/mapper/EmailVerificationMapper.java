package ai.shoppingapp.repository.mapper;

import ai.shoppingapp.repository.entity.EmailVerification;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class EmailVerificationMapper implements RowMapper<EmailVerification> {

    @Override
    public EmailVerification mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocalDateTime expiresAt = rs.getTimestamp("expires_at") != null 
                ? rs.getTimestamp("expires_at").toLocalDateTime() 
                : null;

        return new EmailVerification(
            rs.getString("email"),
            rs.getString("otp"),
            rs.getString("name"),
            rs.getString("phone"),
            rs.getString("password"),
            rs.getString("address"),
            expiresAt,
            rs.getInt("attempt_count")
        );
    }
}