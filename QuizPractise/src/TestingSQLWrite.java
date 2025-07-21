import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
/**
 * JUnit test class for comprehensive testing of all methods within the SQLRead class.
 */

class TestingSQLWrite {

	@BeforeAll
	static void creatingTestDatabase() throws Exception {
	    String createTableStmt1 = "CREATE TABLE table1 (table1_ID INT(11) PRIMARY KEY AUTO_INCREMENT,"
	            + "table1_Q VARCHAR(200) NOT NULL,"
	            + "table1_A VARCHAR(50) NOT NULL);";

	    String createTableStmt2 = "CREATE TABLE table2 (table2_ID INT(11) PRIMARY KEY AUTO_INCREMENT,"
	            + "table2_Q VARCHAR(200) NOT NULL,"
	            + "table2_A VARCHAR(50) NOT NULL);";
	    
	    String createTestTable = "CREATE TABLE testHighscore (testHighscore_ID INT(11) PRIMARY KEY AUTO_INCREMENT,"
	    		+ "testHighscore_Name VARCHAR(200) NOT NULL,"
	    		+ "testHighscore_Number INT(11) NOT NULL);";

	    String insertDataStmt1 = "INSERT INTO table1 (table1_Q, table1_A) VALUES "
	            + "('Capital of France?','Paris'),"
	            + "('Number of continents?','Seven'),"
	            + "('Author of Romeo and Juliet?','William Shakespeare'),"
	            + "('Largest mammal?','Blue Whale'),"
	            + "('Year Titanic sank?','1912'),"
	            + "('Capital of Australia?','Canberra'),"
	            + "('Planets in our solar system?','Eight');";

	    String insertDataStmt2 = "INSERT INTO table2 (table2_Q, table2_A) VALUES "
	            + "('Capital of Japan?','Tokyo'),"
	            + "('Painter of Mona Lisa?','Leonardo da Vinci'),"
	            + "('Currency of Brazil?','Brazilian Real'),"
	            + "('Red Planet?','Mars'),"
	            + "('Author of Harry Potter series?','J.K. Rowling'),"
	            + "('Largest ocean on Earth?','Pacific Ocean'),"
	            + "('Year World War II ended?','1945');";
	    
	    String insertDataStmt3 = "INSERT INTO testhighscore (testhighscore_Name, testhighscore_Number) VALUES"
	            + "('Steve Bull', '70'),"
	            + "('Don Goodman', '60'),"
	            + "('Billy Wright', '50'),"
	            + "('Tax Man', '45'),"
	            + "('Coke Zero', '45'),"
	            + "('Robbie Keane', '40'),"
	            + "('Orange Juice', '35'),"
	            + "('Joe Bloggs', '30'),"
	            + "('Luke Skywalker', '30'),"
	            + "('Darth Vader', '20');";

	    try {
	        // Creating the tables
	        Connection connection = DBConnector.getConnection(); // connect to database
	        PreparedStatement pstmt1 = connection.prepareStatement(createTableStmt1);
	        pstmt1.executeUpdate();
	        pstmt1.close();

	        PreparedStatement pstmt2 = connection.prepareStatement(createTableStmt2);
	        pstmt2.executeUpdate();
	        pstmt2.close();
	        
	        PreparedStatement pstmt3 = connection.prepareStatement(createTestTable);
	        pstmt3.executeUpdate();
	        pstmt3.close();

	        PreparedStatement pstmt4 = connection.prepareStatement(insertDataStmt1);
	        pstmt4.executeUpdate();
	        pstmt4.close();

	        PreparedStatement pstmt5 = connection.prepareStatement(insertDataStmt2);
	        pstmt5.executeUpdate();
	        pstmt5.close();
	        
	        PreparedStatement pstmt6 = connection.prepareStatement(insertDataStmt3);
	        pstmt6.executeUpdate();
	        pstmt6.close();

	        connection.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@AfterAll
	static void deletingDatabase() throws Exception {
	    //Deleting database after tests
	    String dropTablesStmt = "DROP TABLE table1, table2, testhighscore;"; 
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
	 * Tests addQandASingleSQLTable method in the SQLwrite class.
	 * Includes two tests cases
	 */
	@Test
	void testAddQandASingleSQLTable() {
		//Testing to see whether our question and answer has been added. Then checking last row (7).
		String testQ = "This is your test question?";
		String testA = "Test Answer";
		String testTable = "table1";
		SQLWrite testingSQLW = new SQLWrite();
		
		// Writing question and answer to data base.
		testingSQLW.addQandASingleSQLTable(testQ, testA, testTable);
		//Using a method from Read to see if entered data matches.
		SQLRead readSingle = new SQLRead();
		assertEquals(readSingle.readAllQuestionsFromSQL(testTable).get(7), testQ);
		assertEquals(readSingle.readAllAnswersFromSQL(testTable).get(7), testA);
		
		//Deleting data that's just been wrote.
		try {
            String deleteQuery = "DELETE FROM table1 ORDER BY table1_ID DESC LIMIT 1;";

            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

                // Execute the delete statement
                preparedStatement.executeUpdate();
                System.out.println("Last row deleted from table1.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	@Test
    void checkAddQandASingleSQLTable() {
		//check correct size of the table. Table populated with 7 rows of questions/answers as default.
		String testQ = "This is your test question Checking size?";
		String testA = "Test Answer Checking size";
		String testTable = "table1";
		SQLWrite testingSQLW = new SQLWrite();
		
		// Writing question and answer to data base.
		testingSQLW.addQandASingleSQLTable(testQ, testA, testTable);

        
        //Check to see if correct amount of questions/answers added, + header.
        SQLRead readSingle = new SQLRead();
		assertEquals(readSingle.readAllAnswersFromSQL(testTable).size(), 8); 
		
		//Deleting data that's just been wrote.
		try {
            String deleteQuery = "DELETE FROM table1 ORDER BY table1_ID DESC LIMIT 1;";

            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

                // Execute the delete statement
                preparedStatement.executeUpdate();
                System.out.println("Last row deleted from table1.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }  
    }
	
	/**
	* Tests getLastIDFromTable method in the SQLwrite class.
	* Includes two tests cases
	*/
	@Test
    void checkGetLastIDFromTable() {
		//Checking whether last ID is 7. Preloaded table only has 7 rows.
		String testT = "table2";
		SQLWrite testID = new SQLWrite();
		int lastID = testID.getLastIDFromTable(testT);
		assertEquals(lastID, 7);
		
    }
	
	@Test
    void intGetLastIDFromTable() {
		//Checking whether last ID is 7. Preloaded table only has 7 rows.
		String testT = "table2";
		SQLWrite testID = new SQLWrite();
		int lastID = testID.getLastIDFromTable(testT);
		
		// Check if lastID is an int
		assertTrue(Integer.class.isInstance(lastID));
    }
	
	/**
	* Tests addQandAMultipleSQLTable method in the SQLwrite class.
	* Includes two tests cases
	*/
	@Test
    void checkAddQandAMultipleSQLTable() {
		//Checking if LinkedLists have been loaded into SQL table.
		LinkedList<String> questions = new LinkedList<>();
        LinkedList<String> answers = new LinkedList<>();
        questions.add("This is test question 1");
        questions.add("This is test question 2");
        questions.add("This is test question 3");
        answers.add("Answer 1 right here.");
        answers.add("Answer 2 right here.");
        answers.add("Answer 3 right here.");
		
		String testT = "table2";
		SQLWrite multiWrite = new SQLWrite();
		multiWrite.addQandAMultipleSQLTable(questions, answers, testT);
	
		
		// Check if specific test question have been added and in correct position.
		SQLRead readSingle = new SQLRead();
		assertEquals(readSingle.readAllQuestionsFromSQL(testT).get(7), "This is test question 1");
		assertEquals(readSingle.readAllQuestionsFromSQL(testT).get(8), "This is test question 2");
		assertEquals(readSingle.readAllQuestionsFromSQL(testT).get(9), "This is test question 3");
		assertEquals(readSingle.readAllAnswersFromSQL(testT).get(7), "Answer 1 right here.");
		assertEquals(readSingle.readAllAnswersFromSQL(testT).get(8), "Answer 2 right here.");
		assertEquals(readSingle.readAllAnswersFromSQL(testT).get(9), "Answer 3 right here.");
		
		//Deleting data that's just been wrote.
		try {
            String deleteQuery = "DELETE FROM table2 ORDER BY table2_ID DESC LIMIT 1;";

            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

                preparedStatement.executeUpdate();
                System.out.println("Last row deleted from table2.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
				
    }
	
	@Test
    void checkSizeAddQandAMultipleSQLTable() {
		//Checking if LinkedLists have been loaded into SQL table.
		LinkedList<String> questions = new LinkedList<>();
        LinkedList<String> answers = new LinkedList<>();
        questions.add("This is test question 1");
        questions.add("This is test question 2");
        questions.add("This is test question 3");
        answers.add("Answer 1 right here.");
        answers.add("Answer 2 right here.");
        answers.add("Answer 3 right here.");
		
		String testT = "table1";
		SQLWrite multiWrite = new SQLWrite();
		multiWrite.addQandAMultipleSQLTable(questions, answers, testT);
	
		
		// Check if specific test question/answers have been added by row count.
		SQLRead readSingle = new SQLRead();
		assertEquals(readSingle.readAllAnswersFromSQL(testT).size(), 10); 
		assertEquals(readSingle.readAllQuestionsFromSQL(testT).size(), 10);
		
		//Deleting data that's just been wrote.
		try {
            String deleteQuery = "DELETE FROM table1 ORDER BY table1_ID DESC LIMIT 1;";

            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

                preparedStatement.executeUpdate();
                System.out.println("Last row deleted from table1.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }		
    }
	
	/**
	* Tests writeHighscoresSQL method in the SQLwrite class.
	* Includes two tests cases
	 * @throws SQLException 
	*/
	@Test
    void checkwriteHighscoresSQL() throws SQLException {
		//Checking if LinkedLists have been loaded into SQL table.
		LinkedList<Highscore> testingHighscore = new LinkedList<>();
        SQLWrite testHigh = new SQLWrite();
        String testSQL = "testHighscore";
        
        Highscore highscore = new Highscore(11, "Testing Name", 90);
        testingHighscore.add(highscore);
        
        testHigh.writeHighscoresSQL(testingHighscore, testSQL);
        
        // Checking if highscore now has 9 rows.
        SQLRead readHighscore = new SQLRead();
		assertEquals(readHighscore.readHighscoresSQL(testSQL).size(), 12); 
        
        
    }
	
	@Test
    void lastRowWriteHighscoresSQL() throws SQLException {
		//Checking if LinkedLists have been loaded into SQL table.
		LinkedList<Highscore> testingHighscore = new LinkedList<>();
        SQLWrite testHigh = new SQLWrite();
        String testSQL = "testHighscore";
        Highscore highscore = new Highscore(12, "Testing Name2", 5);
        testingHighscore.add(highscore);
        
        testHigh.writeHighscoresSQL(testingHighscore, testSQL);
        
        // Checks to see if highscore is empty.
        SQLRead readHighscore = new SQLRead();
        assertNotNull(readHighscore.readHighscoresSQL(testSQL)); 
        
       
    }	

}
