import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SQLWrite {
	
	
	SQLWrite(){
		//Constructor here.
	}
	
	//Method to add a single Question and Answer to a given Table. 
	
	public void addQandASingleSQLTable(String question, String answer, String tableName) {
        // Check for blank questions or answers
        if (question == null || question.trim().isEmpty() || answer == null || answer.trim().isEmpty()) {
            System.out.println("Error: Blank question or answer.");
            return;
        }

        // Get the last ID from the table
        int lastID = getLastIDFromTable(tableName);

        // Insert the new question and answer with auto-generated ID to the table
        String sql = "INSERT INTO " + tableName + " ("+tableName+"_ID, "+tableName+"_Q, "+tableName+"_A) VALUES (?, ?, ?)";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, lastID + 1);
            preparedStatement.setString(2, question);
            preparedStatement.setString(3, answer);

            // Execute the insert statement
            preparedStatement.executeUpdate();

            System.out.println("Question and answer added to the table successfully.");

        } catch (SQLException e) {
            System.out.println("Error writing to the database: " + e.getMessage());
        }
    }

    public int getLastIDFromTable(String tableName) {
        String sql = "SELECT MAX("+tableName+"_ID) AS lastID FROM " + tableName;
        int lastID = 0;

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                lastID = resultSet.getInt("lastID");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving last ID from the database: " + e.getMessage());
        }

        return lastID;
    }
	
	
	
	// Method to add multiple questions and answers to DB
    public void addQandAMultipleSQLTable(LinkedList<String> multiQuestions, LinkedList<String> multiAnswers, String tableName) {
        // Check for blank questions or answers
        if (multiQuestions == null || multiAnswers == null || multiQuestions.isEmpty() || multiAnswers.isEmpty()) {
            System.out.println("Error: Blank questions or answers.");
            return;
        }

        // Get the last ID from the table
        int lastID = getLastIDFromTable(tableName);

        // Insert the new questions and answers with auto-generated IDs to the table
        String sql = "INSERT INTO " + tableName + " (" + tableName + "_ID, " + tableName + "_Q, " + tableName + "_A) VALUES (?, ?, ?)";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Iterate through the linked lists
            for (int i = 0; i < multiQuestions.size(); i++) {
                preparedStatement.setInt(1, lastID + i + 1);
                preparedStatement.setString(2, multiQuestions.get(i));
                preparedStatement.setString(3, multiAnswers.get(i));

                // Execute the insert statement
                preparedStatement.executeUpdate();
            }

            System.out.println("Multiple questions and answers added to the table successfully.");

        } catch (SQLException e) {
            System.out.println("Error writing to the database: " + e.getMessage());
        }
    }
	
	// Method to write highscores.
	public void writeHighscoresSQL(LinkedList<Highscore> highscores, String tableName) throws SQLException {
        String sql = "INSERT INTO "+tableName+" ("+tableName+"_ID, "+tableName+"_Name, "+tableName+"_Number) VALUES (?, ?, ?)";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Highscore highscore : highscores) {
                preparedStatement.setInt(1, highscore.getId());
                preparedStatement.setString(2, highscore.getName());
                preparedStatement.setInt(3, highscore.getNumber());

   
                preparedStatement.executeUpdate();
            }

            System.out.println("Highscores written to the database successfully!");

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
            throw e; // Re-throw the exception to indicate the failure
        }
    }
	

}
