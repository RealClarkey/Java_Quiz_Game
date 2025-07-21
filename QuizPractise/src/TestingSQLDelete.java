import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test class for comprehensive testing of all methods within the SQLDelete class.
 */
class TestingSQLDelete {

	@BeforeAll
	static void creatingTestDatabase() throws Exception {
	    // Statements for creating test tables in the database to work with, will be dropped later
	    String createTable1 = "CREATE TABLE table1 (table1_ID INT(11) PRIMARY KEY AUTO_INCREMENT,"
	            + "table1_Q VARCHAR(200) NOT NULL,"
	            + "table1_A VARCHAR(50) NOT NULL);";

	    String createTable2 = "CREATE TABLE table2 (table2_ID INT(11) PRIMARY KEY AUTO_INCREMENT,"
	            + "table2_Q VARCHAR(200) NOT NULL,"
	            + "table2_A VARCHAR(50) NOT NULL);";

	    String insertValuesStmt1 = "INSERT INTO table1 (table1_Q, table1_A) VALUES "
	            + "('Capital of France?','Paris'),"
	            + "('Number of continents?','Seven'),"
	            + "('Author of Romeo and Juliet?','William Shakespeare'),"
	            + "('Largest mammal?','Blue Whale'),"
	            + "('Year Titanic sank?','1912'),"
	            + "('Capital of Australia?','Canberra'),"
	            + "('Planets in our solar system?','Eight');";

	    String insertValuesStmt2 = "INSERT INTO table2 (table2_Q, table2_A) VALUES "
	            + "('Capital of Japan?','Tokyo'),"
	            + "('Painter of Mona Lisa?','Leonardo da Vinci'),"
	            + "('Currency of Brazil?','Brazilian Real'),"
	            + "('Red Planet?','Mars'),"
	            + "('Author of Harry Potter series?','J.K. Rowling'),"
	            + "('Largest ocean on Earth?','Pacific Ocean'),"
	            + "('Year World War II ended?','1945');";

	    try {
	        // Creating the tables
	        Connection connection = DBConnector.getConnection(); // connect to database
	        PreparedStatement pstmt1 = connection.prepareStatement(createTable1);
	        pstmt1.executeUpdate();
	        pstmt1.close();

	        PreparedStatement pstmt2 = connection.prepareStatement(createTable2);
	        pstmt2.executeUpdate();
	        pstmt2.close();

	        System.out.println("Test tables have been created in the database.");

	        // Inserting values into the tables
	        PreparedStatement pstmt4 = connection.prepareStatement(insertValuesStmt1);
	        pstmt4.executeUpdate();
	        pstmt4.close();

	        PreparedStatement pstmt5 = connection.prepareStatement(insertValuesStmt2);
	        pstmt5.executeUpdate();
	        pstmt5.close();

	        System.out.println("Test values have been inserted");
	        System.out.println("Test highscores have been inserted.");

	        connection.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@AfterAll
	static void deletingDatabase() throws Exception {
	    //Dropping the test tables from server.
	    String dropTablesStmt = "DROP TABLE table1, table2;"; 
	    try {
	        Connection connection = DBConnector.getConnection(); 
	        PreparedStatement pstmtDrop = connection.prepareStatement(dropTablesStmt);

	        pstmtDrop.executeUpdate();
	        pstmtDrop.close();
	        connection.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	
	/**
	* Tests clearHighscoreTable method in the SQLDelete class.
	* Includes two tests cases 
	*/
	@Test
	void testClearHighscoreTable() throws SQLException {
		SQLDelete testD = new SQLDelete();
		
		//Clear highscore table.
		testD.clearHighscoreTable();
		//Test to see if table is empty.
		SQLRead readHighscore = new SQLRead();
        assertTrue(readHighscore.readHighscoresSQL("highscore").isEmpty()); 
		
		//Re-add all the data back to highscores.
		String reEnter = "INSERT INTO Highscore (Highscore_ID, Highscore_Name, Highscore_Number) VALUES "
			    + "(1, 'Leigh', 80), "
			    + "(2, 'Steve Bull', 70), "
			    + "(3, 'Don Goodman', 60), "
			    + "(4, 'Billy Wright', 50), "
			    + "(5, 'Robbie Keane', 40), "
			    + "(6, 'Joe Bloggs', 30), "
			    + "(7, 'Luke Skywalker', 30), "
			    + "(8, 'Darth Vader', 20)";
		
		try {
	        // Creating the tables
	        Connection connection = DBConnector.getConnection();
	        PreparedStatement pstmt1 = connection.prepareStatement(reEnter);

	        pstmt1.executeUpdate();
	        pstmt1.close();

	        System.out.println("Test highscores have been inserted.");

	        connection.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Test
	void checkClearHighscoreTable() throws SQLException {
		SQLDelete testD = new SQLDelete();
		String SQLpath = "highscore";
		//Clear highscore table.
		testD.clearHighscoreTable();
		
		// Check first row for a count. should return 0.
		SQLRead readSingle = new SQLRead();
		assertEquals(readSingle.readHighscoresSQL(SQLpath).size(), 0); 
		
		//Re-add all the data back to highscores.
		String reEnter = "INSERT INTO Highscore (Highscore_ID, Highscore_Name, Highscore_Number) VALUES "
			    + "(1, 'Leigh', 80), "
			    + "(2, 'Steve Bull', 70), "
			    + "(3, 'Don Goodman', 60), "
			    + "(4, 'Billy Wright', 50), "
			    + "(5, 'Robbie Keane', 40), "
			    + "(6, 'Joe Bloggs', 30), "
			    + "(7, 'Luke Skywalker', 30), "
			    + "(8, 'Darth Vader', 20)";
		
		try {
	        // Creating the tables
	        Connection connection = DBConnector.getConnection(); // connect to database
	        PreparedStatement pstmt1 = connection.prepareStatement(reEnter);

	        pstmt1.executeUpdate();
	        pstmt1.close();

	        System.out.println("Test highscores have been inserted.");

	        connection.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	* Tests deleteRowFromSQLTable method in the SQLDelete class.
	* Includes two tests cases 
	*/
	@Test
	void testDeleteRowFromSQLTable() throws SQLException {
		SQLDelete testDRow = new SQLDelete();
		String SQLPathTest = "table1";
		testDRow.deleteRowFromSQLTable(1, SQLPathTest);
		
		// Check delete row is no longer: 'Number of continents?','Seven'
		SQLRead ReadDRow = new SQLRead();
		
		assertNotEquals(ReadDRow.readAllQuestionsFromSQL(SQLPathTest).get(1), "Number of continents?");
		assertNotEquals(ReadDRow.readAllAnswersFromSQL(SQLPathTest).get(1), "Seven");
		
		// New row data should be: 'Author of Romeo and Juliet?, William Shakespeare' 
		assertEquals(ReadDRow.readAllQuestionsFromSQL(SQLPathTest).get(1), "Author of Romeo and Juliet?");
		assertEquals(ReadDRow.readAllAnswersFromSQL(SQLPathTest).get(1), "William Shakespeare");
	
	}
	
	@Test
	void checkDeleteRowFromSQLTable() throws SQLException {
		SQLDelete testDRow = new SQLDelete();
		String SQLPathTest = "table2";
		testDRow.deleteRowFromSQLTable(1, SQLPathTest);
		
		// Check SQL table row count. Default is 7, after row deletion is now 6'
		SQLRead ReadDRow = new SQLRead();
		assertEquals(ReadDRow.readAllAnswersFromSQL(SQLPathTest).size(), 6);
	}
	
}
