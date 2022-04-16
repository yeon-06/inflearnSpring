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
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection connection = DBConnectionUtil.getConnection();
        PreparedStatement preparedStatement = null;

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
        String sql = "select * from member where member_id=?";

        Connection connection = DBConnectionUtil.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

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

    public void update(String memberId, int money) {
        String sql = "update member set money=? where member_id=?";

        Connection connection = DBConnectionUtil.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, money);
            preparedStatement.setString(2, memberId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("[DB ERROR]", e);
            throw new IllegalStateException(e);

        } finally {
            DBConnectionUtil.close(connection, preparedStatement);
        }
    }

    public void delete(String memberId) {
        String sql = "delete from member where member_id=?";

        Connection connection = DBConnectionUtil.getConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, memberId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("[DB ERROR]", e);
            throw new IllegalStateException(e);

        } finally {
            DBConnectionUtil.close(connection, preparedStatement);
        }
    }
}
