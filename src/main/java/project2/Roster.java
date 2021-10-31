/**
 * The Roster class is used to instantiate Roster objects, which are growable lists of Students. Roster has data fields
 * and operations related to the manipulation of this list. Such operations include adding Students, removing them,
 * and checking if the Roster is empty.
 * @author Adams, Jennifer
 */

public class Roster {

    private Student[] roster;
    private int size; //keep track of the number of students in the roster

    /**
     * Default constructor for the Roster class, creates a Student array of size 4 with 0 Student objects initially
     * in it.
     */
    public Roster(){
        size = 0;
        roster = new Student[4];
    }

    /**
     * Used to check if the inputted Student object is in the Roster
     * @param student student to look for
     * @return
     */
    public boolean isHere(Student student){
        for( int c = 0; c < size; c++ ){
            if( roster[c].equals(student) ){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns position of given Student in the roster
     * @param student
     * @return
     */
    public Student placement(Student student){
        for( int c = 0; c < size; c++ ){
            if( roster[c].equals(student) ){
                return roster[c];
            }
        }
        return student;
    }

    /**
     * Returns true if the roster is empty and false otherwise
     * @return
     */
    private boolean isEmpty(){
        if( size == 0 ){
            return true;
        }
        return false;
    }

    /**
     * This method is called whenever the Roster has reached capacity. Its function is to increase the array size by
     * 4 and it accomplishes this by copying the old array into a new, longer one.
     */
    private void grow() {
        int newLength = roster.length + 4;
        Student[] biggerRoster = new Student[newLength + 4];
        for( int c = 0; c < size; c++ ){
            biggerRoster[c] = roster[c];
        }
        this.roster = biggerRoster;
    }

    /**
     * Checks if the inputted Student object is already in the Roster and if not, adds them to the last open spot.
     * Calls grow if the Roster is full.
     * @param student student to be added
     * @return
     */
    public boolean add(Student student) {
        if( isHere(student) ){
            return false;
        }
        if( roster.length == size ){
            grow();
        }
        roster[size] = student;
        size++;
        return true;
    }

    /**
     * Removes the inputted Student object from the Roster if they are in the Roster.
     * @param student student to be removed
     * @return boolean that lets the caller know if the removal was successful.
     */
    public boolean remove(Student student) {
        if( isEmpty() || !isHere(student) ){
            return false;
        }
        int newRosterPtr = 0;
        Student[] newRoster = new Student[roster.length];
        for( int c = 0; c < size; c++ ){
            if( roster[c].equals(student) ){
                continue;
            }
            newRoster[newRosterPtr] = roster[c];
            newRosterPtr++;
        }
        this.roster = newRoster;
        size--;
        return true;
    }

    /**
     * If the Roster is not empty, prints the full string representation of each Student.
     */
    public void print(){
        if( isEmpty() ){
            System.out.println("Student roster is empty!");
        }else {
            System.out.println("* list of students in the roster **");
            for (int c = 0; c < size; c++) {
                System.out.println(roster[c].fullStringRep());
            }
            System.out.println("* end of roster **");
        }
    }

    /**
     * Sorts the Roster by the name of each Student.
     * @param roster roster to be sorted
     */
    private void sortByName(Student[] roster){
        for( int i = 0; i < size; i++ ){
            int earliest = i;
            for(int j = i+1; j < size; j++){
                if( roster[earliest].getProfile().getName().compareTo(roster[j].getProfile().getName()) > 0 ){
                    earliest = j;
                }
            }
            swap(roster, i, earliest);
        }
    }

    /**
     * Prints the full string representation of each Student in the Roster.
     */
    public void printByName(){
        if( isEmpty() ){
            System.out.println("Student roster is empty!");
        }else {
            sortByName(roster);
            for (int c = 0; c < size; c++) {
                System.out.println(roster[c].fullStringRep());
            }
        }
    }

    /**
     * Sorts the Roster by each Student object's associated last payment date.
     * @param roster roster to be sorted
     */
    private void sortByDate(Student[] roster){
        for( int i = 0; i < size; i++ ){
            int earliest = i;
            for(int j = i+1; j < size; j++){
                if( roster[earliest].getLastPaymentDate().compareTo(roster[j].getLastPaymentDate()) > 0 ){
                    earliest = j;
                }
            }
            swap(roster, i, earliest);
        }
    }

    /**
     * Prints the full string representation of each Student object in the order in which their last payments were
     * made.
     */
    public void printByDate(){
        if( isEmpty() ){
            System.out.println("Student roster is empty!");
        }else {
            sortByDate(roster);
            System.out.println("* list of students made payments ordered by payment date **");
            for (int c = 0; c < size; c++) {
                if(roster[c].getLastPaymentDate() == "--/--/--"){
                    continue;
                }
                System.out.println(roster[c].fullStringRep());
            }
            System.out.println("* end of roster **");
        }
    }

    /**
     * Method used by the sorting methods to swap any two inputted elements of the inputted Roster.
     * @param roster roster that has the values to be swapped.
     * @param first
     * @param second
     */
    private void swap(Student[] roster, int first, int second){
        //Swap helper method for selection sort algorithm
        Student save = roster[first];
        roster[first] = roster[second];
        roster[second] = save;
    }

    /**
     Getter method for size of the roster so it can be used in
     the tuition manager class
     @return size
     */
    public int getNumStudents(){
        return size;
    }

    /**
     * Calls the tuitionDue() method on each Student object in the Roster to calculate their respective tuitions.
     */
    public void calculateTuition(){
        for(int i = 0; i < size; i++){
            roster[i].tuitionDue();
        }
    }

}