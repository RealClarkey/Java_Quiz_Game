import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestingDelete {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//Creating a test CSV
	    
		String csvPath = "testing/deleteCSVtest.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvPath))) {
            // Write the new data to the CSV file, replacing existing content
            writer.write("Test_ID, Test_Q, Test_A\n");
            writer.write("1, How old is the sun?, 4.6 billion years\n");
            writer.write("2, What is the capital of Japan?, Tokyo\n");
            writer.write("3, Who wrote 'Romeo and Juliet'?, William Shakespeare\n");
            writer.write("4, What is the largest planet in our solar system?, Jupiter\n");
            writer.write("5, In which year did World War II end?, 1945\n");
            writer.write("6, What is the chemical symbol for water?, H2O\n");

        } 
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		// Delete the test file after tests.
		String filePath = "testing\\deleteCSVtest.csv";
	    Files.deleteIfExists(Path.of(filePath));
	    
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	/**
	 * Tests the functionality of the deleteRowFromCSV method in the Delete class.
	 * 
	 * This method deletes a specified row identified by Row ID from a CSV file. After deletion,
	 * it reorganizes the remaining rows by updating their Row IDs.
	 * 
	 * Below are two tests: one verifying the basic functionality, and the other ensuring
	 * the correct rearrangement of Row IDs after deletion.
	 */
	@Test
	void testDeleteRow() {
		// Test to see if give row is deleted by id. Row ID 2 = What is the capital of Japan?.
		int rowToDelete = 2;
		String csvPath = "testing/deleteCSVtest.csv";
		Delete testD = new Delete();
		testD.deleteRowFromCSV(rowToDelete, csvPath);
		
		Read readClassMultiple = new Read(csvPath);
		assertNotEquals(readClassMultiple.readAllQuestionsFromCSV().get(rowToDelete), "What is the capital of Japan?");		
	}
	
	@Test
	void testDeleteRowFromCSV() throws FileNotFoundException, IOException {
		// Check if the CSV has updated with the new Row ID once a row ID has been deleted.
        int rowToCheck = 2; 
        String csvPath = "testing/deleteCSVtest.csv";

        Delete testD = new Delete();
        testD.deleteRowFromCSV(rowToCheck, csvPath);

        // Verifying the content of the given Row ID
        try (BufferedReader reader = new BufferedReader(new FileReader(csvPath))) {
            List<String> lines = Files.readAllLines(Paths.get(csvPath));
            assertTrue(rowToCheck < lines.size()); 

            String[] parts = lines.get(rowToCheck).split(",");
            int id = Integer.parseInt(parts[0].trim());

            assertEquals(id, rowToCheck);
        } 
    }
	

}
