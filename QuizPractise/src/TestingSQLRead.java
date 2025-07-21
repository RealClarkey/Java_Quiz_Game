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

class TestingSQLRead {
	

	@BeforeAll
	static void creatingTestDatabase() throws Exception {
	    // Statements for creating test tables in the database to work with, will be dropped later
	    String createTable1 = "CREATE TABLE table1 (ID INT(11) PRIMARY KEY AUTO_INCREMENT,"
	            + "Question VARCHAR(200) NOT NULL,"
	            + "Answer VARCHAR(50) NOT NULL);";

	    String createTable2 = "CREATE TABLE table2 (ID INT(11) PRIMARY KEY AUTO_INCREMENT,"
	            + "Question VARCHAR(200) NOT NULL,"
	            + "Answer VARCHAR(50) NOT NULL);";

	    String createTableStmtHighscore = "CREATE TABLE testhighscore (testhighscore_ID INT(11) PRIMARY KEY AUTO_INCREMENT,"
	            + "testhighscore_Name VARCHAR(200) NOT NULL,"
	            + "testhighscore_Number INT(11) NOT NULL);";

	    String insertValues1 = "INSERT INTO table1 (Question, Answer) VALUES "
	            + "('Capital of France?','Paris'),"
	            + "('Number of continents?','Seven'),"
	            + "('Author of Romeo and Juliet?','William Shakespeare'),"
	            + "('Largest mammal?','Blue Whale'),"
	            + "('Year Titanic sank?','1912'),"
	            + "('Capital of Australia?','Canberra'),"
	            + "('Planets in our solar system?','Eight');";

	    String insertValues2 = "INSERT INTO table2 (Question, Answer) VALUES "
	            + "('Capital of Japan?','Tokyo'),"
	            + "('Painter of Mona Lisa?','Leonardo da Vinci'),"
	            + "('Currency of Brazil?','Brazilian Real'),"
	            + "('Red Planet?','Mars'),"
	            + "('Author of Harry Potter series?','J.K. Rowling'),"
	            + "('Largest ocean on Earth?','Pacific Ocean'),"
	            + "('Year World War II ended?','1945');";

	    String insertHighscores = "INSERT INTO testhighscore (testhighscore_Name, testhighscore_Number) VALUES"
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
	        PreparedStatement pstmt1 = connection.prepareStatement(createTable1);
	        pstmt1.executeUpdate();
	        pstmt1.close();

	        PreparedStatement pstmt2 = connection.prepareStatement(createTable2);
	        pstmt2.executeUpdate();
	        pstmt2.close();


	        PreparedStatement pstmtHighscore = connection.prepareStatement(createTableStmtHighscore);
	        pstmtHighscore.executeUpdate();
	        pstmtHighscore.close();

	        // Inserting data into tables.
	        PreparedStatement pstmt4 = connection.prepareStatement(insertValues1);
	        pstmt4.executeUpdate();
	        pstmt4.close();

	        PreparedStatement pstmt5 = connection.prepareStatement(insertValues2);
	        pstmt5.executeUpdate();
	        pstmt5.close();

	       
	        PreparedStatement pstmt7 = connection.prepareStatement(insertHighscores);
	        pstmt7.executeUpdate();
	        pstmt7.close();

	        connection.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@AfterAll
	static void deletingDatabase() throws Exception {
	    //Removing tables from server.
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
	 * Tests the readFromDatabaseQs method in the SQLRead class.
	 * Populates a 2D LinkedList of questions from six SQL tables.
	 * Includes three test cases.
	 */
	@Test
	void testQuestions2dLinkedList() {
		//Testing if method has loaded correct data in 2D questions LinkedList, using a random question.
		LinkedList<LinkedList<String>> questions = new LinkedList<>();
		String[] databaseTableNames = {"Food", "Geography", "History", "Movie", "Science", "Sport",};
		SQLRead dbReader = new SQLRead();
		questions.addAll(dbReader.readFromDatabaseQs(databaseTableNames));
		assertEquals(questions.get(5).get(4), "How many points is a bullseye worth in darts?");
		assertEquals(questions.get(3).get(2), "Which city is Home Alone 2 set in?");
		assertEquals(questions.get(2).get(7), "Which car company produced the Model T?");
		
		assertNotNull(questions);
		
	}
	
	@Test
	void testNullQuestions2dLinkedList() {
		//Testing if 2d LinkedLists aren't empty.
		LinkedList<LinkedList<String>> questions = new LinkedList<>();
		String[] databaseTableNames = {"Food", "Geography", "History", "Movie", "Science", "Sport",};
		SQLRead dbReader = new SQLRead();
		questions.addAll(dbReader.readFromDatabaseQs(databaseTableNames));
		
		assertNotNull(questions);
	}
	
	@Test
	public void isStringQuestions2dLinkedList() {
		// Test whether the answer returned is a String type.
		LinkedList<LinkedList<String>> questions = new LinkedList<>();
		String[] databaseTableNames = {"Food", "Geography", "History", "Movie", "Science", "Sport",};
		SQLRead dbReader = new SQLRead();
		questions.addAll(dbReader.readFromDatabaseQs(databaseTableNames));
		String value = questions.get(5).get(4);
		String value2 = questions.get(3).get(2);
		
		// Chosing two random questions to match String.
		assertTrue(value instanceof String, "Expected value to be a String");
		assertTrue(value2 instanceof String, "Expected value to be a String");
	}
	
	/**
	 * Tests the readFromDatabaseAs method in the SQLRead class.
	 * Populates a 2D LinkedList of answers from six SQL tables.
	 * Includes three test cases.
	 */
	@Test
	void testanswers2dLinkedList() {
		//Testing if method has loaded correct data in 2D answers LinkedList, using a random answer.
		LinkedList<LinkedList<String>> answers = new LinkedList<>();
		String[] databaseTableNames = {"Food", "Geography", "History", "Movie", "Science", "Sport",};
		SQLRead dbReader = new SQLRead();
		answers.addAll(dbReader.readFromDatabaseAs(databaseTableNames));
		assertEquals(answers.get(3).get(2), "New York");
		assertEquals(answers.get(4).get(8), "Hydrogen");
		assertEquals(answers.get(1).get(6), "Toronto");
		
		assertNotNull(answers);
		
	}
	
	@Test
	void testNullanswers2dLinkedList() {
		//Testing if 2d LinkedLists aren't empty.
		LinkedList<LinkedList<String>> answers = new LinkedList<>();
		String[] databaseTableNames = {"Food", "Geography", "History", "Movie", "Science", "Sport",};
		SQLRead dbReader = new SQLRead();
		answers.addAll(dbReader.readFromDatabaseAs(databaseTableNames));
		
		assertNotNull(answers);
	}
	
	@Test
	public void isStringanswers2dLinkedList() {
		// Test whether the answer returned is a String type.
		LinkedList<LinkedList<String>> answers = new LinkedList<>();
		String[] databaseTableNames = {"Food", "Geography", "History", "Movie", "Science", "Sport",};
		SQLRead dbReader = new SQLRead();
		answers.addAll(dbReader.readFromDatabaseAs(databaseTableNames));
		String value = answers.get(1).get(7);
		String value2 = answers.get(4).get(1);
		
		// Chosing two random answers to match String.
		assertTrue(value instanceof String, "Expected value to be a String");
		assertTrue(value2 instanceof String, "Expected value to be a String");
	}
	
	/**
	 * Tests the readAllQuestionsFromSQL method in the SQLRead class.
	 * Populates a LinkedList of questions from a specific SQL tables.
	 * Includes three test cases.
	 */
	@Test
	public void correctSizeReadAllQuestionsFromSQL() {
		// Test whether the correct size has been read in the SQL table
		String sqlTest = "table1";
		// Test to see if the LinkedList has been populated with 7 questions from table.
		SQLRead readSingle = new SQLRead();
		assertEquals(readSingle.readAllQuestionsFromSQL(sqlTest).size(), 7); 
	}
	
	@Test
	public void correctReadAllQuestionsFromSQL() {
		// Test to see if we get expected results from 3 random questions.
		String sqlTest = "table2";
		SQLRead readSingle = new SQLRead();
		assertEquals(readSingle.readAllQuestionsFromSQL(sqlTest).get(2), "Currency of Brazil?");
		assertEquals(readSingle.readAllQuestionsFromSQL(sqlTest).get(4), "Author of Harry Potter series?");
		assertEquals(readSingle.readAllQuestionsFromSQL(sqlTest).get(6), "Year World War II ended?");
	}
	
	@Test
	public void notNullReadAllQuestionsFromSQL() {
		// Test whether the LinkedList isn't Null.
		String sqlTest = "table2";
		SQLRead readSingle = new SQLRead();
		assertNotNull(readSingle.readAllQuestionsFromSQL(sqlTest));
	}
	
	/**
	 * Tests the readAllanswersFromSQL method in the SQLRead class.
	 * Populates a LinkedList of answers from a specific SQL tables.
	 * Includes three test cases.
	 */
	@Test
	public void correctSizeReadAllanswersFromSQL() {
		// Test whether the correct size has been read in the SQL table
		String sqlTest = "table1";
		// Test to see if the LinkedList has been populated with 7 answers from table.
		SQLRead readSingle = new SQLRead();
		assertEquals(readSingle.readAllAnswersFromSQL(sqlTest).size(), 7); 
	}
	
	@Test
	public void correctReadAllanswersFromSQL() {
		// Test to see if we get expected results from 3 random answers.
		String sqlTest = "table2";
		SQLRead readSingle = new SQLRead();
		assertEquals(readSingle.readAllAnswersFromSQL(sqlTest).get(1), "Leonardo da Vinci");
		assertEquals(readSingle.readAllAnswersFromSQL(sqlTest).get(2), "Brazilian Real");
		assertEquals(readSingle.readAllAnswersFromSQL(sqlTest).get(5), "Pacific Ocean");
	}
	
	@Test
	public void notNullReadAllanswersFromSQL() {
		// Test whether the LinkedList isn't Null.
		String sqlTest = "table2";
		SQLRead readSingle = new SQLRead();
		assertNotNull(readSingle.readAllAnswersFromSQL(sqlTest));
	}
	
	/**
	 * Tests the readHighscoresSQL method in the Read class.
	 * Populates a LinkedList of highscores from 1 CSV.
	 * Includes two test cases.
	 */
	@Test
	public void notNullHighscoreSQL() {
		// Test whether the LinkedList isn't Null.
		String sqlTest = "testhighscore";
		SQLRead readHighscore = new SQLRead();
		
		assertNotNull(readHighscore.readHighscoresSQL(sqlTest));
	}
	
	@Test
	void correctSizeHighscoreSQL() {
		// Test to see if the LinkedList has been populated with 10 questions from the CSV.
		String sqlTest = "testhighscore";
		SQLRead readHighscore = new SQLRead();
		assertEquals(readHighscore.readHighscoresSQL(sqlTest).size(), 10); 
	}

}
