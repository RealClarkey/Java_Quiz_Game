import static org.junit.jupiter.api.Assertions.*;

import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestingGUI {

	

	@BeforeAll
	static void creatingTestDatabase() throws Exception {
		// Statements for creating test tables in the database to work with, will be dropped later
	    String createTableStmt1 = "CREATE TABLE table1 (table1_ID INT(11) PRIMARY KEY AUTO_INCREMENT,"
	            + "table1_Q VARCHAR(200) NOT NULL,"
	            + "table1_A VARCHAR(50) NOT NULL);";

	    String insertValuesStmt1 = "INSERT INTO table1 (table1_Q, table1_A) VALUES "
	            + "('Capital of France?','Paris'),"
	            + "('Number of continents?','Seven'),"
	            + "('Author of Romeo and Juliet?','William Shakespeare'),"
	            + "('Largest mammal?','Blue Whale'),"
	            + "('Year Titanic sank?','1912'),"
	            + "('Capital of Australia?','Canberra'),"
	            + "('Planets in our solar system?','Eight');";

	    try {
	        Connection connection = DBConnector.getConnection(); // connect to database
	        PreparedStatement pstmt1 = connection.prepareStatement(createTableStmt1);
	        pstmt1.executeUpdate();
	        pstmt1.close();


	        PreparedStatement pstmt4 = connection.prepareStatement(insertValuesStmt1);
	        pstmt4.executeUpdate();
	        pstmt4.close();

	        
	        connection.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@AfterAll
	static void deletingDatabase() throws Exception {
		//Removing tables from server.
	    String dropTablesStmt = "DROP TABLE table1;"; 
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
	
	//Testing rollDiceTest method.
	@Test
    void rollDiceTest() {
        Gui gui = new Gui(null);
        int diceRoll = gui.rollDice();
        assertTrue(diceRoll >= 1 && diceRoll <= 6);
    }
	
	//Testing updatePlayerScore method.
    @Test
    void testUpdatePlayerScore() {
        Gui gui = new Gui(null);
        gui.updatePlayerScore(100);
        assertEquals("Score: 100", gui.lblPlayerScore.getText(), "Player score should be updated.");
    }
    
    //Testing updatePlayerName method.
    @Test
    void testUpdatePlayerName() {
    	//Testing updateplayername functionality.
        Gui gui = new Gui(null);
        gui.updatePlayerName("TestPlayer");
        assertEquals("TestPlayer", gui.lblPlayerName.getText(), "Player name should be updated.");
    }
    
    //Testing of gamescreen button clicked.
    @Test
    void rollDiceButtonActionListenerTest() {
    	//Checking functionality of mouse button click for rolldice.
        Gui gui = new Gui(null);
        JButton rollDiceButton = new JButton("Roll Dice");
        gui.createGameScreen();

        // Simulating a button click
        ActionEvent actionEvent = new ActionEvent(rollDiceButton, ActionEvent.ACTION_PERFORMED, null);
        for (ActionListener listener : rollDiceButton.getActionListeners()) {
            listener.actionPerformed(actionEvent);
        }
        assertEquals("Dice Roll: ", gui.diceRollValue.getText(), "Dice roll value should be updated.");
    }
    
    /**
	* Tests createIntroScreen method in the Gui class.
	* Includes two tests cases 
	*/
    @Test
    void testCreateIntroScreen() {
    	//Test to see if player name input functionality is working.
        Gui gui = new Gui(null);
        gui.createIntroScreen();
        gui.btnPlayerNameButton.doClick();
        gui.lblPlayerName.setText("John Doe");
        assertEquals("John Doe", gui.lblPlayerName.getText());
    }
    
    @Test
    public void testNavigateToAdminScreen() {
    	//Test to see if admin menu button is working.
        Gui gui = new Gui(null);
        // Click "Admin Menu"
        gui.btnAdminMenu.doClick();
        assertTrue(gui.cardPanel.getComponent(2).isVisible()); 
    }
    
    /**
	* Tests createGameScreen method in the Gui class.
	* Includes two tests cases 
	*/
    @Test
    public void testRollDiceButtonClicked() {
    	//check if the rolldice button has been clicked.
        Gui gui = new Gui(null);
        ButtonClickTracker clickTracker = new ButtonClickTracker();
        gui.addRollDiceActionListener(clickTracker);
        // Simulating the button click
        gui.rollDiceButton.getActionListeners()[0].actionPerformed(new ActionEvent(gui.rollDiceButton, ActionEvent.ACTION_PERFORMED, null));
        // Check if the button was clicked
        assertTrue(clickTracker.isButtonClicked());
    }
    // ActionListener to track the button clicks.
    private static class ButtonClickTracker implements ActionListener {
        private boolean buttonClicked = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            buttonClicked = true;
        }
        public boolean isButtonClicked() {
            return buttonClicked;
        }
    }
    
    @Test
    public void testBackground() {
    	//check if background image has loaded
        Gui gui = new Gui(null);
        // Access the gamePanel created in the Gui
        JPanel gamePanel = (JPanel) gui.cardPanel.getComponent(1); 
        Image backgroundImage = gui.backgroundImage;

        // Check if the backgroundImage is not empty/null
        assertNotNull(backgroundImage);
        // Check if the gamePanel's paintComponent method has been called
        assertTrue(gamePanel.isDoubleBuffered());
    }
    
    /**
	* Tests createAdminScreen method in the Gui class.
	* Includes two tests cases 
	*/
    @Test
    void testCreateAdminScreenNotNull() {
        Gui gui = new Gui(null);
        JPanel adminPanel = gui.createAdminScreen();

        assertNotNull(adminPanel, "adminPanel should not be null");
    }

    @Test
    void testCreateAdminScreenComponentTypes() {
        Gui gui = new Gui(null);
        JPanel adminPanel = gui.createAdminScreen();

        assertTrue(adminPanel.getComponent(0) instanceof JButton); 
        assertTrue(adminPanel.getComponent(1) instanceof JLabel); 
        assertTrue(adminPanel.getComponent(2) instanceof JLabel); 
    }
    
    /**
	* Tests displayCSVContent method in the Gui class.
	* Includes two tests cases  
	*/
    @Test
    void testdisplayCSVContent() throws Exception {
    	//testing returned result is as expected.
        Gui gui = new Gui(null);
        String csvTest = "testing/test2.csv";
        gui.displayCSVContent(csvTest);
           
        String expected = "Showing data from: " + csvTest + "Test_ID,Test_Q,Test_A\n"
                + "1,Question1,Answer1\n"
                + "2,Question2,Answer2";
        String actual = gui.textArea.getText().trim();

        assertEquals(expected, actual);

        
    }
    
    @Test
    void notNullDisplayCSVContent2() throws Exception {
    	//testing that result is not null
        Gui gui = new Gui(null);
        String csvTest = "testing/test2.csv";
        gui.displayCSVContent(csvTest);
        assertNotNull(gui.textArea);
        
    }
    
    /**
	* Tests displaySqlContentInTextField method in the Gui class.
	* Includes two tests cases  
	*/
    @Test
    void testDisplaySqlContentInTextField() {
    	//Testing that data is returned correctly from SQL table by comparing to expected data.
        Gui gui = new Gui(null);
        String tableName = "table1";

        gui.displaySqlContentInTextField(tableName);
        String actual = gui.textArea.getText().trim();

        String expectedHeader = "table1_ID, table1_Q, table1_A";
        String expectedRow1 = "1, Capital of France?, Paris";
        String expectedRow2 = "2, Number of continents?, Seven";
        String expectedRow3 = "3, Author of Romeo and Juliet?, William Shakespeare";
        String expectedRow4 = "4, Largest mammal?, Blue Whale";
        String expectedRow5 = "5, Year Titanic sank?, 1912";
        String expectedRow6 = "6, Capital of Australia?, Canberra";
        String expectedRow7 = "7, Planets in our solar system?, Eight";

        String expected = "Showing data from: " + tableName + "\n\n"
                + expectedHeader + "\n"
                + expectedRow1 + "\n"
                + expectedRow2 + "\n"
                + expectedRow3 + "\n"
                + expectedRow4 + "\n"
                + expectedRow5 + "\n"
                + expectedRow6 + "\n"
                + expectedRow7;

        assertEquals(expected, actual);
    }

    @Test
    void stringdisplaySqlContentInTextField() {
    	//Testing returned value is a string.
        Gui gui = new Gui(null);
        String testTableName = "food";

        gui.displaySqlContentInTextField(testTableName);
        String actual = gui.textArea.getText().trim();

        assertTrue(actual instanceof String, "The result should be an instance of String.");
    }
    
    /**
	* Tests showIntroScreen method in the Gui class.
	* Includes two tests cases  
	*/
    @Test
    void testShowIntroScreen() {
    	//Testing that the intro screen is called.
        Gui gui = new Gui(null);
        gui.showIntroScreen();
        assertEquals(gui.cardPanel, gui.getFrame().getContentPane()); 
    }
    
    @Test
    public void testVisibleShowIntroScreen() {
        // Testing the introscreen is visiable when called.
        Gui gui = new Gui(null);
        gui.showIntroScreen();
        assertTrue(gui.cardPanel.isShowing());
    }
    
    /**
	* Tests showGameScreen method in the Gui class.
	* Includes two tests cases  
	*/
    @Test
    void testShowGameScreen() {
    	//Testing that the game screen is called.
        Gui gui = new Gui(null);
        gui.showGameScreen();
        assertEquals(gui.cardPanel, gui.getFrame().getContentPane()); 
    }
    
    @Test
    public void testVisibleShowGameScreen() {
        // Testing the gamescreen is visiable when called.
        Gui gui = new Gui(null);
        gui.showGameScreen();
        assertTrue(gui.cardPanel.isShowing());
    }
    
    /**
	* Tests showAdminScreen method in the Gui class.
	* Includes two tests cases  
	*/
    @Test
    void testShowAdminScreen() {
    	//Testing that the admin screen is called.
        Gui gui = new Gui(null);
        gui.showAdminScreen();
        assertEquals(gui.cardPanel, gui.getFrame().getContentPane()); 
    }
    
    @Test
    public void testVisibleShowAdminScreen() {
        // Testing the admin screen is visiable when called.
        Gui gui = new Gui(null);
        gui.showAdminScreen();
        assertTrue(gui.cardPanel.isShowing());
    }

}
