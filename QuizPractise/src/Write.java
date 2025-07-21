import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class Write {

    // Method to add a single question and answer to CSV
    void addQandASingleToCSV(String question, String answer, String CSVPath) {
        // Check for blank questions or answers
        if (question == null || question.trim().isEmpty() || answer == null || answer.trim().isEmpty()) {
            System.out.println("Error: Blank question or answer.");
            return;
        }

        // Read the last ID from the CSV file
        Read lastIDRead = new Read(CSVPath);
        int lastID = lastIDRead.getLastIDFromCSV(CSVPath);

        // Append the new question and answer with auto-generated ID to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSVPath, true))) {
            writer.write((lastID + 1) + "," + question + "," + answer + "\n");
            System.out.println("Question and answer added to CSV successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // Method to add multiple questions and answers to CSV
    void addQandAMultipleToCSV(LinkedList<String> questions, LinkedList<String> answers, String CSVPath) {
        // Read the last ID from the CSV file
    	Read lastIDRead = new Read(CSVPath);
        int lastID = lastIDRead.getLastIDFromCSV(CSVPath);

        // Append the new questions and answers with auto-generated IDs to the CSV file
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSVPath, true))) {
            if (lastID == 0) {
                // If the file is empty, write the header
                writer.println("Test_ID,Test_Q,Test_A");
            }
         // Had to add this here so the new data will start on a new line and not end of existing last row.
            //writer.println(); 
            for (int i = 0; i < questions.size(); i++) {
                // Increment the last ID before using it
                int newID = lastID + i + 1;
                writer.println(newID + "," + questions.get(i) + "," + answers.get(i));
                System.out.println(writer);
            }

            System.out.println("Questions and answers added to CSV successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // Method to write to a highscore CSV file.
    public void writeHighscores(String filePath, LinkedList<Highscore> highscores) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the header
            writer.write("Highscore_ID,Highscore_Name,Highscore_Number");
            writer.newLine();

            // Write each highscore entry
            for (Highscore highscore : highscores) {
                writer.write(String.format("%d,%s,%d",
                        highscore.getId(), highscore.getName(), highscore.getNumber()));
                writer.newLine();
            }

            System.out.println("Highscores written successfully!");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception according to your needs
            throw e; // Rethrow the exception to indicate the failure
        }
    }
}
