import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test class for comprehensive testing of all methods within the BoardPosition class.
 */

class TestingBoardPositions {


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
	* Tests testGetPositionX method in the Highscore class.
	* Includes two tests cases 
	*/
	@Test
    public void testGetPositionX_validIndex() {
        BoardPositions boardPositions = new BoardPositions();
        int index = 3; 

        int expectedX = 615; // The expected X coordinate for index 3
        int actualX = boardPositions.getPositionX(index);

        assertEquals(expectedX, actualX);
    }

    @Test
    public void testGetPositionX_invalidIndex() {
        BoardPositions boardPositions = new BoardPositions();
        int index = -1; 

        int defaultX = -1; // The default X coordinate for an invalid index
        int actualX = boardPositions.getPositionX(index);

        assertEquals(defaultX, actualX);
    }
    
    /**
	* Tests testGetPositionY method in the Highscore class.
	* Includes two tests cases 
	*/
    @Test
    public void testGetPositionY_validIndex() {
        BoardPositions boardPositions = new BoardPositions();
        int index = 8; 

        int expectedY = 523; // The expected Y coordinate for index 8
        int actualY = boardPositions.getPositionY(index);

        assertEquals(expectedY, actualY);
    }

    @Test
    public void testGetPositionY_invalidIndex() {
        BoardPositions boardPositions = new BoardPositions();
        int index = 25; 

        int defaultY = -1; // The default Y coordinate for an invalid index
        int actualY = boardPositions.getPositionY(index);

        assertEquals(defaultY, actualY);
    }
}
