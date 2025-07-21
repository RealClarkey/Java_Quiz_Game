import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.StringJoiner;

interface ButtonClickListener {
	int rollDiceClicked(int value);
	void playTurn(int result);
}

public class Gui extends JPanel implements ButtonClickListener {
	private ButtonClickListener buttonClickListener;
	Image backgroundImage;
	private Image playerIconImage;
	private Image quizLogo;
	private BoardPositions positions;
	private int playerX;
	private int playerY;

	public JLabel lblPlayerName;
	public JLabel diceRollValue;
	private JLabel positionLabel;
	private JLabel categoryLogic;
	private JLabel lblLivesCount;
	private JLabel lblCurrentAnswers;
	private JLabel lblHighScoresTextLabel;
	public JLabel lblPlayerScore;
	JButton btnPlayerNameButton;
	JButton btnAdminMenu;
	JButton rollDiceButton;
	JButton btnSingleQuestionSubmit;

	public JTextArea textArea;

	private JFrame frame;
	public JPanel cardPanel;
	public static final String INTRO_SCREEN = "IntroScreen";
	public static final String GAME_SCREEN = "GameScreen";
	public static final String ADMIN_SCREEN = "AdminScreen";
	public JTextField textField;
	private JTextField textFieldQuestion;
	private JTextField textFieldAnswer;
	private JTextField textFieldCSVName;
	private JTextField textTableName;
	private JTextField textRemoveRowNumber;
	private JTextField textRemoveCSVName;

	private LinkedList<String> multiQuestions;
	private LinkedList<String> multiAnswers;
	
	public void addRollDiceActionListener(ActionListener listener) {
        rollDiceButton.addActionListener(listener);
    }

	// LAST CHECKPOINT HERE.

