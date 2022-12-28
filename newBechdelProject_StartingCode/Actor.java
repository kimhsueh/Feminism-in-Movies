/**
 * Represents an object of type Actor. An Actor has a name and a gender.
 *
 * @author (Audrey Yip, Jasmine Le, Kim Hsueh)
 * @version (3 Dec 2022)
 */
public class Actor
{
    private String name;
    private String gender;

    /**
     * Constructor for class Actor
     * @param name the name of the actor
     * @param gender the gender of the actor
     */
    public Actor(String name, String gender){
        this.name = name;
        this.gender = gender;
    }

    /**
     * Returns the name of the actor
     * @return name, the name of the actor
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the the new name of the actor
     * @param newName, the new name of the actor
     */
    public void setName(String newName){
        this.name = newName;
    }

    /**
     * Returns the gender of the actor
     * @return gender, the gender of the actor
     */
    public String getGender(){
        return gender;
    }

    /**
     * Sets the gender of the actor
     * @param newGender, the new gender of the actor
     */
    public void setGender(String newGender){
        this.gender = newGender;
    }

    /**
     * Method toString()
     * @return s string representation of Actor
     */
    public String toString(){
        //String s = "We are currently looking at actor " + name + " who is " + gender + ". ";
        String s = name + ", " + gender;
        return s;
    }

    /**
     * Defined b/c Actor (mutable) is used as a key in a Hashtable.
     * It makes sure that the same Actors have always the same hash code.
     * So, the hash code of any object that is used as key in a hash table,
     * has to be produced on an *immutable* quantity,
     * like a String (such a string is the name of the actor in our case)
     * 
     * @return an integer, which is the hash code for the name of the actor
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Tests this actor with input one and determines whether they are equal.
     * Two actors are considered equal if they have the same name and gender.
     * 
     * @param other an Object that will be casted to a Action to be compared to this name and gender of an Actor
     * @return true if both objects are of type Actor, 
     * and have the same name and gender, false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Actor) {
            return this.name.equals(((Actor) other).name) && 
            this.gender.equals(((Actor) other).gender); 
            // Need explicit (Actor) cast to use .name
        } else {
            return false;
        }
    }

    /**
     * main method for testing
     */
    public static void main (String[] args){
        Actor s1 = new Actor("Bud M", "Male");
        Actor s2 = new Actor("Winnie M", "Male");
        Actor s3 = new Actor("Jennifer K", "Female");
        Actor s4 = new Actor("Kim H", "Male");
        Actor s5 = new Actor("Audrey Y", "Female");

        System.out.println("~~~~~~~Creating new actors and printing them out~~~~~~");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(s4);
        System.out.println(s5+"\n");

        System.out.println("~~~~~~~Testing setGender() method~~~~~~");
        s5.setGender("Male");
        System.out.println("Changing Actor Audreys gender to Male--> Expected: Male Actual: " +
            s5.getGender());
        s4.setGender("Female");
        System.out.println("Changing Actor Kims gender to Female--> Expected: Female Actual:" + s4.getGender()+ "\n");

        System.out.println("~~~~~~~Testing getGender() method~~~~~~");
        System.out.println("Getting Actor Audreys gender Expected: Male Actual: " +
            s5.getGender());
        System.out.println("Getting Actor Jennifer’s gender Expected: Female Actual: " +  s3.getGender()+"\n");

        System.out.println("~~~~~~~Testing getName() method~~~~~~");
        System.out.println("Getting Actor Winnies name Expected: Winnies M Actual: " +
            s2.getName());
        System.out.println("Getting Actor Buds name Expected: Bud M Actual: " + s1.getName()+"\n");

        System.out.println("~~~~~~~Testing hashCode() method~~~~~~");
        System.out.println("HashCode for Actor Bud :" + s1.hashCode());
        System.out.println("HashCode for Actor Winnie :" + s2.hashCode());
        System.out.println("HashCode for Actor Jennifer :" + s3.hashCode());
        System.out.println("HashCode for Actor Kim :" + s4.hashCode());
        System.out.println("HashCode for Actor Audreys :" + s5.hashCode()+"\n");

        System.out.println("~~~~~~~Testing equals() method~~~~~~");
        System.out.println("Bud equals to Winnie (false): " + s1.equals(s2));
        System.out.println("Winnie equals to Bud (false): " + s2.equals(s1));
        System.out.println("Audrey equals to Kim (false): " + s5.equals(s4));
        System.out.println("Kim equals to Kim (true): " + s5.equals(s5));
    }
}