import java.util.*;
import java.io.*;
import javafoundations.PriorityQueue;

/**
 * Represents a collection of movies. Uses a LinkedList to hold the movie 
 * objects. Movie data (title and test results) are coming from a file 
 * named "nextBechdel_allTests.txt". Data regarding actors who participated 
 * in each movie is read from a file named "nextBechdel_castGender.txt". 
 *
 * @author (Audrey Yip, Jasmine Le, Kim Hsueh)
 * @version (5 Dec 2022)
 */
public class MovieCollection
{
    // instance variables
    private LinkedList<Movie> allMovies;
    private LinkedList<Actor> allActors;
    private String testsFileName;
    private String castsFileName;
    /**
     * Constructor for objects of class MovieCollection
     * 
     * @param testsFileName file with all movie test results
     * @param castsFileName file with casts for all movies
     */
    public MovieCollection(String testsFileName,String castsFileName)
    {
        this.testsFileName = testsFileName;
        this.castsFileName = castsFileName;
        allMovies = new LinkedList<Movie>();
        allActors = new LinkedList<Actor>();
        readMovies();
        readCasts();
    }

    /**
     * Returns all the movies in a LinkedList
     * 
     * @return LinkedList with all Movies, each with title, actors and Bechdel test results.
     */
    public LinkedList<Movie> getMovies(){
        return allMovies;
    }

    /**
     * Returns the titles of all movies in the collection
     * 
     * @return a LinkedList of strings of the titles of all the movies
     */
    public LinkedList<String> getMovieTitles(){
        LinkedList<String> titles = new LinkedList<String>();
        // get title of each movie in allMovies, add to linked list
        for (Movie movie: allMovies) {
            titles.add(movie.getTitle());
        }
        return titles;
    }

    /**
     * Returns all the actors in a LinkedList
     * 
     * @return a LinkedList with all the Actors, each complete with their name and gender.
     */
    public LinkedList<Actor> getActors(){
        return allActors;
    }

    /**
     * Returns the names of all actors in the collection
     * @return a LinkedList of strings of the names of all actors
     */
    public LinkedList<String> getActorNames(){
        LinkedList<String> names = new LinkedList<String>();
        // get title of each movie in allMovies, add to linked list
        for (Actor actor: allActors) {
            names.add(actor.getName());
        }
        return names;
    }

