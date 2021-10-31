import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * TuitionManager is the user interface class for the Program. Here, inputs are read and outputs are given based on
 * them.
 * @author Adams, Jennifer
 */

public class TuitionManager{
    private Roster roster;
    private String name;
    private String major;
    private String credits;
    private String command;
    private boolean abroad;
    private String payment;
    private String aid;

    /**
     * Run is called when the program begins and is where the inputs are taken in
     */
    public void run(){
        System.out.println("Tuition Manager starts running.");
        Scanner scanner = new Scanner(System.in);
        String line  = scanner.nextLine();
        this.roster = new Roster();
        while (!line.equals("Q")) {
            StringTokenizer st = new StringTokenizer(line, ",", false);
            try {
                command = st.nextToken();
                if (command.equals("P") || command.equals("PT") || command.equals("PN") || command.equals("C")) {
                        printOrCalc(command);
                } else if (command.equals("T") || command.equals("S") || command.equals("F") || command.equals("R")
                        || command.equals("AR") || command.equals("AN") || command.equals("AT")|| command.equals("AI")){
                        name = st.nextToken();
                        major = st.nextToken();
                        if (command.equals("R")) {
                            remove();
                        }else if (command.equals("S") || command.equals("F")) {
                            try {
                                aid = st.nextToken();
                            } catch (NoSuchElementException ex) {
                                System.out.println("Missing the amount.");
                                line = scanner.nextLine();
                                continue;
                            }
                            sOrF(command);
                        }else if (command.equals("T")) {
                            try {
                                payment = st.nextToken();
                            } catch (NoSuchElementException ex) {
                                System.out.println("Payment amount missing.");
                                line = scanner.nextLine();
                                continue;
                            }
                            if(!payment.equals("0")) {
                                pay(payment, st.nextToken());
                            }else{
                                System.out.println("Invalid amount.");
                            }
                        } else if(command.equals("AT")||command.equals("AI")||command.equals("AN")||command.equals("AR")) {
                            try {
                                credits = st.nextToken();
                            } catch (NoSuchElementException ex) {
                                System.out.println("Credit hours is missing.");
                                line = scanner.nextLine();
                                continue;
                            }
                            if (command.equals("AT") || command.equals("AI")) {
                                addTOrI(command, st.nextToken());
                            } else if (command.equals("AN") || command.equals("AR")) {
                                addNRorR(command);
                            }
                        }
                    } else {
                    System.out.println("\nCommand '" + command + "' is not supported!");
                }
                line = scanner.nextLine();
            } catch (NoSuchElementException ex){
                    System.out.println("Missing data in command line.");
                    line = scanner.nextLine();
                    continue;
            }
        }
        System.out.println("Tuition Manager terminated.");
    }

    /**
     * This method is called when the user wants to add a nonresident or resident
     * @param command
     */
    private void addNRorR(String command){
        if(command.equals("AN")){
            addNonResident();
        }else if (command.equals("AR")) {
            addResident();
        }
    }

    /**
     * This method is called to distribute aid.
     * @param command
     */
    private void sOrF(String command){
        if(command.equals("S")){
            studyAbroad(aid);
        } else {
            finaid(aid);
        }
    }

    /**
     * This method is used to add a TriState or International student
     * @param command
     * @param nextToken
     */
    private void addTOrI(String command, String nextToken){
        if(command.equals("AT")) {
            addTriState(nextToken);
        }else{
            addInternational(nextToken);
        }

    }

    /**
     * This method is used to add a non resident
     */
    private void addNonResident(){
        if(!Major.includes(major.toUpperCase())){
            System.out.println("'" + major + "' is not a valid major.");
        }else {
            Profile profile = new Profile(name, major.toUpperCase());
            if(validCredits()) {
                int numCredit = Integer.parseInt(credits);
                NonResident nonResident = new NonResident(profile, numCredit);
                if (roster.add(nonResident)) {
                    System.out.println("Student added.");
                } else {
                    System.out.println("Student is already in the roster.");
                }
            }
        }
    }

    /**
     * This method is used to add a TriState student to the roster
     * @param state
     */
    private void addTriState(String state) {
        if(!Major.includes(major.toUpperCase())){
            System.out.println("'" + major + "' is not a valid major.");
        }else {
            Profile profile = new Profile(name, major.toUpperCase());
            if (state.toUpperCase().replaceAll("\\s+","").equals("NY")
                    || state.toUpperCase().replaceAll("\\s+","").equals("CT")) {
                if(validCredits()) {
                    int creditsAsInt = Integer.parseInt(credits);
                    TriState tristate = new TriState(profile, creditsAsInt, state);
                    if (roster.add(tristate)) {
                        System.out.println("Student added.");
                    } else {
                        System.out.println("Student is already in the roster.");
                    }
                }
            } else {
                System.out.println("Not part of the tri-state area.");
            }
        }
    }

    /**
     * This method adds an International student to the roster
     * @param status
     */
    private void addInternational(String status ){
        if(!Major.includes(major.toUpperCase())){
            System.out.println("'" + major + "' is not a valid major.");
        }else {
            Profile profile = new Profile(name, major.toUpperCase());
            if (status.toUpperCase().equals("TRUE")) {
                abroad = true;
            } else if (status.toUpperCase().equals("FALSE")) {
                abroad = false;
            }
            if (validCredits()) {
                int creditAsInt = Integer.parseInt(credits);
                if (creditAsInt >= 12) {
                    International international = new International(profile, creditAsInt, abroad);
                    if (roster.add(international)) {
                        System.out.println("Student added.");
                    } else {
                        System.out.println("Student is already in the roster.");
                    }
                } else {
                    System.out.println("International students must enroll at least 12 credits");
                }
            }
        }
    }

