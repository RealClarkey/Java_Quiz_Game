# Trivial Pursuit-Inspired Java Quiz Game

This project is a modified version of *Trivial Pursuit*, developed as a university coursework assignment. It showcases principles of object-oriented programming, unit testing, GUI development, and integration with both CSV files and MySQL databases.

##  Game Overview

- Roll a dice to randomly select one of six quiz categories.
- Answer a question from that category.
- Continue rolling and answering until you either reach a specified number of correct answers or lose all your lives.
- Questions are loaded from CSV files or a MySQL database and are not repeated within the same game.

##  Features

###  Core Functionality
- Object-Oriented Design following SOLID principles.
- Read/write/delete questions using both CSV files and MySQL.
- Customisable number of questions and lives.
- Fully unit-tested using JUnit.

###  Graphical User Interface
- Java Swing GUI with menus for both players and admins.
- Visual feedback for dice rolls, score, and game status.
- **Custom Board Feature**: A circular board UI with a player icon that moves based on dice rolls.

###  Circular Board Movement
The GUI includes a rotating board mechanic where:
- A player icon is drawn on a circular path.
- Each dice roll moves the player along that circle by a fixed angle (e.g., 30° per position).
- Movement is calculated using `Math.cos()` and `Math.sin()` to determine new (x, y) coordinates based on the angle and radius.

This adds a dynamic, board-game feel to the otherwise text-heavy quiz interface.

###  Admin Tools
- Add single or multiple questions to specific CSVs or MySQL tables.
- Remove questions by row ID.
- GUI buttons and input fields to manage all quiz content visually.

###  High Score Table
- View, sort, and reset high scores.
- Supports both CSV and MySQL storage.

## Key Files

| File/Class             | Purpose                                      |
|------------------------|----------------------------------------------|
| `Read.java`            | Reads from CSV files                         |
| `Write.java`           | Writes to CSV files                          |
| `Delete.java`          | Deletes from CSV files                       |
| `DBConnector.java`     | Manages MySQL connections                    |
| `DBCredentials.java`   | Stores protected DB credentials              |
| `GameFunctions.java`   | Core game logic (dice roll, check answer...) |
| `GameSetup.java`       | Runs the game loop                           |
| `Gui.java`             | Main Swing GUI interface                     |
| `BoardPositions.java`  | Handles circular player movement             |

##  Testing

Each method includes at least 2 unit tests. Tests cover:
- CSV loading and data validation
- Adding/removing questions
- Game logic (dice rolls, answer checking)
- GUI actions and database calls

##  Requirements

- Java 11+
- XAMPP (for local MySQL testing)
- JUnit 5 (for testing)

##  How to Run

### CLI Version
1. Compile and run `Main.java`
2. Follow the prompts to set up the game and play via terminal.

### GUI Version
1. Run `Gui.java`
2. Enter player name, then select "Play Game" or "Admin" mode
3. Use the graphical interface to play or manage questions

>  If using MySQL, make sure your XAMPP server is running and the credentials in `DBCredentials.java` are correct.

##  Screenshots (Optional)
Main Menu:
<img width="1181" height="759" alt="Main Menu" src="https://github.com/user-attachments/assets/3f5a3f3d-9f9e-4d28-ac45-9a43f9dae576" />
Admin Menu:
<img width="1182" height="763" alt="admin-panel" src="https://github.com/user-attachments/assets/002af1fb-777e-42bc-badd-d142293c6412" />
Game board:
<img width="1182" height="759" alt="Main-board" src="https://github.com/user-attachments/assets/8a67a342-2a17-4869-8f8e-c31a0f912586" />
Asking Question:
<img width="1182" height="759" alt="asking-question" src="https://github.com/user-attachments/assets/06fca7a3-208c-48e2-aa8c-2a10f55cfdf4" />

**Author**: Leigh Clarke  
**Module**: University Coursework – Software Engineering Portfolio  
**Project**: Cost-Effective Trivial Pursuit-Inspired Quiz Game

