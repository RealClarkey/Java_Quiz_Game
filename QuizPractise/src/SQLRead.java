import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class SQLRead {
	
	
	/*
	 * SQL SECTION:
	 * The following SQL section is dedicated to handling database operations using MySQL instead of CSVs.
	 * Equivalent methods have been implemented to retrieve data previously stored in CSVs, which is now
	 * stored in MySQL databases.
	 */
	
	//SQL Read Questions method here 2d Linked list:
		public LinkedList<LinkedList<String>> readFromDatabaseQs(String[] tableNames) {
	        LinkedList<LinkedList<String>> result = new LinkedList<>();

	        try {
	            for (String tableName : tableNames) {
	                String query = "SELECT * FROM " + tableName; // This adds all columns
	                LinkedList<String> columnValues = new LinkedList<>();

	                try (Connection connection = DBConnector.getConnection();
	                	 PreparedStatement preparedStatement = connection.prepareStatement(query);
	                     ResultSet resultSet = preparedStatement.executeQuery()) {

	                    while (resultSet.next()) {
	                        // Fetch values from the second column (index 2)
	                        String value = resultSet.getString(2);
	                        columnValues.add(value);
	                    }
	                }

	                result.add(columnValues);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return result;
	    }
		
		//SQL Read Answers method here 2D linked list:
		public LinkedList<LinkedList<String>> readFromDatabaseAs(String[] tableNames) {
	        LinkedList<LinkedList<String>> result = new LinkedList<>();

	        try {
	            for (String tableName : tableNames) {
	                String query = "SELECT * FROM " + tableName; // This will grab all the tables.
	                LinkedList<String> columnValues = new LinkedList<>();

	                try (Connection connection = DBConnector.getConnection();
	                	 PreparedStatement preparedStatement = connection.prepareStatement(query);
	                     ResultSet resultSet = preparedStatement.executeQuery()) {

	                    while (resultSet.next()) {
	                        // Fetch values from the second column (index 3)
	                        String value = resultSet.getString(3);
	                        columnValues.add(value);
	                    }
	                }

	                result.add(columnValues);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return result;
	    }
	
	// SQL Read Questions method here:
    public LinkedList<String> readAllQuestionsFromSQL(String sqlTableName) {
        LinkedList<String> questions = new LinkedList<>();

        try (Connection connection = DBConnector.getConnection()) {
            String query = "SELECT * FROM " + sqlTableName;

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Fetch values from the second column (index 2)
                    String value = resultSet.getString(2);
                    questions.add(value);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    // SQL Read Answers method here:
    public LinkedList<String> readAllAnswersFromSQL(String sqlTableName) {
        LinkedList<String> answers = new LinkedList<>();

        try (Connection connection = DBConnector.getConnection()) {
            String query = "SELECT * FROM " + sqlTableName;

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    // Fetch values from the third column (index 3)
                    String value = resultSet.getString(3);
                    answers.add(value);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answers;
    }

    // This is for handling the highscore data.
    public LinkedList<Highscore> readHighscoresSQL(String tableName) {
        LinkedList<Highscore> highscores = new LinkedList<>();
        String highscoreID = tableName+"_ID";
        String highscoreName = tableName+"_Name";
        String highscoreNumber = tableName+"_Number";

        try (Connection connection = DBConnector.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {

            while (rs.next()) {
                int id = rs.getInt(highscoreID);
                String name = rs.getString(highscoreName);
                int number = rs.getInt(highscoreNumber);

                Highscore highscore = new Highscore(id, name, number);
                highscores.add(highscore);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //System.out.println(highscores);
        return highscores;
    }
    
 // Display SQL content in text field
//    public void displaySqlContentInTextField(String tableName) {
//        StringBuilder content = new StringBuilder();
//
//        try {
//            String query = "SELECT * FROM " + tableName;
//
//            try (Connection connection = DBConnector.getConnection();
//                 PreparedStatement preparedStatement = connection.prepareStatement(query);
//                 ResultSet resultSet = preparedStatement.executeQuery()) {
//
//                // Get column names
//                int columnCount = resultSet.getMetaData().getColumnCount();
//                StringJoiner headerJoiner = new StringJoiner(", ");
//                for (int i = 1; i <= columnCount; i++) {
//                    headerJoiner.add(resultSet.getMetaData().getColumnName(i));
//                }
//                content.append(headerJoiner.toString()).append("\n");
//
//                // Get data
//                while (resultSet.next()) {
//                    StringJoiner rowJoiner = new StringJoiner(", ");
//                    for (int i = 1; i <= columnCount; i++) {
//                        rowJoiner.add(resultSet.getString(i));
//                    }
//                    content.append(rowJoiner.toString()).append("\n");
//                }
//            }
//
//            // Display data in the text area (adjust textArea accordingly)
//            //System.out.println("Showing data from: " + tableName + "\n\n" + content.toString());
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    
    
}