    /**
     * This method adds a Resident to the roster
     */
    private void addResident(){
        if(!Major.includes(major.toUpperCase())){
            System.out.println("'" + major + "' is not a valid major.");
        }else {
            Profile profile = new Profile(name, major.toUpperCase());
            if (validCredits()) {
                int creditAsInt = Integer.parseInt(credits);
                Resident resident = new Resident(profile, creditAsInt);
                if(!roster.add(resident)){
                    System.out.println("Student is already in the roster.");
                } else{
                    System.out.println("Student added.");
                }
            }
        }
    }

    /**
     * What to do if the inputted credit load is invalid for the purposes of this program.
     * @return
     */
    private boolean validCredits(){
        try{
            int creditAsInt = Integer.parseInt(credits);
            if(creditAsInt < 3 && creditAsInt >= 0 ){
                System.out.println("Minimum credit hours is 3.");
                return false;
            }else if(creditAsInt > 24){
                System.out.println("Credit hours exceed the maximum 24.");
                return false;
            }else if(creditAsInt < 0){
                System.out.println("Credit hours cannot be negative");
                return false;
            }
            return true;
        }
        catch (NumberFormatException ex){
            System.out.println("Invalid credit hours.");
            return false;
        }
    }

    /**
     * Removes a Student from the Roster
     */
    private void remove(){
        if(!Major.includes(major.toUpperCase())){
            System.out.println("'" + major + "' is not a valid major.");
        }else {
            Profile profile = new Profile(name, major.toUpperCase());
            if (roster.getNumStudents() == 0) {
                System.out.println("Student roster is empty!");
            } else {
                try {
                    Student studentRemove = new Student(profile);
                    if (roster.remove(studentRemove)) {
                        System.out.println("Student removed from the roster.");
                    }
                } catch (IndexOutOfBoundsException ex){
                        System.out.println("Student is not in the roster.");
                }
            }
        }

    }

    /**
     * Calculates the tuition of each student
     */
    private void calculate(){
        int numStudents = roster.getNumStudents();
        if(numStudents == 0 || numStudents < 0){
            System.out.println("Student roster is empty!");
        }
        else{
            roster.calculateTuition();
            System.out.println("Calculation completed.");
        }
    }

    /**
     * Sets the studyAbroad status of a Student
     * @param abroad
     */
    private void studyAbroad(String abroad){
        Profile profile = new Profile(name, major.toUpperCase());
        boolean status  = false;
        try{
            Student studentAbroad = new Student(profile);
            studentAbroad = roster.placement(studentAbroad);
            if(abroad.equals("false") || abroad.equals("true")) {
                if(abroad.equals("true")){ status = true; }
                if (studentAbroad instanceof International) {
                    if(studentAbroad.getCredits() > 12){
                        studentAbroad.setCredits(12);
                    }
                    ((International) studentAbroad).setStudyAbroad(status);
                    studentAbroad.setLastPaymentDate("--/--/--");
                    studentAbroad.setPaymentMade(0.00);
                    studentAbroad.tuitionDue();
                    System.out.println("Tuition updated.");
                } else {
                    System.out.println("Couldn't find the international student.");
                }
            }
            else{
                System.out.println("Invalid status");
            }

        }
        catch (IndexOutOfBoundsException ex){
            System.out.println("Couldn't find the international student.");
        }
    }

    /**
     * Processes payments on a given date
     * @param payment
     * @param date
     */
    private void pay(String payment, String date){
        if(!Major.includes(major.toUpperCase())){
            System.out.println("'" + major + "' is not a valid major.");
        }else {
            double amount = Double.parseDouble(payment);
            Date paymentDate = new Date(date);
            Profile profile = new Profile(name, major.toUpperCase());
            Student student = new Student(profile);
            if (paymentDate.isValid()) {
                student = roster.placement(student);
                if (student.getTuition() >= amount) {
                    if(amount > 0 ) {
                        student.payTuition(amount);
                        student.setLastPaymentDate(date);
                        System.out.println("Payment applied.");
                    }
                    else{
                        System.out.println("Invalid amount.");
                    }
                }
                else {
                    System.out.println("Amount is greater than amount due");
                }
            } else {
                System.out.println("Payment date invalid.");
            }
        }
    }

    /**
     * Processes and applies financial aid if applicable
     * @param aid
     */
    private void finaid(String aid){
        double aidAmount = Double.parseDouble(aid);
        Profile profile = new Profile(name, major.toUpperCase());
        Student student = new Student(profile);
        student = roster.placement(student);
        if( aidAmount < 0  || aidAmount > 10000){
            System.out.println("Invalid amount.");
        }else if(!roster.isHere(student)){
            System.out.println("Student not in the roster.");
        }else{
            if(student instanceof  Resident) {
                if(student.getStatus() != -1){
                    if(!student.getfinAidApplied()) {
                        student.applyAid(aidAmount);
                        System.out.println("Tuition updated");
                    }else{
                        System.out.println("Awarded once already.");
                    }
                } else {
                    System.out.println("Parttime student doesn't qualify for the award.");
                }

            }else{
                System.out.println("Not a resident student.");
            }

        }
    }

    /**
     * Determines whether to print or calculate
     * @param command
     */
    private void printOrCalc(String command){
        if (command.equals("P")){
            roster.print();
        } else if(command.equals("PN")) {
            roster.printByName();
        } else if(command.equals("PT")) {
            roster.printByDate();
        } else if(command.equals("C")){
            calculate();
        }
    }
}