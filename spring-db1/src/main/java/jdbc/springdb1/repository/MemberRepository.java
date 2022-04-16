package jdbc.springdb1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jdbc.springdb1.connection.DBConnectionUtil;
import jdbc.springdb1.domain.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberRepository {

    public Member save(Member member) {
        Connection connection = DBConnectionUtil.getConnection();
        String sql = "insert into member(member_id, money) values (?, ?)";

        try (connection; PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, member.getMemberId());
            preparedStatement.setInt(2, member.getMoney());
            preparedStatement.executeUpdate();

            return member;
        } catch (SQLException e) {
            log.error("[DB ERROR]", e);
            throw new IllegalStateException(e);
        }
    }
}
