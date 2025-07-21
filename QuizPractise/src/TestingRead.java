import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test class for comprehensive testing of all methods within the Read class.
 */

class TestingRead {
	
	static LinkedList<String> multipleCSVPaths;
	static {
		multipleCSVPaths = new LinkedList<>();
		multipleCSVPaths.add("data\\food.csv");
		multipleCSVPaths.add("data\\geography.csv");
		multipleCSVPaths.add("data\\history.csv");
		multipleCSVPaths.add("data\\movie.csv");
		multipleCSVPaths.add("data\\science.csv");
		multipleCSVPaths.add("data\\sport.csv");
    }
	
	String csvPathTest = "data\\food.csv";
	String csvHighscore = "highscore\\highscore.csv";
	String csvPathBigTest = "testing\\big.csv";
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	
	/**
	 * Tests the readQuestionsFromMultipleCSVs method in the Read class.
	 * Populates a LinkedList of questions from six CSV files.
	 * Includes four test cases.
	 */
	@Test
	void correctSizeQuestionsMultipleCSVs() {
		// Test to see if the LinkedList has been populated with 6 CSVs.
		Read readClassMultiple = new Read(multipleCSVPaths);
		assertEquals(readClassMultiple.readQuestionsFromMultipleCSVs().size(), 6); 
	}
	
	@Test
	public void correctQuestionsMultipleCSVs() {
		// Test to see if three random questions match given String.
		Read readClassMultiple = new Read(multipleCSVPaths);
		assertEquals(readClassMultiple.readQuestionsFromMultipleCSVs().get(5).get(4), "How many points is a bullseye worth in darts?");
		assertEquals(readClassMultiple.readQuestionsFromMultipleCSVs().get(3).get(2), "Which city is Home Alone 2 set in?");
		assertEquals(readClassMultiple.readQuestionsFromMultipleCSVs().get(2).get(7), "Which car company produced the Model T?");
	}
	
	@Test
	public void notNullQuestionsMultipleCSVs() {
		// Test whether the LinkedList isn't Null.
		Read readMultiple = new Read(multipleCSVPaths);
		assertNotNull(readMultiple.readQuestionsFromMultipleCSVs());
	}
	
	@Test
	public void isStringQuestionsMultipleCSVs() {
		// Test whether the answer returned is a String type.
		Read readMultiple = new Read(multipleCSVPaths);
		
		String value = readMultiple.readQuestionsFromMultipleCSVs().get(3).get(2);
		String value2 = readMultiple.readQuestionsFromMultipleCSVs().get(5).get(9);
		
		// Chosing two random questions to match String.
		assertTrue(value instanceof String, "Expected value to be a String");
		assertTrue(value2 instanceof String, "Expected value to be a String");

	}	
	
	/**
	 * Tests the readAnswersFromMultipleCSVs method in the Read class.
	 * Populates a LinkedList of questions from six CSV files.
	 * Includes four test cases.
	 */
	@Test
	void correctSizeAnswersMultipleCSVs() {
		// Test to see if the LinkedList has been populated with 6 CSVs.
		Read readMultiple = new Read(multipleCSVPaths);
		assertEquals(readMultiple.readAnswersFromMultipleCSVs().size(), 6); 
	}
	
	@Test
	public void correctAnswersMultipleCSVs() {
		// Test to see if we get expected results from 3 random answers.
		Read readMultiple = new Read(multipleCSVPaths);
		assertEquals(readMultiple.readAnswersFromMultipleCSVs().get(3).get(2), "New York");
		assertEquals(readMultiple.readAnswersFromMultipleCSVs().get(4).get(8), "Hydrogen");
		assertEquals(readMultiple.readAnswersFromMultipleCSVs().get(1).get(7), "New Zealand");

	}
	
	@Test
	public void notNullAnswersMultipleCSVs() {
		// Test whether the LinkedList isn't Null.
		Read readMultiple = new Read(multipleCSVPaths);
		assertNotNull(readMultiple.readAnswersFromMultipleCSVs());
	}
	
	@Test
	public void isStringAnswersMultipleCSVs() {
		// Test whether the answer returned is a String type.
		Read readMultiple = new Read(multipleCSVPaths);
		
		String value = readMultiple.readAnswersFromMultipleCSVs().get(1).get(7);
		String value2 = readMultiple.readAnswersFromMultipleCSVs().get(4).get(1);

		// Chosing two random questions to match String.
		assertTrue(value instanceof String, "Expected value to be a String");
		assertTrue(value2 instanceof String, "Expected value to be a String");
	}
	
	/**
	 * Tests the readAllQuestionsFromCSV method in the Read class.
	 * Populates a LinkedList of questions from one CSV files.
	 * Includes four test cases.
	 */
	@Test
	void correctSizeQuestionsSingleCSVs() {
		// Test to see if the LinkedList has been populated with 10 questions from the CSV.
		Read readSingle = new Read(csvPathTest);
		assertEquals(readSingle.readAllQuestionsFromCSV().size(), 10); 
	}
	
