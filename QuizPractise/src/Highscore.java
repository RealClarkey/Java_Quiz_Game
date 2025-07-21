import java.util.Collections;
import java.util.LinkedList;

public class Highscore {
        private int id;
        private String name;
        private int number;

        public Highscore(int id, String name, int number) {
            this.id = id;
            this.name = name;
            this.number = number;
        }

        // Getter methods

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getNumber() {
            return number;
        }
        
        public void setId(int id) {
            this.id = id;
        }
        
      // New method to add a new score and rearrange highscores
        public static void addHighscore(LinkedList<Highscore> highscores, String playerName, int playerScore) {
            // Create a new highscore
            Highscore newHighscore = new Highscore(0, playerName, playerScore);

            // Add the new highscore
            highscores.add(newHighscore);

            // Sort the highscores in descending order based on the score
            Collections.sort(highscores, (hs1, hs2) -> Integer.compare(hs2.getNumber(), hs1.getNumber()));

            // Update the IDs based on the sorted order
            for (int i = 0; i < highscores.size(); i++) {
                highscores.get(i).setId(i + 1);
            }

            // Keep only the top 10 highscores
            if (highscores.size() > 10) {
                highscores.subList(10, highscores.size()).clear();
            }

            //System.out.println(newHighscore);
        }

        
        
        
        
    }