    /**
     * Reads the input file, and uses its first column (movie title) to 
     * create all movie objects. Adds the included information on the 
     * Bechdel test results to each movie.
     */
    private void readMovies()
    {
        try {
            Scanner testScan = new Scanner (new File(testsFileName));
            // skip the first line
            testScan.nextLine();
            while (testScan.hasNext()) {
                String line = testScan.nextLine();
                // save movie title in variable
                String[] movieInfo = line.split(",");
                String title = movieInfo[0];
                // construct new movie for each line with title
                Movie temp = new Movie(title);
                /*
                String testString = "";
                for (int i = 1; i < movieInfo.length; i++){
                if (i == movieInfo.length -1){
                testString += movieInfo[i];
                }else{
                testString += movieInfo[i] + ",";
                }
                }
                 */
                // add the rest of the String Array to testResults
                temp.setTestResults(Arrays.copyOfRange(movieInfo, 1,14));
                temp.setRaw(temp.feministScore());
                temp.setCategory(temp.CategoryScore()) ;
                temp.setTotal();
                // add movie to allMovies
                allMovies.add(temp); // points to null
            }
            testScan.close();            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Reads the casts for each movie, from input casts file
     */
    private void readCasts()
    {
        for (int i = 0; i < allMovies.size(); i++) {
            Movie movie = allMovies.get(i);
            movie.addAllActors(castsFileName);
        }
    }

    /**
     * Returns a list of all Movies that pass the n-th Bechdel test
     * @param n index of Bechdel test
     * @return the Linkedlist of movie objects that passed the n-th Bechdel test
     */
    public LinkedList<Movie> findAllMoviesPassedTestNum​(int n)
    {
        LinkedList<Movie> passedTest = new LinkedList<Movie>();
        // access testResults vector, if nth test is passed add it to list
        for (Movie movie : allMovies) {
            if (movie.getAllTestResults().get(n).equals(1)) {
                passedTest.add(movie);
            }
        }
        return passedTest;
    }

    /**
     * method that checks which movies pass the Bechdel test
     * @return passedBechdelTest a linkedList of all the movies that passed the BechdelTest
     */
    public LinkedList<Movie> passedBechdel(){
        LinkedList<Movie> passedBechdelTest = new LinkedList<Movie>();
        // access testResults vector, if nth test is passed add it to list
        for (Movie movie : allMovies) {
            if (movie.getAllTestResults().get(0).equals(1)) {
                passedBechdelTest.add(movie);
            }
        }
        return passedBechdelTest;
    }

    /**
     * method that checks if movie passes the Peirce or Landau test
     * @return passPLTest a linkedList of the movies that pass either the Peirce or Landau or both tests 
     */
    public LinkedList<Movie> passedPeirceorLandau(){
        LinkedList<Movie> passedPLTest = new LinkedList<Movie>();
        // access testResults vector, if nth test is passed add it to list
        for (Movie movie : allMovies) {
            if (movie.getAllTestResults().get(1).equals(1) ||
            movie.getAllTestResults().get(2).equals(1)) {
                passedPLTest.add(movie);
            }
        }
        return passedPLTest;
    }

    /**
     * method that checks if movie passes the White test and not the Rees-Davis test
     * @return passedWTest a linkedList of the movies that passed the White test and not the Rees-Davis test
     */
    public LinkedList<Movie> passedWhiteNotRees(){
        LinkedList<Movie> passedWTest = new LinkedList<Movie>();
        // access testResults vector, if nth test is passed add it to list
        for (Movie movie : allMovies) {
            if (movie.getAllTestResults().get(11).equals(1) &&
            movie.getAllTestResults().get(12).equals(0)) {
                passedWTest.add(movie);
            }
        }
        return passedWTest;
    }

    /**
     * Returns a PriorityQueue of movies in the provided data based on their 
     * feminist score.
     * @return q a PriorityQueue of movies in the provided data based on their 
     * feminist score
     */
    public PriorityQueue<Movie> rankMovies(){
        PriorityQueue<Movie> q = new PriorityQueue<Movie>();
        for (int i = 0; i < allMovies.size(); i++) {
            q.enqueue(allMovies.get(i));
        }
        return q;
        }

    /**
     * Method toString()
     * @return string representation of MovieCollection
     */
    public String toString(){
        String s = "This collection has " + allMovies.size() + " movies:";

        for (int i = 0; i < allMovies.size(); i++) { //null pointer
            s = s + "\n" + allMovies.get(i).getTitle() + " and has a raw feminist score of " + allMovies.get(i).feministScore();
        }

        return s;
    }

    /**
     * Main method for testing
     */
    public static void main (String[] args){
        System.out.println("---Testing MovieCollection constructor and its getter methods()---");
        
        MovieCollection test = new MovieCollection("data/small_allTests.txt", 
                "data/small_castGender.txt");
        
        //System.out.println(test);
        System.out.println("\n---Testing getMovies() with the small allTests---");
        System.out.println(test.getMovies());
        
        System.out.println("\n---Testing getMovieTitles() with the small allTests---");
        System.out.println(test.getMovieTitles());
        
        System.out.println("\n---Testing readCasts() and the toString() with the small allTests---");
        System.out.println("Expecting the names to repeat three times, since all movie has the same actors:");
        System.out.println(test.allActors);
        
        MovieCollection file1 = new MovieCollection
            ("data/nextBechdel_allTests.txt", "data/nextBechdel_castGender.txt");
        //System.out.println("\n"+file1);
        
        System.out.println("\n---Testing findAllMoviesPassedTestNum()---");
        LinkedList<Movie> AllMovieTesting = file1.findAllMoviesPassedTestNum(4);
        System.out.println("Expected Output: 27");
        System.out.println("Actual Output: " + AllMovieTesting.size());
        
        System.out.println("\n---Testing passedBechdel()---");
        LinkedList<Movie> BechdelTesting = file1.passedBechdel();
        System.out.println("Expected Output: 32");
        System.out.println("Actual Output: " + BechdelTesting.size());
        System.out.println("\nThe following movies passed the Bechdel Test:");
        if (BechdelTesting.size()> 0){
            for (Movie movie : BechdelTesting) {
                System.out.println(movie.getTitle());
            }
        }
        else {System.out.println("None of the movies pass.");}
        
        System.out.println("\n---Testing passedPeirceorLandau()---");
        LinkedList<Movie> PeirceLandauTesting = file1.passedPeirceorLandau();
        System.out.println("Expected Output: 47");
        System.out.println("Actual Output: " + PeirceLandauTesting.size());
        System.out.println("\nThe following movies passed the Peirce Test or the Landau Test:");
        if (PeirceLandauTesting.size()> 0){
            for (Movie movie : PeirceLandauTesting) {
                System.out.println(movie.getTitle());
            }
        }
        else {System.out.println("None of the movies pass.");}
        
        System.out.println("\n---Testing passedWhiteNotRees()---");
        LinkedList<Movie> WhiteReesTesting = file1.passedWhiteNotRees();
        System.out.println("Expected Output: 0");
        System.out.println("Actual Output: " + WhiteReesTesting.size());
        System.out.println("\nThese are the movies passed the White Test and not the Rees-Davies Test:");
        if (WhiteReesTesting.size()> 0){
            for (Movie movie : WhiteReesTesting) {
                System.out.println(movie.getTitle());
            }
        }   
        else {System.out.println("None of the movies pass.");}
        
        
        System.out.println("\n---Testing KDtest() from Movie class---");
        System.out.println("Testing on Bad Moms");
        Movie badMoms = test.allMovies.get(0);
        System.out.println("Expected Output: 1");
        System.out.println("Actual Output: " + badMoms.kdTest());
        
        System.out.println("\n---Testing CompareTo() from Movie class---");
        PriorityQueue<Movie> PQtest= file1.rankMovies();
        System.out.println(PQtest);
    }
}
