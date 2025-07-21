import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test class for comprehensive testing of all methods within the GameSetup class.
 */
class TestingGameSetup {
	
	
	
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
	* Tests getCategoryForPosition method in the GameSetup class.
	* Includes two tests cases 
	*/
	@Test
    public void testGetCategoryForPosition() {
		// Test if correct Category is returned for given board position
		GameSetup gsTesting = new GameSetup();
        // Testing if each return type is what's expected.
		String position = gsTesting.getCategoryForPosition(2);
		String position2 = gsTesting.getCategoryForPosition(13);
        assertEquals(position, "History");
        assertEquals(position2, "Food");
    }

    @Test
    public void testStringGetCategoryForPosition() {
    	GameSetup gsTesting = new GameSetup();
        // Testing to see if return types is string.
    	assertTrue(gsTesting.getCategoryForPosition(2) instanceof String);
    	assertTrue(gsTesting.getCategoryForPosition(7) instanceof String);
    	assertTrue(gsTesting.getCategoryForPosition(11) instanceof String);
    }
    
    /**
	* Tests getQuestionForCategory method in the GameSetup class.
	* Includes two tests cases 
	*/
	@Test
    public void testGetQuestionForFoodCategory() {
		//Testing that a return value isn't null and it matches expected value.
		GameSetup gsTesting = new GameSetup();
        String question = gsTesting.getQuestionForCategory("Food", 0);
        assertNotNull("Question should not be null", question);
        assertEquals(gsTesting.getQuestionForCategory("Food", 0), "What is the primary filling in a traditional BLT sandwich. Bacon Lettuce and what else?");
        assertEquals(gsTesting.getQuestionForCategory("History", 2), "What is the collective name given to countries formerly ruled by the British Empire?");
        assertEquals(gsTesting.getQuestionForCategory("Movie", 5), "What was the name of the prison Andy Dufresne escapes from?");
    }

    @Test
    public void testGetQuestionForInvalidCategory() {
    	//Testing to check a result shouldn't return with an invalid category.
    	GameSetup gsTesting = new GameSetup();
        // Assuming there are no questions for an invalid category
        String question = gsTesting.getQuestionForCategory("InvalidCategory", 0);
        assertEquals("InvalidCategory", question);
    }
    
    /**
	* Tests getRemainingRowsCount method in the GameSetup class.
	* Includes two tests cases 
	*/
	@Test
    public void testGetRemainingRowsCount() {
		//Testing whether the table is greater than one. By default 10 rows in table.
		GameSetup gsTesting = new GameSetup();
		int remainingRows = gsTesting.getRemainingRowsCount("Food");
        // Assuming there are rows in the "Food" category
        assertTrue(remainingRows > 0);
    }

    @Test
    public void testGetRemainingRowsCountForInvalidCategory() {
    	//Testing last row value in table. Value should be 10
    	GameSetup gsTesting = new GameSetup();
        int remainingRows = gsTesting.getRemainingRowsCount("Food");
        assertEquals(10, remainingRows);
    }
    
    /**
	* Tests getCategoryIndex method in the GameSetup class.
	* Includes two tests cases 
	*/
	@Test
    public void testGetCategoryIndexForFood() {
		//Test to make sure value returned for food is 0.
        GameSetup gsTesting = new GameSetup();
        int index = gsTesting.getCategoryIndex("Food");
        assertEquals(0, index);
    }

    @Test
    public void testGetCategoryIndexForInvalidCategory() {
    	//Test to make sure invalid category returns -1.
        GameSetup gsTesting = new GameSetup();
        int index = gsTesting.getCategoryIndex("InvalidCategory");
        assertEquals( -1, index);
    }
    
    /**
	* Tests getCategoryIndex method in the GameSetup class.
	* Includes two tests cases 
	*/
    @Test
    public void testGetAnswerForFoodCategory() {
		//Testing that a return value isn't null and it matches expected value.
		GameSetup gsTesting = new GameSetup();
        String answer = gsTesting.getAnswerForCategory("Food", 0);
        assertNotNull("Question should not be null", answer);
        assertEquals(gsTesting.getAnswerForCategory("Food", 0), "Tomato");
        assertEquals(gsTesting.getAnswerForCategory("History", 2), "Commonwealth");
        assertEquals(gsTesting.getAnswerForCategory("Movie", 5), "Shawshank Redemption");
    }

    @Test
    public void testGetAnswerForInvalidCategory() {
    	//Testing to check a result shouldn't return with an invalid category.
    	GameSetup gsTesting = new GameSetup();
        // Assuming there are no answers for an invalid category
        String question = gsTesting.getAnswerForCategory("InvalidCategory", 0);
        assertEquals("InvalidCategory", question);
    }
    
    /**
	* Tests removeUsedQuestion method in the GameSetup class.
	* Includes two tests cases 
	*/
    @Test
    public void testRemoveUsedQuestionForFoodCategory() {
    	GameSetup gsTesting = new GameSetup();
        int initialSize = gsTesting.getRemainingRowsCount("Food");

        // Assuming there are questions in the "Food" category
        if (initialSize > 0) {
            gsTesting.removeUsedQuestion("Food", 0);

            int newSize = gsTesting.getRemainingRowsCount("Food");
            assertEquals(initialSize - 1, newSize);
        }
    }

    @Test
    public void testRemoveUsedQuestionForInvalidCategory() {
    	GameSetup gsTesting = new GameSetup();
    	String category = "Movie";
    	
        // Removes the first question in the Movie Linkedlist.
    	// "Who directed the films Kill Bill and Pulp Fiction?" is 2nd row. After deletion should be 1st.
    	gsTesting.removeUsedQuestion(category, 0);

    	assertEquals(gsTesting.getQuestionForCategory("Movie", 0), "Who directed the films Kill Bill and Pulp Fiction?");
    }
   
}
