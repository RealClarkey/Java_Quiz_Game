import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Delete {

    void deleteRowFromCSV(int rowID, String csvPath) {
        try {
            // Reading the existing CSV file
            BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
            List<String> lines = new ArrayList<>();
            String line;

            // Read and store the header separately
            String header = csvReader.readLine();
            lines.add(header);

            while ((line = csvReader.readLine()) != null) {
                lines.add(line);
            }

            csvReader.close();

            // Remove the specified rowID.
            if (rowID >= 0 && rowID < lines.size()) {
                lines.remove(rowID);
            } else {
                System.out.println("Invalid rowID. No row deleted.");
                return;
            }

            // Update Question IDs in the remaining lines
            for (int i = 1; i < lines.size(); i++) {
                String[] parts = lines.get(i).split(",");
                parts[0] = String.valueOf(i); // Update the Question ID
                lines.set(i, String.join(",", parts));
            }

            // Now writing the modified content back to the CSV.
            BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csvPath));
            for (String updatedLine : lines) {
                csvWriter.write(updatedLine);
                csvWriter.newLine();
            }
            csvWriter.close();


        } catch (IOException e) {
            e.printStackTrace(); // Error handling goes here.
        }
    }
}