	@Test
	public void correctQuestionsSingleCSVs() {
		// Test to see if we get expected results from 3 random questions.
		Read readSingle = new Read(csvPathTest);		
		assertEquals(readSingle.readAllQuestionsFromCSV().get(2), "What is the main ingredient in a classic omelette?");
		assertEquals(readSingle.readAllQuestionsFromCSV().get(4), "What type of cheese is used on a traditional Margherita Pizza?");
		assertEquals(readSingle.readAllQuestionsFromCSV().get(7), "Which beverage is made from fermented grapes?");
	}
	
	@Test
	public void notNullQuestionsSingleCSVs() {
		// Test whether the LinkedList isn't Null.
		Read readSingle = new Read(csvPathTest);
		assertNotNull(readSingle.readAllQuestionsFromCSV());
	}
	
	@Test
	public void isStringQuestionsSingleCSVs() {
		// Test whether the question returned is a String type.
		Read readSingle = new Read(csvPathTest);
		String value = readSingle.readAllQuestionsFromCSV().get(7);
		String value2 = readSingle.readAllQuestionsFromCSV().get(4);

		// Chosing two random questions to match String.
		assertTrue(value instanceof String, "Expected value to be a String");
		assertTrue(value2 instanceof String, "Expected value to be a String");
	}
	
	/**
	 * Tests the readAllAnswersFromCSV method in the Read class.
	 * Populates a LinkedList of questions from one CSV files.
	 * Includes four test cases.
	 */
	@Test
	void correctSizeAnswersSingleCSVs() {
		// Test to see if the LinkedList has been populated with 10 questions from the CSV.
		Read readSingle = new Read(csvPathTest);
		assertEquals(readSingle.readAllAnswersFromCSV().size(), 10); 
	}
	
	@Test
	public void correctAnswersSingleCSVs() {
		// Test to see if we get expected results from 3 random questions.
		Read readSingle = new Read(csvPathTest);		

		assertEquals(readSingle.readAllAnswersFromCSV().get(1), "Baguette");
		assertEquals(readSingle.readAllAnswersFromCSV().get(3), "Lamb");
		assertEquals(readSingle.readAllAnswersFromCSV().get(8), "Strawberries");
	}
	
	@Test
	public void notNullAnswersSingleCSVs() {
		// Test whether the LinkedList isn't Null.
		Read readSingle = new Read(csvPathTest);
		assertNotNull(readSingle.readAllAnswersFromCSV());
	}
	
	@Test
	public void isStringAnswersSingleCSVs() {
		// Test whether the question returned is a String type.
		Read readSingle = new Read(csvPathTest);
		String value = readSingle.readAllAnswersFromCSV().get(2);
		String value2 = readSingle.readAllAnswersFromCSV().get(5);

		// Chosing two random questions to match String.
		assertTrue(value instanceof String, "Expected value to be a String");
		assertTrue(value2 instanceof String, "Expected value to be a String");
	}
	
	/**
	 * Tests the readHighscores method in the Read class.
	 * Populates a LinkedList of highscores from 1 CSV.
	 * Includes two test cases.
	 */
	@Test
	public void notNullHighscoreCSVs() {
		// Test whether the LinkedList isn't Null.
		Read readHighscore = new Read(csvHighscore);
		assertNotNull(readHighscore.readHighscores(csvHighscore));
	}
	
	@Test
	void correctSizeHighscoreCSVs() {
		// Test to see if the LinkedList has been populated with 10 questions from the CSV.
		Read readHighscore = new Read(csvHighscore);
		assertEquals(readHighscore.readAllAnswersFromCSV().size(), 10); 
	}
	
	/**
	 * Tests getLastLineFromCSV method in Read class.
	 * Grabs the last row in any CSV and returns the data.
	 * Includes two test cases.
	 */
	@Test
	public void checkGetLastLineFromCSV() throws IOException {
		// Check if the data returned is what is expected.
		Read lastLineRead = new Read(csvPathTest);
		assertEquals(lastLineRead.getLastLineFromCSV(csvPathTest), "10,\" What is the main ingredient in the Middle Eastern dish \"\"hummus\"\"?\",Chickpeas");	
	}
	
	@Test
	public void isStrinGetLastLineFromCSV() throws IOException {
		// Check if the data returned is a String.
		Read lastLineRead = new Read(csvPathTest);	
		assertTrue(lastLineRead.getLastLineFromCSV(csvPathTest) instanceof String);		
	}
	
	/**
	 * Tests getLastIDFromCSV method in Read class.
	 * Grabs the last row in any CSV and returns the row ID data.
	 * Includes two test cases.
	 */
	@Test
	public void lastIDBigLastIDFromCSV() throws IOException {
		// Check if last rowID is 100 of a big csv test file.
		Read lastIDRead = new Read(csvPathBigTest);
		assertEquals(100, lastIDRead.getLastIDFromCSV(csvPathBigTest));	
	}
	
	@Test
	public void lastIDGetLastIDFromCSV() throws IOException {
		// Check if the last rowID is 10.
		Read lastIDRead = new Read(csvPathTest);	
		assertEquals(10, lastIDRead.getLastIDFromCSV(csvPathTest));		
	}

}


