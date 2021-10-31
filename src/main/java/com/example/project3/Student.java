package com.example.project3;

import java.text.DecimalFormat;
/**
 * The com.example.project3.Student class holds the common data fields and operations for the com.example.project3.Resident, com.example.project3.TriState, com.example.project3.InternationalTest.NonResident, and
 * com.example.project3.International classes. All data fields are private and must be retrieved using getter and setter methods, abstract
 * methods within the class are overridden by com.example.project3.Student's subclasses.
 * @author Adams, Jennifer
 */

public class Student {
    private Profile profile;
    private int credits;
    private double tuition;
    private double balance;
    private double FULL_TIME_FEE = 3268;
    private double PART_TIME_FEE = 2614.4;
    //-1 = Part Time, 0 = Full Time less than 16 credits, 1 = Full time greater than 16 credits
    private int status;
    private String lastPaymentDate;
    //Boolean stating whether the student has received their one time financial aid bonus yet or not.
    private boolean finAidApplied;
    //Number of credits after which the student will be charged on an additional per-credit basis for each credit
    //exceeding the EXCESS POINT
    private double EXCESS_POINT = 16;
    private double paymentMade;
    private double aidAmount;

    /**
     * Returns the date of this com.example.project3.Student's last payment
     * @return
     */
    public String getLastPaymentDate(){
        return lastPaymentDate;
    }

    /**
     * Takes in amount of aid offered to the com.example.project3.Student and deducts it from this com.example.project3.Student's outstanding balance. Sets
     * the finAidApplied field to true to indicate that this com.example.project3.Student has received their one-time financial aid benefit.
     * @param aidAmount
     */
    public void applyAid(double aidAmount){
        balance -= aidAmount;
        finAidApplied = true;
        this.aidAmount = aidAmount;
    }

    /**
     * Returns amount of aid an instance of com.example.project3.Student has received.
     * @return
     */
    public double getaidAmount(){
        return aidAmount;
    }

    /**
     * Updates this com.example.project3.Student's paymentMade field to be whatever is passed as an argument.
     * @param paymentMade
     */
    public void setPaymentMade(double paymentMade){
        this.paymentMade = paymentMade;
    }

    /**
     * Returns double representing the number of credits after which a com.example.project3.Student will be additionally charged for the extra
     * credit hours they take on a per-credit basis.
     * @return
     */
    public double getEXCESS_POINT(){
        return EXCESS_POINT;
    }

    /**
     * Updates lastPaymentDate field of the com.example.project3.Student to whatever was passed.
     * @param lastPaymentDate
     */
    public void setLastPaymentDate(String lastPaymentDate){
        this.lastPaymentDate = lastPaymentDate;
    }

    /**
     * Returns boolean representing whether this com.example.project3.Student has received their one time financial aid benefit.
     * @return
     */
    public boolean getfinAidApplied(){
        return finAidApplied;
    }

    /**
     * Takes an integer representing the amount the user wants to pay for the com.example.project3.Student's tuition. If the outstanding balance
     * is smaller than this amount, the payment is rejected and the method returns false; otherwise the payment is made
     * and the method returns true.
     * @param paymentAmount amount to be paid
     * @return
     */
    public void payTuition( double paymentAmount ){
        paymentMade += paymentAmount;
        balance -= paymentAmount;
    }


    /**
     * Returns integer representing the status of this com.example.project3.Student based on its number of credit hours.
     * @return
     */
    public int getStatus(){
        return status;
    }

    /**
     * Returns double representing the university fee that part-time students must pay
     * @return
     */
    public double getPART_TIME_FEE(){
        return PART_TIME_FEE;
    }

    /**
     * Returns double representing the university fee that full-time students must pay
     * @return
     */
    public double getFULL_TIME_FEE() {
        return FULL_TIME_FEE;
    }

    /**
     * Sets the com.example.project3.Student's tuition to be whatever double is passed.
     * @param tuition
     */
    public void setTuition(double tuition){
        this.tuition = tuition;
    }

    /**
     * Returns double representing this com.example.project3.Student's tuition.
     * @return
     */
    public double getTuition(){
        return tuition;
    }

    /**
     * This is an abstract method that is overridden by each of the subclasses. The tuition that an instance of a com.example.project3.Student
     * owes is calculated upon calling this method.
     */
    public void tuitionDue() {
    }

    /**
     * Updates this com.example.project3.Student's number of credit hours to be whatever integer amount is passed in.
     * @param credits
     */
    public void setCredits(int credits){
        this.credits = credits;
    }

    /**
     * Updates this com.example.project3.Student's outstanding balance to be whatever integer amount is passed in.
     * @param balance
     */
    public void setBalance(double balance){
        this.balance = balance;
    }

    /**
     * Constructor for an instance of the com.example.project3.Student class. Takes an instance of com.example.project3.Profile and an integer number of credit hours
     * as parameters. Reads number of credits and assigns one of three statuses to the student based on them: -1 meaning
     * part-time if the number of credit hours is less than 12, 0 meaning full time if the number of credit hours is
     * between 12 and 16, or 1 if the number of credit hours is greater than 16. Sets finAidApplied to false by default.
     * @param profile
     * @param credits
     */
    public Student(Profile profile, int credits){

        this.profile = profile;
        this.credits = credits;
        if( credits < 12 ){
            this.status = -1;
        }else if( credits <= 16 ) {
            this.status = 0;
        }else{
            this.status = 1;
        }
        lastPaymentDate = "--/--/--";
        finAidApplied = false;
        paymentMade = 0;
    }

    /**
     * Constructor for the com.example.project3.Student class, converts inputted String representation of a major, converts it, and stores
     * the given name and major.
     */
    public Student(Profile profile){
        this.profile = profile;
    }

    public double getPaymentMade(){
        return paymentMade;
    }

    /**
     * Returns instance of the com.example.project3.Profile class assigned to this instance of the com.example.project3.Student class.
     * @return
     */
    public Profile getProfile(){
        return this.profile;
    }

    /**
     * Returns number of credit hours attributed to this com.example.project3.Student.
     * @return
     */
    public int getCredits(){ return credits; }

    /**
     * Comparison method that returns the equality of this instance of com.example.project3.Student and an inputted instance of com.example.project3.Student
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Student) {
            Student student = (Student) obj;
            return student.profile.equals(this.profile);
        }
        return false;
    }

    /**
     * Method that returns the string representation of this com.example.project3.Student's profile
     * @return
     */
    @Override
    public String toString() {
        return profile.toString();
    }

    /**
     * Returns full string representation of this student, including information such as profile, credit load, tuition,
     * total payment made, and date of last payment. Method is overridden by subclasses to include more information.
     * @return
     */
    public String fullStringRep() {
        DecimalFormat df = new DecimalFormat("###,###.00");
        String result = profile.toString()+":"+credits+" credit hours:tuition due:"+df.format(balance)+":total payment:"+
                df.format(paymentMade)+":last payment date: "+lastPaymentDate+":";
        return result;
    }
}