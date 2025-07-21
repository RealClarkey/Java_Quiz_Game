import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test class for comprehensive testing of all methods within the GameEngine class.
 * The GUI will be loaded for some tests. Please enter the correct answer into the prompt.
 * The correct answer is printed to console.
 */
class TestingGameEngine {

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
	* Tests startGame method in the GameEngine class.
	* Includes two tests cases 
	*/
	@Test
    void startGameShouldInitializeGameVariables() {
		//Testing if the startGame opens GUI and sets GameInProgress to True.
        Gui gui = new Gui(null);
        GameEngine gameEngine = new GameEngine(gui);
        gameEngine.startGame(4); // Passing a dummy dice roll number for testing
        assertTrue(gameEngine.isGameInProgress()); 
    }
	
	@Test
    void startGameFalseInitializeGameVariables() {
		//Testing if the startGame does not open GUI and sets GameInProgress to True.
        Gui gui = new Gui(null);
        GameEngine gameEngine = new GameEngine(gui);
        assertFalse(gameEngine.isGameInProgress()); 
    }
	
	/**
	* Tests playTurn method in the GameEngine class.
	* Includes two tests cases 
	* YOU MUST PLAY THE GAME AND ANSWER THREE QUESTIONS CORRECTLY.
	*/
	@Test
    void testplayTurn() {
		// Testing that the correct answers and playersocre works as intended.
		Gui gui = new Gui(null);
        GameEngine gameEngine = new GameEngine(gui);
        gameEngine.startGame(4); // Start the game with a dummy dice roll number
        int initialCorrectAnswers = gameEngine.getCorrectAnswers();
        int initialPlayerScore = gameEngine.getPlayerScore();

        gameEngine.playTurn(6); // Calling playTurn with a 6 dice roll.

        assertEquals(initialCorrectAnswers + 1, gameEngine.getCorrectAnswers());
        assertEquals(initialPlayerScore + 10, gameEngine.getPlayerScore());

    }
	
	@Test
    public void testPlayTurnWithIncorrectAnswer() {
        // Testing the correct player lives works as intended.
		Gui gui = new Gui(null);
        GameEngine gameEngine = new GameEngine(gui);
        gameEngine.startGame(4); // Start the game with a dummy dice roll number
        int initialPlayerLives = 3;

        gameEngine.playTurn(6); // Calling playTurn with a 6 dice roll.
        System.out.println(gameEngine.getPlayerLives());
        assertEquals(initialPlayerLives, gameEngine.getPlayerLives());
    }
	
	/**
	* Tests checkAnswer method in the GameEngine class.
	* Includes two tests cases 
	*/
	@Test
    public void testcheckAnswer() {
        // Checks if answers is correct, regardless of case.
		Gui gui = new Gui(null);
        GameEngine gameEngine = new GameEngine(gui);
        String correctAnswer = "Pencil Case";
        String userAnswer = "PENCIL cAsE";

        boolean result = gameEngine.checkAnswer(userAnswer, correctAnswer);
        assertTrue(result);
    }

    @Test
    public void testCheckAnswerIncorrect() {
        // Checks if incorrect answers return false.
    	Gui gui = new Gui(null);
        GameEngine gameEngine = new GameEngine(gui);
        String correctAnswer = "Steve";
        String userAnswer = "Bull";

        boolean result = gameEngine.checkAnswer(userAnswer, correctAnswer);
        assertFalse(result);
    }
    
    /**
	* Tests endGame method in the GameEngine class.
	* Includes two tests cases 
	* Will open up two game instances and close them immediately.
	*/
    @Test
    public void testEndGameVictory() {
        // Tests that the game EndGame method works when 10 correctanswers have been entered.
    	Gui gui = new Gui(null);
        GameEngine gameEngine = new GameEngine(gui);

        // Set up the the game with 10 correct answers, a victory.
        gameEngine.setCorrectAnswers(10); 
        gameEngine.setPlayerName("Player1"); 

        //verify the game has closed.
        assertFalse(gameEngine.isGameInProgress()); // Game should not be in progress
    }

    @Test
    public void testEndGameDefeat() {
    	
        // Tests that the game EndGame method works when player has 0 lives..
    	Gui gui = new Gui(null);
        GameEngine gameEngine = new GameEngine(gui);

        // Set up the the game with 0 player lives, a defeat.
        gameEngine.setPlayerLives(0); 
        gameEngine.setPlayerName("Player2"); 

        // Verify the game has closed.
        assertFalse(gameEngine.isGameInProgress()); // Game should not be in progress
    }
    
    /**
	* Tests rollDiceClicked method in the GameEngine class.
	* Includes two tests cases 
	*/
    @Test
    public void testRollDiceClicked() {
    	//checks whether the rolldiceclicked method works as intended (gameinProgress should be true even if default isnt.).
    	Gui gui = new Gui(null);
        GameEngine gameEngine = new GameEngine(gui);

        gameEngine.setGameInProgress(false); 
        
        int result = gameEngine.rollDiceClicked(4); 
        assertTrue(gameEngine.isGameInProgress()); // Game should be in progress after rolling the dice
    }
    
    @Test
    public void testRollDiceClickedInProgress() {
    	//checks whether the rolldiceclicked method works as intended (gameinProgress should be true even if default isnt.).
    	Gui gui = new Gui(null);
        GameEngine gameEngine = new GameEngine(gui);

        gameEngine.setGameInProgress(true);

        // Call rollDiceClicked with a dice value
        int result = gameEngine.rollDiceClicked(6); 
        assertTrue(gameEngine.isGameInProgress()); // Game should be in progress again.

    }

}
