package com.example.project3;
import com.example.project3.Profile;
import com.example.project3.Student;

/**
 * NonResident is a subclass of Student that contains the common data fields and operations for International and
 * TriState Students.
 */

public class NonResident extends Student{

    //NonResident Per Credit Fee
    private double N_P_FEE = 966;
    //NonResident Tuition
    private double N_TUITION = 29737;

    /**
     * Constructor for the NonResident class. Takes a Profile and number of credit hours.
     * @param profile profile of Student to be created
     * @param credits credits taken by student
     */
    public NonResident(Profile profile, int credits){
        super(profile, credits);
    }

    /**
     * Calculates the tuition that an object of this class needs to pay based on its number of credits.
     */
    @Override
    public void tuitionDue(){
        if( getStatus() == -1 ){
            //Part Time NonResident
            setTuition(N_P_FEE * getCredits() + getPART_TIME_FEE());
        }else if( getStatus() == 0){
            //Full Time NonResident
            setTuition( N_TUITION + getFULL_TIME_FEE());
        }else{
            //Full Time NonResident with > 16 credits
            setTuition(N_TUITION + getFULL_TIME_FEE() + N_P_FEE * ( getCredits() - getEXCESS_POINT()) );
        }
        if(getPaymentMade() == 0) {
            setBalance(getTuition());
        }
    }

    /**
     * Returns full string representation of this object.
     * @return
     */
    @Override
    public String fullStringRep(){
        String result = super.fullStringRep() +  "non-resident";
        return result;
    }
}