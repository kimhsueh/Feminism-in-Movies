import java.io.File;
import java.io.IOException;
import java.util.*;
import java.lang.*;

/**
 * Represents an object of type Movie.
 * A Movie object has a title, some Actors, and results for the twelve Bechdel tests.
 *
 * @author (Audrey Yip, Jasmine Le, Kim Hsueh)
 * @version (3 Dec 2022)
 */
public class Movie implements Comparable <Movie>
{
    private String title;
    private Hashtable <Actor, String> roles; //Hashtable to store the roles of the Actors
    private Vector <Integer> testResults; //Vector to hold boolean evaluation for the 13 tests
    private int raw;
    private int category;
    private int total;

    /**
     * Constructor for class Movie.
     * Given the title of a movie it creates a Movie object
     * Raw, category, and total feminist scores are set in MovieCollection class.
     * @param title The title of the movie
     */
    public Movie (String title){
        this.title = title;
        roles = new Hashtable <Actor, String>();
        testResults = new Vector<Integer>();
    }

    /**
     * method to set the raw variable to its corresponding raw feminist score
     * @param num The score that the raw score is initialized to
     */
    public void setRaw(int num){
        raw = num;
    }

    /**
     * method to set the category score to its corresponding category score
     * @param num the score the category variable is initialized to
     */
    public void setCategory(int num){
        category = num;
    }

    /**
     * method to calculate the total score of the raw feminist score and the category score
     */
    public void setTotal(){
        total = raw + category;
    }

    /**
     * method to return the raw feminist score
     * @returns the raw feminist score
     */
    public int getRaw(){
        return raw;
    }

    /**
     * method to return the category score
     * @returns the total category score
     */
    public int getCategory(){
        return category;
    }

    /**
     * method that returns the total score
     * @returns the total feminist score with the raw feminist score and category score
     */
    public int getTotal(){
        return raw + category;
    }

