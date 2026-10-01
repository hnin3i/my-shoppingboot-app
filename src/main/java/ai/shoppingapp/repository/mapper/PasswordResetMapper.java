package ai.shoppingapp.repository.mapper;

import ai.shoppingapp.repository.entity.PasswordReset;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class PasswordResetMapper implements RowMapper<PasswordReset> {
    @Override
    public PasswordReset mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocalDateTime expiresAt = rs.getTimestamp("expires_at") != null
                ? rs.getTimestamp("expires_at").toLocalDateTime()
                : null;

        return new PasswordReset(
            rs.getString("email"),
            rs.getString("otp"),
            expiresAt,
            rs.getInt("attempt_count")
        );
    }
}