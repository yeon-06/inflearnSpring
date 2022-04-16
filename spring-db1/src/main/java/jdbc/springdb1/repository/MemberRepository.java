package jdbc.springdb1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdbc.springdb1.connection.DBConnectionUtil;
import jdbc.springdb1.domain.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberRepository {

    public Member save(Member member) {
        Connection connection = DBConnectionUtil.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "insert into member(member_id, money) values (?, ?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemberId());
            preparedStatement.setInt(2, member.getMoney());
            preparedStatement.executeUpdate();

            return member;
        } catch (SQLException e) {
            log.error("[DB ERROR]", e);
            throw new IllegalStateException(e);

        } finally {
            DBConnectionUtil.close(connection, preparedStatement);
        }
    }

    public Member findById(String id) {
        Connection connection = DBConnectionUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sql = "select * from member where member_id=?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Member(resultSet.getString("member_id")
                        , resultSet.getInt("money"));
            }

            throw new IllegalStateException("해당 id로 조회되지 않습니다.");

        } catch (SQLException e) {
            log.error("[DB ERROR]", e);
            throw new IllegalStateException(e);

        } finally {
            DBConnectionUtil.close(connection, preparedStatement, resultSet);
        }
    }
}
