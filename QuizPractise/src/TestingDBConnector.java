import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

/**
 * JUnit test class for comprehensive testing of the DBConnector class.
 */
class TestingDBConnector {
	
	

	@Test
    public void testGetConnection_successful() {
		//Testing correct login credentials
        try {
            Connection connection = DBConnector.getConnection();
            assertNotNull("Connection should not be null", connection);
        } catch (SQLException e) {
            fail("Unexpected SQLException: " + e.getMessage());
        }
    }

    @Test
    public void testGetConnection_open() {
    	//Testing if connection has been made and is open
        try {
            Connection connection = DBConnector.getConnection();
            // Verify that the connection is open
            assertTrue(connection.isValid(5));
            
        } catch (SQLException e) {
            
        }
    }


}
