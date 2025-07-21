import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Read {;
	private String path;
	private LinkedList<String> csvFileNames;
	
	public Read(String constructorPath) { // use this constructor when you just want to use
		this.path = constructorPath;
	}

	public Read(LinkedList<String> constructorCSVFilePaths) {
		this.csvFileNames = constructorCSVFilePaths;
	}

	
	
    
    /*
     * This is for handling the data from CSVs.
     */
    
	// Used for Single Question and Answer.
	public LinkedList<String> readAllQuestionsFromCSV() { //Tested. This is correct.
	    LinkedList<String> questions = new LinkedList<>();
	    //System.out.println("Attempting to read questions from: " + path); // Debug print
	    try (BufferedReader br = new BufferedReader(new FileReader(path))) {
	        String line = br.readLine(); // Reads the header
	        while ((line = br.readLine()) != null) {  
	            StringTokenizer tokenizer = new StringTokenizer(line, ",");
	         // This will grab the questions from the 2nd column (Category_Q).
	            if (tokenizer.countTokens() > 1) {
	                tokenizer.nextToken(); // Skip the first column
	                questions.add(tokenizer.nextToken().trim());
	            }
	        }
	        //System.out.println("Questions read successfully!"); // Debug print
	    } catch (IOException e) {
	        e.printStackTrace(); 
	    }
	    return questions;
	}

	
	public LinkedList<String> readAllAnswersFromCSV() { //Tested. This is correct.
        LinkedList<String> answers = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // Read the header
	        while ((line = br.readLine()) != null) {
	            StringTokenizer tokenizer = new StringTokenizer(line, ",");
	            // This will grab the answers from the 3rd column (Category_A).
	            for (int i = 0; i < 2 && tokenizer.hasMoreTokens(); i++) {
	                tokenizer.nextToken(); // Skip the first two columns
	            }
	            if (tokenizer.hasMoreTokens()) {
	                answers.add(tokenizer.nextToken().trim());
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
        return answers;
    }

	
	//Used for Multiple Question and Answers.
	
	public LinkedList<LinkedList<String>> readQuestionsFromMultipleCSVs() { //Tested. This is correct.
	    LinkedList<LinkedList<String>> result = new LinkedList<>();
	    for (String csvFileName : csvFileNames) {
	        LinkedList<String> questions = new LinkedList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
	            String line = br.readLine(); // Read the header
	            while ((line = br.readLine()) != null) {
	                String[] parts = line.split(",");
	                if (parts.length >= 2) { 
	                    questions.add(parts[1].trim()); 
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace(); 
	        }
	        result.add(questions);
	    }
	    return result;
	}

	public LinkedList<LinkedList<String>> readAnswersFromMultipleCSVs() { //Tested. This is correct.
	    LinkedList<LinkedList<String>> result = new LinkedList<>();
	    for (String csvFileName : csvFileNames) {;
	        LinkedList<String> answers = new LinkedList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
	            String line = br.readLine(); // Read the header
	            while ((line = br.readLine()) != null) {
	                String[] parts = line.split(",");
	                if (parts.length >= 3) { 
	                    answers.add(parts[2].trim()); 
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace(); 
	        }
	        result.add(answers);
	    }
	    return result;
	}
	
	// For reading the highscore CSV
	public LinkedList<Highscore> readHighscores(String filePath) { // Tested. This is correct.
        LinkedList<Highscore> highscores = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read the header line to skip it
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int number = Integer.parseInt(data[2]);

                Highscore highscore = new Highscore(id, name, number);
                highscores.add(highscore);
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }

        return highscores;
    }
	
	String getLastLineFromCSV(String CSVPath) throws IOException {
        String lastLine = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(CSVPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
        }
        return lastLine;
    }
	
	public int getLastIDFromCSV(String CSVPath) {
        int lastID = 0;
        try {
            File file = new File(CSVPath);
            if (file.exists()) {
            	Read lastLineRead = new Read(CSVPath);
                String lastLine = lastLineRead.getLastLineFromCSV(CSVPath);
                if (!lastLine.isEmpty()) {
                    // Only works if CSV structure is: ID,Question,Answer
                    String[] values = lastLine.split(",");
                    lastID = Integer.parseInt(values[0]);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading from CSV file: " + e.getMessage());
        }
        return lastID;
    }
	
	



}
