import java.util.LinkedList;

public class GameSetup {
	
    // Initializing LinkedLists without data
    private LinkedList<String> csvFileNames = new LinkedList<>();
    private LinkedList<LinkedList<String>> questions = new LinkedList<>();
    private LinkedList<LinkedList<String>> answers = new LinkedList<>();
	
     
	public GameSetup() {
//		csvFileNames.add("food.csv");
//        csvFileNames.add("geography.csv");
//        csvFileNames.add("history.csv");
//        csvFileNames.add("movie.csv");
//        csvFileNames.add("science.csv");
//        csvFileNames.add("sport.csv");
//		
//		Read reader = new Read(csvFileNames);
//		
//		questions = new LinkedList<LinkedList<String>>();
//		answers = new LinkedList<LinkedList<String>>();
//		
//		questions = reader.readQuestionsFromMultipleCSVs();
//		answers = reader.readAnswersFromMultipleCSVs();
		
		
		String[] databaseTableNames = {"Food", "Geography", "History", "Movie", "Science", "Sport",};
		SQLRead dbReader = new SQLRead();
        
		for (String tableName : databaseTableNames) {
            // Read questions
			//readFromDatabase(String columnName, String[] tableNames)
            questions.addAll(dbReader.readFromDatabaseQs(databaseTableNames));

            // Read answers
            answers.addAll(dbReader.readFromDatabaseAs(databaseTableNames));
		}
		
	}
	
	
	
	// Returning a Category depending upon player position. Used for determine Q category.
    String getCategoryForPosition(int position) {
        if (position == 4 || position == 10 || position == 13 || position == 23) {
            return "Food";
        } else if (position == 5 ||position == 3 || position == 8 || position == 11) {
            return "Geography";
        } else if (position == 2|| position == 7 || position == 12 || position == 17) {
            return "History";
        } else if (position == 1 ||position == 16|| position == 18 || position == 22) {
            return "Movie";
        } else if (position == 9 ||position == 6|| position == 14 || position == 20) {
            return "Science";
        } else {
            return "Sport";
        }
    }
	
	
	
	// Used to grab question based on category. Will pick a RandomQ.
	String getQuestionForCategory(String category, int RandomNumber) {
		int randomNumberIndex = RandomNumber;	
    	
    	if(category == "Food") {
    		String foodQuestion = questions.get(0).get(randomNumberIndex);
    		return foodQuestion;
    	} else if(category == "Geography") {
    		String geographyQuestion = questions.get(1).get(randomNumberIndex);
    		return geographyQuestion;
    	} else if(category == "History") {
    		String historyQuestion = questions.get(2).get(randomNumberIndex);
    		return historyQuestion;
    	} else if(category == "Movie") {
    		String movieQuestion = questions.get(3).get(randomNumberIndex);
    		return movieQuestion;
    	} else if(category == "Science") {
    		String scienceQuestion = questions.get(4).get(randomNumberIndex);
    		return scienceQuestion;
    	} else if(category == "Sport") {
    		String sportQuestion = questions.get(5).get(randomNumberIndex);
    		return sportQuestion;
    	}        
		return category;
    }
	
	//determining the remaining rows in a linkedlist category. used for generating random numbers.
	public int getRemainingRowsCount(String category) {
        int index = getCategoryIndex(category);
        return questions.get(index).size();
    }
	
	//This is used to determine the category index. used for generating random numbers.
	public int getCategoryIndex(String category) {
        switch (category) {
        case "Food":
            return 0;
        case "Geography":
            return 1;
        case "History":
            return 2;
        case "Movie":
            return 3;
        case "Science":
            return 4;
        case "Sport":
            return 5;
        default:
            return -1; // Handle the case where the category is not found
    }
}



	public String getAnswerForCategory(String category, int RandomNumber) {
		int randomNumberIndex = RandomNumber;	
    	
    	if(category == "Food") {
    		String foodAnswer = answers.get(0).get(randomNumberIndex);
    		return foodAnswer;
    	} else if(category == "Geography") {
    		String geographyAnswer = answers.get(1).get(randomNumberIndex);
    		return geographyAnswer;
    	} else if(category == "History") {
    		String historyAnswer = answers.get(2).get(randomNumberIndex);
    		return historyAnswer;
    	} else if(category == "Movie") {
    		String movieAnswer = answers.get(3).get(randomNumberIndex);
    		return movieAnswer;
    	} else if(category == "Science") {
    		String scienceAnswer = answers.get(4).get(randomNumberIndex);
    		return scienceAnswer;
    	} else if(category == "Sport") {
    		String sportAnswer = answers.get(5).get(randomNumberIndex);
    		return sportAnswer;
    	}        
		return category;
    }
	
	public void removeUsedQuestion(String category, int index) {
	    
	    if ("Food".equals(category)) {
	        questions.get(0).remove(index);
	        answers.get(0).remove(index);
	        System.out.println("Food size: " + questions.get(0).size());
	    } else if ("Geography".equals(category)) {
	        questions.get(1).remove(index);
	        answers.get(1).remove(index);
	        System.out.println("Geography size: " + questions.get(1).size());
	    } else if ("History".equals(category)) {
	        questions.get(2).remove(index);
	        answers.get(2).remove(index);
	        System.out.println("History size: " + questions.get(2).size());
	    } else if ("Movie".equals(category)) {
	        questions.get(3).remove(index);
	        answers.get(3).remove(index);
	        System.out.println("Movie size: " + questions.get(3).size());
	    } else if ("Science".equals(category)) {
	        questions.get(4).remove(index);
	        answers.get(4).remove(index);
	        System.out.println("Science size: " + questions.get(4).size());
	    } else if ("Sport".equals(category)) {
	        questions.get(5).remove(index);
	        answers.get(5).remove(index);
	        System.out.println("Sport size: " + questions.get(5).size());
	    }	    
	}
	
	
}
