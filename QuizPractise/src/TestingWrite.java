import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test class for comprehensive testing of all methods within the Write class.
 * This testing class will create test csvs. Tests will be created to check if correct data has been wrote.
 * Manual checking of CSVs is also recommended once this class has been run.
 */

class TestingWrite {
	
	String csvPath = "testing\\test.csv";
	String csvPath2 = "testing\\test2.csv";
	
	private void deleteFile(String filePath) {
        try {
            Files.deleteIfExists(Path.of(filePath));
        } catch (IOException e) {
            System.err.println("Failed to delete test file: " + e.getMessage());
        }
    }

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
	 * Tests addQandASingleToCSV method in the write class.
	 * Also includes getLastLineFromCSV.
	 * Includes two tests cases
	 */
	@Test
    void testAddQandASingleToCSV() throws IOException {
		// Checks if questions and answers are being wrote to csv file, using getLastLineFromCSV method to confirm too.
        Write write = new Write();
        String question = "This is a Test Question";
        String answer = "Test Answer Here";

        // Delete the test file if it already exists.
        File file = new File(csvPath);
        if (file.exists()) {
            file.delete();
        }
        
        Read lastLineRead = new Read(csvPath);
        
         
        // Call the method.
        write.addQandASingleToCSV(question, answer, csvPath);

        // Verify that the file is created and contains the expected content.
        assertTrue(file.exists());
        
        String lastLine = lastLineRead.getLastLineFromCSV(csvPath);
        assertEquals("1,This is a Test Question,Test Answer Here", lastLine);
         
    }
	
	/**
	 * Tests addQandAMultipleToCSV method in the write class.
	 * Also includes getLastLineFromCSV.
	 * Includes two tests cases.
	 */
	@Test
    void testAddQandAMultipleToCSV() throws IOException {
		// Testing correct data has been wrote to the CSV.
        Write write = new Write();
        LinkedList<String> questions = new LinkedList<>();
        LinkedList<String> answers = new LinkedList<>();
        questions.add("Question1");
        questions.add("Question2");
        answers.add("Answer1");
        answers.add("Answer2");

        // Deletes the test file if it already exists
        File file = new File(csvPath2);
        if (file.exists()) {
            file.delete(); // this will show up as red on unit test if the file doesn't already exist.
        }
        
        Read lastLineRead = new Read(csvPath2);

        write.addQandAMultipleToCSV(questions, answers, csvPath2);

        // Verify that the file has been created.
        assertTrue(file.exists()); 
        
		//Check last line of the created data.
        String lastLine = lastLineRead.getLastLineFromCSV(csvPath2);
        assertEquals("2,Question2,Answer2", lastLine);
        
    }
	
	@Test
    void checkSizeAddQandAMultipleToCSV() throws IOException {
		//check correct size of the CSV file.
        Write write = new Write();
        LinkedList<String> questions = new LinkedList<>();
        LinkedList<String> answers = new LinkedList<>();
        questions.add("Question1");
        questions.add("Question2");
        answers.add("Answer1");
        answers.add("Answer2");

        // Delete the test file if it already exists
        File file = new File(csvPath2);
        if (file.exists()) {
            file.delete(); // this will show up as red on unit test if the file doesn't already exist.
        }

        // Call the method.
        write.addQandAMultipleToCSV(questions, answers, csvPath2);
        
        //Check to see if correct amount of questions/answers added, + header.
        Read readSingle = new Read(csvPath2);
		assertEquals(readSingle.readAllAnswersFromCSV().size(), 2); 
		assertEquals(readSingle.readAllQuestionsFromCSV().size(), 2);
        
        
    }
	
	/**
	 * Tests writeHighscores method in the write class.
	 * Includes two tests cases.
	 */
	@Test
    public void testWriteHighscoresEmptyHighscores() {
		//check to see if correct data has been wrote to CSV.
        String filePath = "testing//testWriteHighscoresEmpty.csv";
        LinkedList<Highscore> highscores = new LinkedList<>();

        try {
            Write yourClass = new Write();
            yourClass.writeHighscores(filePath, highscores);

            // Verify file content
            String fileContent = Files.readString(Path.of(filePath));
            assertTrue(fileContent.contains("Highscore_ID,Highscore_Name,Highscore_Number"));
        } catch (IOException e) {
            fail("Unexpected exception: " + e.getMessage());
        } finally {
            // Clean up: delete the test file
            deleteFile(filePath);
        }
    }
	
	@Test
    public void writeToWriteHighScores() {
		// check to see if the correct data has been wrote to the CSV.
        String filePath = "testing//highscore.csv";
        LinkedList<Highscore> testingHighscore = new LinkedList<>();
        
        
        Highscore highscore = new Highscore(1, "Leigh", 80);
        testingHighscore.add(highscore);

        try {
            Write yourClass = new Write();
            yourClass.writeHighscores(filePath, testingHighscore);

            // Verify file content
            String fileContent = Files.readString(Path.of(filePath));
            assertTrue(fileContent.contains("Highscore_ID,Highscore_Name,Highscore_Number"));
        } catch (IOException e) {
            fail("Unexpected exception: " + e.getMessage());
        } finally {
            // Clean up: delete the test file
            deleteFile(filePath);
        }
    } 
	
}