    /**
     * method to read file line by line and adds the actor to the movie
     * @param movieFile the file that will be read line by line 
     */
    public void addAllActors(String movieFile){
        try {
            Scanner fileScan = new Scanner (new File(movieFile));
            // skip the first line
            fileScan.nextLine();
            while (fileScan.hasNext()) {
                String line = fileScan.nextLine();
                addOneActor(line);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Method that takes in input, generates an Actor, and adds obj to actors of this movie
     * @param input String as formatted in the input file ("nextBechdel_castGender.txt")
     * @return Actor object with name and actor gender read in from inputted file 
     */
    public Actor addOneActor(String input){
        // split line by ,"
        String[] line = input.split(",\"");
        for(int i = 0; i<line.length; i++)
            line[i] = line[i].replaceAll("\"", "");
        // index necessary information
        String movieName = line[0];
        // if actor is not in this movie, return null 
        if (! movieName.equals(title)) {
            return null;
        }
        String actorName = line[1];
        String actorGender = line[5];
        String actorRole = line[3];

        // create new Actor with name and gender
        Actor actor = new Actor(actorName, actorGender);
        // add Actor to roles hashtable
        roles.put(actor, actorRole);
        return actor;
    }

    /**
     * Tests this movie object with the input one and determines whether they are equal.
     * @param other an Object that will be casted to a Movie to be compared to this.movie
     * @return true if both objects are movies and have the same title, 
     * false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Movie) {
            // Need explicit (Movie) cast to use .title
            return this.title.equals(((Movie) other).title);
        } else {
            return false;
        }
    }

    /**
     * Method that takes the Actors from Hashtable roles and adds the names to a linked list
     * @returns a Linked List with all the actor names who played in this movie
     */
    public LinkedList<String> getActors(){
        //create new LinkedList
        LinkedList <String> actorNames = new LinkedList<String>();

        Enumeration<Actor> e = roles.keys();
        while( e.hasMoreElements()) {
            Actor anActor = e.nextElement();
            String info = roles.get(anActor);
            actorNames.add(anActor.getName());
        }
        return actorNames;
    }

    /**
     * Method that returns Hashtable roles
     * @returns the movie's actors and their roles in a hash table
     */
    public Hashtable getAllActors(){
        //create a new Hashtable
        //Hashtable <Actor,String> allActors = new Hashtable <Actor,String>();
        //roles.forEach((k,v) -> allActors.put(k,k.getGender()));
        //return allActors;
        return roles;
    }

    /**
     * @returns a Vector with all the Bechdel test results for this movie
     */
    public Vector getAllTestResults(){
        return testResults;
    }

    /**
     * @return the movie's title
     */
    public String getTitle(){
        return title;
    }

    /**
     * method populates testResults vector with 0 and 1 adding them by swapping the given
     * string values of 0s to 1s and 1s to 0s 
     * @param input which will take and split a line and add its content to a vector
     */
    public void setTestResults(String[] input){
        //we wanted to make the 1s the score that pass the test
        //for loop to iterate the input and change the values
        for (String testResult : input) {
            if (testResult.equals("0")) {
                testResults.add(1); 
            } else {
                testResults.add(0);  
            }
        }
    }

    /**
     * Method that determines and returns the movie's feminist scores. Contains the 
     * array of the weight of each test and takes the testResults Vector converting each
     * value to a integer and multiplying it by the value of the corresponding test
     * @return totalScore of the feminist scoring of all the tests
     */
    public int feministScore(){
        //the tests array below contains all the values we associated with the tests
        int[] tests = {10,13,4,16,13,4,12,9,9,10,10,16,4};
        int t = 0;
        int score = 0;

        //loop to multiple the scores with the test ranking values
        for (int i = 0; i < testResults.size(); i++){
            score += tests[t]*Integer.valueOf(testResults.get(i));
            t++;
        }
        return score;
    }

    /** 
     *  Method final compareTo(), takes in a movie object and compares it to the movie
     *  object that it is being called on. 
     *  It compares the raw score of each movie if inputted movie has a greater score it
     *  returns -1. If movie object it is being called on has a greater score it returns 1. 
     *  Otherwise if both have the same raw score the it compares the category score of each
     *  movie if inputted movie has a greater category score then it returns -1. 
     *  If movie that it is being called on has a greater category score it returns 1.
     *  Otherwise if they have the same category score it compares title of movie.
     *  If both have the same title then returns 0. 
     *  @param Movie movie object that will be compared to the movie object it is being
     *  called on 
     *  @return an integer value of either -1, 1, or 0. -1 if the inputted movie had a higher
     *  score, 
     *  1 if the movie object compareTo was being called on has a higher feminstScore. 
     *  0 if they had the same feminist score, category score, and had the same name. 
     */
    final public int compareTo(Movie movie2) {
        //compares the new feminist score
        if (this.raw- movie2.raw < 0){
            return -1;
        }
        if (this.raw - movie2.raw > 0){
            return 1;
        }

        //compares the new feminist score
        if (this.category- movie2.category < 0){
            return -1;
        }
        if (this.category - movie2.category > 0){
            return 1;
        }

        //if the above doesn't work, then we compare by alphabetical order
        if (this.getTitle().compareTo(movie2.getTitle()) < 0){
            return 1;
        }
        if (this.getTitle().compareTo(movie2.getTitle()) > 0){
            return -1;
        }
        return 0;
    }

    /**
     * Generates a categoryScore based on which categorys of tests that a movie passes 
     * It uses total to keep tally of which categories that the movie passes and then mutiples the total value by 2
     * @return integer of cateogory score  
     */
    public int CategoryScore(){
        int bechdel = 0;
        int behindCamera = 0;
        int intersectional = 0;
        int protagonists = 0;
        int supporting = 0;
        int cateogory = 0;

        //iterate testResults vector
        for (int i = 0; i < testResults.size(); i++ ) {
            if(i == 0){
                if (testResults.get(i)==1 && bechdel == 0){
                    bechdel += 1;
                    cateogory += 1;}
            }
            if(i == 1 || i == 2 || i == 4){
                if (testResults.get(i)==1 && protagonists == 0){
                    protagonists += 1;
                    cateogory += 1;}
            }
            if(i == 3 || i == 5 || i == 9){
                if (testResults.get(i)==1 && supporting == 0){
                    supporting += 1;
                    cateogory += 1;}
            }
            if(i == 6 || i == 7 || i == 8){
                if (testResults.get(i)==1 && intersectional == 0){
                    intersectional += 1;
                    cateogory += 1;}
            }
            if(i == 10 || i == 11 || i == 12){
                if (testResults.get(i)==1 && behindCamera == 0){
                    behindCamera += 1;
                    cateogory += 1;}
            }
        }
        return cateogory*2;
    }

    /**
     * Method that checks whether the supporting cast is 50 percent women using the movie's roles hashtable (Koeze Dottle Test)
     * Called on a cast file it creates Actor objects and for each line if the line actor has a gender of male or female it 
     * adds it to the total, if says supporting it is not added. Then taking the total score divided by the female score(containing the tally of how many females are in cast) 
     * 
     * @return "0" if movie passes the test, "1" otherwise
     */
    public String kdTest(){
        Enumeration<Actor> e = roles.keys();
        int total = 0;
        int female = 0;
        while(e.hasMoreElements()) {   
            Actor actor = e.nextElement();
            String role = roles.get(actor);
            if (role.equals("Supporting") && !actor.getGender().equals("Unknown")) {
                total++;
                if (actor.getGender().equals("Female")) {
                    female++;
                }
            }
        }

        System.out.println("This movie has " + total + " supporting actors of known gender, " + female + " of which are female.");

        float ratio = total/female;
        if (ratio >= 2) {
            return "0";
        }

        return "1";
    }

    /**
     * Method toString()
     * @return s string representation of Movie 
     */
    public String toString(){
        String s = title + " with a raw feminist score of " + this.raw + " and a bonus of "+
            this.category ;

        return s;
    }

    /**
     * main method for testing
     */
    public static void main (String[] args){
        // constructing movies for testing
        Movie testMadea = new Movie("Boo! A Madea Halloween");
        Movie testAlpha = new Movie("Alpha");

        System.out.println("---Testing addOneActor()---");
        System.out.println("Test String: \"Boo! A Madea Halloween\",\"Diamond White\",\"Tiffany\",\"Supporting\",\"8\",\"Female");
        System.out.println("Expected Output: Diamond White (Female)");
        System.out.println("Actual Output: " + testMadea.addOneActor("Boo! A Madea Halloween\",\"Diamond White\",\"Tiffany\",\"Supporting\",\"8\",\"Female"));

        System.out.println("\n---Testing addAllActors()---");
        System.out.println("Test File: small_castGender.txt");
        testAlpha.addAllActors("data/small_castGender.txt");
        System.out.println("Expected Output: Hashtable with Tyler, Cassi, Patrice, and Stella");
        System.out.println("Actual Output: " + testAlpha.roles);

        System.out.println("\n----Testing getActors() and getAllActors()----");
        LinkedList<String> test = testAlpha.getActors();
        System.out.println("Expected Output getActors(): [Patrice Lovely, Cassi Davis,Stella, Tyler Perry]");
        System.out.println("Actual Output: " + test);
        Hashtable<Actor,Actor> testing = testAlpha.getAllActors();
        System.out.println("Expected Output getAllActors(): Hashtable with Tyler, Cassi,Patrice, and Stella");
        System.out.println("Actual Output: " + testing);

        System.out.println("\n----Testing setTestResults()----");
        System.out.println("Input: \"0,0,0,1,0,0,0,1,0,0,1,1,1\"");
        String[] testingAlpha = {"0","0","0","1","0","0","0","1","0","0","1","1","1"};
        testAlpha.setTestResults(testingAlpha);
        System.out.println("Expected Output: [0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1]");
        System.out.println("Actual Output: " + testAlpha.testResults);

        System.out.println("\n----Testing toString()----");
        System.out.println(testAlpha);

        System.out.println("\n----Testing feministScore()----");
        String testingLineAlpha = "Alpha,0,0,0,1,0,0,0,1,0,0,1,1,1";
        System.out.println(testingLineAlpha);
        System.out.println("Expected Output: 75");
        System.out.println("Actual Output: " +  testAlpha.feministScore());

        Movie testBeta = new Movie("Beta");
        String testingLineBeta = "Beta,0,0,0,1,0,0,0,1,0,0,1,1,1";
        String[] testingBeta = {"0","0","0","1","0","0","0","1","0","0","1","1","1"};
        testAlpha.setTestResults(testingBeta);
        System.out.println(testingLineBeta);
        System.out.println("Expected Output: 0");
        System.out.println("Actual Output: " +  testBeta.feministScore());

        Movie testGamma = new Movie("Gamma");
        String testingLineGamma = "Gamma,0,0,0,1,0,0,0,1,0,0,1,1,1";
        String[] testingGamma = {"0","0","0","1","0","0","0","1","0","0","1","1","1"};
        testAlpha.setTestResults(testingGamma);
        System.out.println(testingLineGamma);
        System.out.println("Expected Output: 0");
        System.out.println("Actual Output: " +  testGamma.feministScore());

        System.out.println("\n----Testing two movies with a raw feminist score of 40----");
        Movie testIA = new Movie("Ice Age: Collision Course");
        String []testingIA = {"0","1","0","1","1","1","0","1","1","0","1","1","0"};

        testIA.setTestResults(testingIA);
        Movie testStorks = new Movie("Storks");
        String[]testingStorks = {"1","0","1","1","0","1","1","1","1","0","1","1","0"};
        testStorks.setTestResults(testingStorks);

        System.out.println("Expected Output: -1 (where Ice Age has a smaller feminist score than Storks");
        System.out.println("Actual Output: " + testIA.compareTo(testStorks));

        System.out.println("\n----Testing two movies with a total feminist score of 62----");
        Movie testArrival = new Movie("Arrival");
        String[]testingArrival = {"0","0","1","0","0","1","1","1","1","0","1","1","1"};
        testArrival.setTestResults(testingArrival);

        Movie testGonT = new Movie("The Girl on the Train");
        String[]testingGonT = {"0","0","1","0","0","1","1","1","1","0","1","1","1"};
        testGonT.setTestResults(testingGonT);

        System.out.println("Expected Output: 1 (where Arrival is lexicographically first)");
        System.out.println("Actual Output: " + testArrival.compareTo(testGonT));

    }
}