import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test class for comprehensive testing of all methods within the Highscore class.
 */
class TestingHighscore {

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
	* Tests addHighscore method in the Highscore class.
	* Includes two tests cases 
	*/
	@Test
    public void testAddHighscore() {
		//Adding one highscore to linkedlist.
        LinkedList<Highscore> highscores = new LinkedList<>();
        Highscore.addHighscore(highscores, "Player1", 100);
        
        //Proving individual highsccore has been added.
        assertEquals(1, highscores.size());
        assertEquals("Player1", highscores.get(0).getName());
        assertEquals(100, highscores.get(0).getNumber());
    }

    @Test
    public void testAddMultipleHighscores() {
    	//Adding multiple highscores to linkedlist.
        LinkedList<Highscore> highscores = new LinkedList<>();
        Highscore.addHighscore(highscores, "Player1", 100);
        Highscore.addHighscore(highscores, "Player2", 150);
        Highscore.addHighscore(highscores, "Player3", 120);
        
        //Proving multiple highscores been added and in correct order.
        assertEquals(3, highscores.size());
        assertEquals("Player2", highscores.get(0).getName()); // Player2 has the highest score
        assertEquals("Player3", highscores.get(1).getName()); // Player3 has the second-highest score
        assertEquals("Player1", highscores.get(2).getName()); // Player1 has the third-highest score
    }

}
