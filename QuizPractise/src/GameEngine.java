import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;

class GameEngine implements ButtonClickListener {
    private Gui gui;
    private int playerPosition;
    private int playerLives;
    private int correctAnswers;
    private int maxQuestions;
    private int playerScore;
    private boolean gameInProgress;
    private GameSetup gameSetup;
	private String playerName;
    
	public GameEngine(Gui gui) {
        this.gui = gui;
        this.setPlayerLives(3);
        this.setCorrectAnswers(0);
        this.maxQuestions = 10; // Set the maximum number of questions
        this.setGameInProgress(false); // Set to false so it wont work until dice roll been clicked.
        this.gameSetup = new GameSetup();

    }
    
    public void setButtonClickListener(ButtonClickListener listener) {
    }


    public void startGame(int diceRollNumber) {
        playerPosition = 0;
        setCorrectAnswers(0);
        setPlayerScore(0);
        setGameInProgress(true);
        playTurn(diceRollNumber);
        System.out.println("This is startGame");
        
        
    }

 
    
    public void playTurn(int diceRollNumber) {
    	
    	
        if (isGameInProgress()) {

        	Random random = new Random();       	
        	
            int diceRoll = diceRollNumber;
            gui.updateDiceRollValue(diceRoll);
            playerPosition = (playerPosition + diceRoll) % 24;
            gui.updatePlayerPosition(playerPosition);
            gui.updatePlayerIconPosition(playerPosition, playerPosition);
            

            // Determine the category based on playerPosition
            String category = gameSetup.getCategoryForPosition(playerPosition);
            gui.updatecategoryLogic(category);
            
            // Generating random number based on remaining index in linkedlists.
            int remainingRows = gameSetup.getRemainingRowsCount(category);
            int randomNumber = random.nextInt(remainingRows);
            System.out.println("This is randomNumber: " + randomNumber);

            // Retrieve the question and answer
            String question = gameSetup.getQuestionForCategory(category, randomNumber);
            String answer = gameSetup.getAnswerForCategory(category, randomNumber);
            System.out.println("The correct answer: " + answer);
            
            // Create dialog box for question and user answer input
            String userAnswer = JOptionPane.showInputDialog("Question: " + question);
           
            

            if (checkAnswer(userAnswer, answer)) {
                setCorrectAnswers(getCorrectAnswers() + 1);
                setPlayerScore(getPlayerScore() + 10);
                gui.updatePlayerScore(getPlayerScore());
                gui.updatePlayerIconPosition(playerPosition, playerPosition);
                gui.updateCurrentAnswers(getCorrectAnswers());
                gameSetup.removeUsedQuestion(category, randomNumber);

                if (getCorrectAnswers() == maxQuestions) {
                    endGame(true);
                } else {
                    //playTurn(diceRollNumber);
                }
            } else {
                setPlayerLives(getPlayerLives() - 1);
                gui.updateLivesCount(getPlayerLives());
                gui.updatePlayerIconPosition(playerPosition, playerPosition);
                if (getPlayerLives() == 0) {
                    endGame(false);
                     
                } else {
                    //playTurn(diceRollNumber);
                }
            }
        }
    }

    //getter and setter.
    public void setPlayerName(String playerName) {
    	this.playerName = playerName;
    }
    
    boolean checkAnswer(String userAnswer, String answer) {
        // Checks if answer is correct but ignores case sensitivity.
        return userAnswer != null && userAnswer.equalsIgnoreCase(answer);
    }

    public void endGame(boolean isVictory) {
        setGameInProgress(false);
        if (isVictory) {
            JOptionPane.showMessageDialog(null, "Congratulations! You won with " + getCorrectAnswers() + " correct answers.");
        } else {
            JOptionPane.showMessageDialog(null, "Game over. You answered " + getCorrectAnswers() + " questions correctly.");
        }

        // Read existing highscores from the CSV file
        String highscoreSQL = "highscore";
        SQLRead reader = new SQLRead();
        LinkedList<Highscore> existingHighscores = reader.readHighscoresSQL(highscoreSQL);

        // Add the new highscore
        Highscore.addHighscore(existingHighscores, playerName, getPlayerScore());

        // Create an instance of SQLWrite and SQL Delete class
        SQLWrite write = new SQLWrite();
        SQLDelete delete = new SQLDelete();
        
        //Potentially add this method to it's own class.
        //Need to wipe current table first.
        try {
			delete.clearHighscoreTable();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        try {
			write.writeHighscoresSQL(existingHighscores, "highscore");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Highscores successfully written to CSV.");

        // Exit the game
        System.exit(0);
    }

	@Override
	public int rollDiceClicked(int value) {
		//System.out.println("GameEngine, rollDiceClicked: " + value);
	    if (!isGameInProgress()) {
	    	//System.out.println("GameEngine. rollDiceClicked(). If statement. ");
	        startGame(value); // Start the game if it's not already in progress
	        
	    } else {
	    	//System.out.println("GameEngine. This is after game in progress");
	    	playTurn(value); // Trigger the game logic with the dice roll value
	    }

	    return value;
	}
	//Created getters and setters for testing classes.
	boolean isGameInProgress() {
		return gameInProgress;
	}

	public void setGameInProgress(boolean gameInProgress) {
		this.gameInProgress = gameInProgress;
	}

	public int getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public int getPlayerLives() {
		return playerLives;
	}

	public void setPlayerLives(int playerLives) {
		this.playerLives = playerLives;
	}

}
