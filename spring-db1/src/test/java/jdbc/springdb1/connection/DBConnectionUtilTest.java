package jdbc.springdb1.connection;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DBConnectionUtilTest {

    @DisplayName("연결 확인")
    @Test
    void connection() {
        assertDoesNotThrow(DBConnectionUtil::getConnection);
    }
}