import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLDelete {
	
	SQLDelete(){
		
	}
	

		// This is used to delete the existing highscore table. 
	    // Needed to the new data can be added and not appended.
		public void clearHighscoreTable() throws SQLException {
	        String sql = "DELETE FROM highscore";

	        try (Connection connection = DBConnector.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

	            // Execute the delete statement
	            preparedStatement.executeUpdate();

	            System.out.println("Highscore table cleared successfully!");

	        } catch (SQLException e) {
	            e.printStackTrace(); 
	            throw e; 
	        }
	    }
		
		
		// Delete a specific row then reorder IDs
		void deleteRowFromSQLTable(int rowID, String tableName) {
		    try (Connection conn = DBConnector.getConnection()) {
		        // Use a PreparedStatement to prevent SQL injection vulnerabilities
		        PreparedStatement stmt = conn.prepareStatement("DELETE FROM "+tableName+" WHERE "+tableName+"_ID = ?");
		        stmt.setInt(1, rowID);  // Set the rowID parameter

		        int rowsDeleted = stmt.executeUpdate();

		        if (rowsDeleted > 0) {
		            System.out.println("Row with ID " + rowID + " deleted from the SQL table.");
		        } else {
		            System.out.println("Invalid rowID. No row deleted.");
		        }

		       
		    } catch (SQLException e) {
		        e.printStackTrace();  
		    }
		}
		
	
	
	
}