	public Gui(ButtonClickListener listener) {
		this.buttonClickListener = listener;

		// Initialize screens
		JPanel introScreen = createIntroScreen();
		JPanel gameScreen = createGameScreen();
		JPanel adminScreen = createAdminScreen();

		// Set up JFrame
		frame = new JFrame("Quiz Game");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				"QuizPractise\\quiz-icon.png"));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(1200, 800);
		frame.setLocationRelativeTo(null);

		// Set up layout and components
		setLayout(new BorderLayout());

		// Create a panel to hold the cards
		cardPanel = new JPanel(new CardLayout());
		cardPanel.add(introScreen, INTRO_SCREEN);
		cardPanel.add(gameScreen, GAME_SCREEN);
		cardPanel.add(adminScreen, ADMIN_SCREEN);

		// Show the intro screen initially
		showIntroScreen();

		frame.setVisible(true);

		GameEngine gameEngine = new GameEngine(this);
		buttonClickListener = gameEngine;

	}

	public JPanel createIntroScreen() {
		JPanel introPanel = new JPanel();
		introPanel.setBackground(new Color(73, 72, 83, 255));
		introPanel.setLayout(null);

		JLabel labelEnterName = new JLabel("Enter Name:"); // Player name input.
		labelEnterName.setForeground(new Color(255, 255, 255));
		labelEnterName.setHorizontalAlignment(SwingConstants.CENTER);
		labelEnterName.setBounds(494, 388, 202, 19);
		labelEnterName.setFont(new Font("Arial", Font.BOLD, 24));

		introPanel.add(labelEnterName);

		// player name input
		textField = new JTextField();
		textField.setBounds(507, 418, 180, 38);
		introPanel.add(textField);
		textField.setColumns(10);

		quizLogo = new ImageIcon("quiz-logo-resized.png").getImage();
		quizLogo = quizLogo.getScaledInstance(340, 300, Image.SCALE_SMOOTH);

		JLabel logoLabel = new JLabel(new ImageIcon(quizLogo));
		logoLabel.setBounds(422, 56, 340, 300);
		introPanel.add(logoLabel);

		btnAdminMenu = new JButton("Admin Menu");
		btnAdminMenu.setBounds(537, 656, 118, 23);
		introPanel.add(btnAdminMenu);

		// button to start the game.
		btnPlayerNameButton = new JButton("Play Game");
		btnPlayerNameButton.setBounds(537, 467, 118, 40);
		introPanel.add(btnPlayerNameButton);

		// Action Listeners for Play Game.
		btnPlayerNameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String playerName = textField.getText(); // Get the text from the textField
				if (!playerName.isEmpty()) {
					updatePlayerName(playerName);
					// Start the game by notifying the listener (GameEngine)
					if (buttonClickListener != null) {
						buttonClickListener.playTurn(0); // PAssing dummy data of 0 to pass an arguement.
						// Set Player name to GameEngine
						((GameEngine) buttonClickListener).setPlayerName(playerName);
						System.out.println("Player name set: " + playerName);
					}
					showGameScreen();
					System.out.println("Starting Game...");
				} else {
					JOptionPane.showMessageDialog(frame, "Please enter your name.");
				}
			}
		});

		// Action Listeners for AdminMenu and Play Game.
		btnAdminMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAdminScreen();
				System.out.println("Starting Admin...");
			}
		});

		return introPanel;
	}

	public JPanel createGameScreen() {
		// Create a JPanel to hold the game screen components
		JPanel gamePanel = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

				// Drawing the player icon position.

				g.drawImage(playerIconImage, playerX, playerY, 40, 40, null);
				// System.out.println("This is from the paintComponent: " + playerX + " " +
				// playerY);
			}
		};
		gamePanel.setLayout(null);

		// Load background image
		backgroundImage = new ImageIcon("gameboard.png").getImage();

		// Load and scale player icon image
		playerIconImage = new ImageIcon("playericon40x40.png").getImage();
		// playerIconImage = playerIconImage.getScaledInstance(40, 40,
		// Image.SCALE_SMOOTH);

		// Dice Roll Components 1st position.
		diceRollValue = new JLabel("Dice Roll: ");
		diceRollValue.setBackground(new Color(255, 255, 255));
		diceRollValue.setForeground(Color.WHITE);
		diceRollValue.setBounds(348, 293, 90, 21);
		diceRollValue.setFont(new Font("Arial", Font.BOLD, 16));
		gamePanel.add(diceRollValue);

		rollDiceButton = new JButton("Roll Dice");
		rollDiceButton.setBounds(346, 334, 85, 21);
		gamePanel.add(rollDiceButton);

		// Displays highscore
		JLabel highscoreLabel = new JLabel("Highscore:");
		highscoreLabel.setForeground(new Color(252, 206, 22));
		highscoreLabel.setBounds(997, 437, 97, 23);
		highscoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
		gamePanel.add(highscoreLabel);

		lblHighScoresTextLabel = new JLabel("");
		lblHighScoresTextLabel.setVerticalAlignment(SwingConstants.TOP);
		lblHighScoresTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblHighScoresTextLabel.setForeground(new Color(255, 255, 255));
		lblHighScoresTextLabel.setBounds(945, 463, 206, 287);
		gamePanel.add(lblHighScoresTextLabel);

		// String path = "highscore/highscore.csv"; - this is used for CSVs.
		String highscoreSQL = "highscore";
		SQLRead reader = new SQLRead();
		LinkedList<Highscore> highscores = reader.readHighscoresSQL(highscoreSQL);

		// Process the highscores as needed
		StringBuilder highscoreText = new StringBuilder();
		for (Highscore highscore : highscores) {
			highscoreText.append(highscore.getId()).append(". ").append(highscore.getName()).append(" &nbsp; ")
					.append(highscore.getNumber()).append("\n");
		}

		lblHighScoresTextLabel.setText("<html>" + highscoreText.toString().replaceAll("\n", "<br>") + "</html>");

		// Player Position Info
		positionLabel = new JLabel("Player Position: ");
		positionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		positionLabel.setBackground(new Color(255, 255, 255));
		positionLabel.setForeground(Color.WHITE);
		positionLabel.setBounds(303, 383, 200, 21);
		positionLabel.setFont(new Font("Arial", Font.BOLD, 16));
		gamePanel.add(positionLabel);

		// Prints out Category Info
		categoryLogic = new JLabel("Current category: ");
		categoryLogic.setBackground(new Color(255, 255, 255));
		categoryLogic.setForeground(Color.WHITE);
		categoryLogic.setBounds(280, 442, 300, 21);
		categoryLogic.setFont(new Font("Arial", Font.BOLD, 16));
		gamePanel.add(categoryLogic);

		// Displays the player's name. Grabbed from intro Panel.
		lblPlayerName = new JLabel("");
		lblPlayerName.setVerticalAlignment(SwingConstants.TOP);
		lblPlayerName.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerName.setForeground(new Color(252, 206, 22));
		lblPlayerName.setBounds(893, 71, 254, 44);
		lblPlayerName.setFont(new Font("Arial", Font.BOLD, 30));
		gamePanel.add(lblPlayerName);

		// Displays the player's lives.
		lblLivesCount = new JLabel("3");
		lblLivesCount.setForeground(new Color(255, 255, 255));
		lblLivesCount.setBounds(1008, 168, 79, 38);
		lblLivesCount.setFont(new Font("Arial", Font.BOLD, 30));
		gamePanel.add(lblLivesCount);

		// Displays the player's correct answers:
		lblCurrentAnswers = new JLabel("Correct Answers: ");
		lblCurrentAnswers.setForeground(new Color(255, 255, 255));
		lblCurrentAnswers.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentAnswers.setBounds(325, 479, 120, 14);
		lblCurrentAnswers.setFont(new Font("Arial", Font.BOLD, 12));
		gamePanel.add(lblCurrentAnswers);

		// Displays the Player's Score
		lblPlayerScore = new JLabel("Score: ");
		lblPlayerScore.setForeground(new Color(255, 255, 255));
		lblPlayerScore.setBounds(982, 248, 165, 36);
		lblPlayerScore.setFont(new Font("Arial", Font.BOLD, 30));
		gamePanel.add(lblPlayerScore);

		positions = new BoardPositions();

		// Roll Dice Button ActionListener
		rollDiceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("rollDice Clicked");
				int result = rollDice();
				
				gamePanel.repaint(); 
				buttonClickListener.rollDiceClicked(result);

			}
		});

		return gamePanel;
	}

	public int rollDice() {
		return (int) (Math.random() * 6) + 1; // Simulate rolling a 6-sided dice
	}

	public void updatePlayerScore(int playerScore) {
		lblPlayerScore.setText("Score: " + playerScore);
	}

	public void updatePlayerName(String playerName) {
		lblPlayerName.setText(playerName);

	}

	// Updates dice roll value from GameEngine
	public void updateDiceRollValue(int value) {
		diceRollValue.setText("Dice Roll: " + value);
		// repaint();
	}

	// Updates updatePlayerPosition value from GameEngine
	public void updatePlayerPosition(int position) {
		positionLabel.setText("Player Position: " + position);
	}

	// UPdates the players lives.
	public void updateLivesCount(int playerLives) {
		lblLivesCount.setText("" + playerLives);
	}

	// Updates the player's current score/answer count.
	public void updateCurrentAnswers(int score) {
		lblCurrentAnswers.setText("Correct Answers: " + score);
	}

	// Updates updatecategoryLogic value from GameEngine
	public void updatecategoryLogic(String text) {
		categoryLogic.setText("Current category: " + text);
	}

	public void updatePlayerIconPosition(int positionX, int positionY) {
		playerX = positions.getPositionX(positionX);
		playerY = positions.getPositionY(positionY);

		// System.out.println("updatePlayerIconPosition: " + "X: " + playerX + " Y: " +
		// playerY);
	}

	JPanel createAdminScreen() {
		JPanel adminPanel = new JPanel();
		adminPanel.setBackground(new Color(73, 72, 83, 255));
		adminPanel.setLayout(null);

		JButton btnIntroButton = new JButton("Intro Screen");
		btnIntroButton.setBounds(32, 21, 111, 23);
		adminPanel.add(btnIntroButton);

		JLabel lblNewLabel = new JLabel("Admin Panel");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setBounds(522, 39, 300, 28);
		adminPanel.add(lblNewLabel);

		JLabel highscoreLabel = new JLabel("Highscore:");
		highscoreLabel.setForeground(new Color(252, 206, 22));
		highscoreLabel.setBounds(1005, 122, 97, 23);
		highscoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
		adminPanel.add(highscoreLabel);

		lblHighScoresTextLabel = new JLabel("");
		lblHighScoresTextLabel.setVerticalAlignment(SwingConstants.TOP);
		lblHighScoresTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblHighScoresTextLabel.setForeground(new Color(255, 255, 255));
		lblHighScoresTextLabel.setBounds(953, 148, 206, 287);
		adminPanel.add(lblHighScoresTextLabel);

		// String path = "highscore/highscore.csv"; - this is used for CSVs.
		String highscoreSQL = "highscore";
		SQLRead reader = new SQLRead();
		LinkedList<Highscore> highscores = reader.readHighscoresSQL(highscoreSQL);

		// Process the highscores as needed
		StringBuilder highscoreText = new StringBuilder();
		for (Highscore highscore : highscores) {
			highscoreText.append(highscore.getId()).append(". ").append(highscore.getName()).append(" ")
					.append(highscore.getNumber()).append("\n");
		}

		lblHighScoresTextLabel.setText("<html>" + highscoreText.toString().replaceAll("\n", "<br>") + "</html>");

		// This is for Single Q/A.
		JLabel lblSingleQA = new JLabel("Add Single Question:");
		lblSingleQA.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSingleQA.setForeground(new Color(255, 255, 255));
		lblSingleQA.setBounds(90, 123, 276, 20);
		adminPanel.add(lblSingleQA);

		textFieldQuestion = new JTextField();
		textFieldQuestion.setText("  Enter Question Here");
		textFieldQuestion.setBounds(90, 161, 367, 20);
		adminPanel.add(textFieldQuestion);
		textFieldQuestion.setColumns(10);

		textFieldAnswer = new JTextField();
		textFieldAnswer.setHorizontalAlignment(SwingConstants.LEFT);
		textFieldAnswer.setText("  Enter Answer Here");
		textFieldAnswer.setBounds(487, 161, 111, 20);
		adminPanel.add(textFieldAnswer);
		textFieldAnswer.setColumns(10);

		textFieldCSVName = new JTextField();
		textFieldCSVName.setText("Table Name");
		textFieldCSVName.setBounds(712, 161, 103, 20);
		adminPanel.add(textFieldCSVName);
		textFieldCSVName.setColumns(10);

		btnSingleQuestionSubmit = new JButton("Submit");
		btnSingleQuestionSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSingleQuestionSubmit.setBounds(722, 197, 89, 23);
		adminPanel.add(btnSingleQuestionSubmit);

		JButton btnSingleQuestionTest = new JButton("Test");
		btnSingleQuestionTest.setBounds(722, 237, 89, 23);
		adminPanel.add(btnSingleQuestionTest);

		JLabel testDataLabel = new JLabel("Test data shown here.");
		testDataLabel.setForeground(new Color(255, 255, 255));
		testDataLabel.setBounds(90, 241, 610, 14);
		adminPanel.add(testDataLabel);

		// SUBMIT SINGLE QUESTION TO A SQL TABLE.

		btnSingleQuestionSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String singleQuestionInput = textFieldQuestion.getText();
				String singleAnswerInput = textFieldAnswer.getText();
				String singleTableName = textFieldCSVName.getText();
				//String singleCSVPath = textFieldCSVName.getText();
				//Write writeSingleQuestion = new Write();
				SQLWrite sqlWriter = new SQLWrite();
				System.out.println(singleTableName);
				sqlWriter.addQandASingleSQLTable(singleQuestionInput, singleAnswerInput, singleTableName);

				//writeSingleQuestion.addQandASingleToCSV(singleQuestionInput, singleAnswerInput, singleCSVPath); - used for CSVs

			}
		});

		// Click Test and show last line of CSV.
		btnSingleQuestionTest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String singleTestPath = textFieldCSVName.getText();
				SQLRead readerTest = new SQLRead();
				LinkedList<String> singleTestsQ = readerTest.readAllQuestionsFromSQL(singleTestPath);
				LinkedList<String> singleTestsA = readerTest.readAllAnswersFromSQL(singleTestPath);

				// Process the last line of Question CSV
				StringBuilder singleTestsTextQ = new StringBuilder();
				String LastTestsText = singleTestsQ.getLast();
				singleTestsTextQ.append(LastTestsText).append("\n");

				// Process the last line of Answer CSV
				StringBuilder singleTestsTextA = new StringBuilder();
				String LastTestsTextA = singleTestsA.getLast();
				singleTestsTextA.append(LastTestsTextA).append("\n");

				testDataLabel.setText("Question: " + singleTestsTextQ.toString() + " Answer: " + singleTestsTextA.toString());
				// System.out.println("Output: " + singleTestsTextQ + singleTestsTextA);
			}
		});

		// This is Multiple Q/A
		JLabel lblAddMultipleTitle = new JLabel("Add Multiple Question:");
		lblAddMultipleTitle.setForeground(new Color(255, 255, 255));
		lblAddMultipleTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAddMultipleTitle.setBounds(90, 381, 257, 20);
		adminPanel.add(lblAddMultipleTitle);

		textTableName = new JTextField();
		textTableName.setText("Enter Table Name");
		textTableName.setBounds(212, 413, 111, 20);
		adminPanel.add(textTableName);
		textTableName.setColumns(10);

		JButton btnUploadCSV = new JButton("Upload");
		btnUploadCSV.setBounds(90, 412, 89, 23);
		adminPanel.add(btnUploadCSV);

		JButton btnSubmitCSVs = new JButton("Submit");
		btnSubmitCSVs.setBounds(345, 412, 89, 23);
		adminPanel.add(btnSubmitCSVs);

		textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(564, 411, 576, 323);
		adminPanel.add(scrollPane);

		// ADD MULTIPLE QUESTIONS AND ANSWERS
		btnUploadCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new CSVFileFilter());
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						String filePath = fileChooser.getSelectedFile().getAbsolutePath();
						System.out.println(filePath);
						displayCSVContent(filePath);

						Read mutliQsandAs = new Read(filePath);
						multiQuestions = mutliQsandAs.readAllQuestionsFromCSV();
						multiAnswers = mutliQsandAs.readAllAnswersFromCSV();

						System.out.println(multiQuestions);
						System.out.println(multiAnswers);

					} catch (Exception ex) {
						textArea.setText("Error: " + ex.getMessage());
					}
				}
			}

		});

		btnSubmitCSVs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Write multiQsandAsMerge = new Write();
				SQLWrite multiQsAs = new SQLWrite();
				multiQsAs.addQandAMultipleSQLTable(multiQuestions, multiAnswers, textTableName.getText());
				try {
					displaySqlContentInTextField(textTableName.getText());
					//displayCSVContent(textTableName.getText()); - this is old one for CSV
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// This is Remove Q
		JLabel lblRemoveAQuestionTitle = new JLabel("Remove A Question:");
		lblRemoveAQuestionTitle.setForeground(new Color(255, 255, 255));
		lblRemoveAQuestionTitle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRemoveAQuestionTitle.setBounds(90, 527, 191, 20);
		adminPanel.add(lblRemoveAQuestionTitle);

		textRemoveRowNumber = new JTextField();
		textRemoveRowNumber.setText("Row Number");
		textRemoveRowNumber.setBounds(93, 559, 86, 20);
		adminPanel.add(textRemoveRowNumber);
		textRemoveRowNumber.setColumns(10);

		textRemoveCSVName = new JTextField();
		textRemoveCSVName.setText("CSV Name");
		textRemoveCSVName.setBounds(212, 559, 86, 20);
		adminPanel.add(textRemoveCSVName);
		textRemoveCSVName.setColumns(10);

		JButton btnRemoveDelete = new JButton("Delete");
		btnRemoveDelete.setBounds(345, 558, 89, 23);
		adminPanel.add(btnRemoveDelete);

		// Click to Remove Question (row from CSV)
		btnRemoveDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// DeleteRowFromCSV(int rowID, String csvPath)

				String rowIDString = textRemoveRowNumber.getText();
				int rowID = Integer.parseInt(rowIDString);
				String csvPath = textRemoveCSVName.getText();

				Delete deleteRow = new Delete();
				SQLDelete deleteSQL = new SQLDelete();
				deleteSQL.deleteRowFromSQLTable(rowID, csvPath);
				//deleteRow.deleteRowFromCSV(rowID, csvPath); - this was used for CSV delete.

			}
		});

		// Action Listeners for AdminMenu and Play Game.
		btnIntroButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showIntroScreen();
				System.out.println("Starting Intro...");
			}
		});

		return adminPanel;
	}

	class CSVFileFilter extends FileFilter {

		@Override

		public String getDescription() {
			return "CSV Files";
		}

		@Override

		public boolean accept(File f) {
			return f.getName().toLowerCase().endsWith(".csv") || f.isDirectory();
		}

	}

	public void displayCSVContent(String filePath) throws Exception {
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		}
		textArea.setText("Showing data from: " + filePath + content.toString());
	}
	
	
	//display table content to text field.
	public void displaySqlContentInTextField(String tableName) {
        StringBuilder content = new StringBuilder();

        try {
            String query = "SELECT * FROM " + tableName;

            try (Connection connection = DBConnector.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                // Get column names
                int columnCount = resultSet.getMetaData().getColumnCount();
                StringJoiner headerJoiner = new StringJoiner(", ");
                for (int i = 1; i <= columnCount; i++) {
                    headerJoiner.add(resultSet.getMetaData().getColumnName(i));
                }
                content.append(headerJoiner.toString()).append("\n");

                // Get data
                while (resultSet.next()) {
                    StringJoiner rowJoiner = new StringJoiner(", ");
                    for (int i = 1; i <= columnCount; i++) {
                        rowJoiner.add(resultSet.getString(i));
                    }
                    content.append(rowJoiner.toString()).append("\n");
                }
            }

            // Display data in the text area 
            textArea.setText("Showing data from: " + tableName + "\n\n" + content.toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	

	public void showIntroScreen() {
		frame.setContentPane(cardPanel);
		CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
		cardLayout.show(cardPanel, INTRO_SCREEN);
	}

	void showGameScreen() {
		CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
		cardLayout.show(cardPanel, GAME_SCREEN);
	}

	void showAdminScreen() {
		CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
		cardLayout.show(cardPanel, ADMIN_SCREEN);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Gui(null));
	}

	@Override
	public int rollDiceClicked(int value) {
		// TODO Auto-generated method stub
		return 0;
	}
	public JFrame getFrame() {
	    return frame;
	}

	@Override
	public void playTurn(int result) {
		// TODO Auto-generated method stub

	}
}
