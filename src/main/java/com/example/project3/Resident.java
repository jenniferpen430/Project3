package com.example.project3;

import com.example.project3.Profile;

import java.text.DecimalFormat;

public class Resident extends Student{

    //Full Time Resident Tuition
    private double FT_RES_TUI = 12536;
    //Resident Per Credit Fee
    private double R_PC_FEE = 404;

    public Resident(Profile profile, int credits){
        super(profile, credits);
    }

    /**
     * Calculates the tuition that an object of this class needs to pay based on its number of credits.
     */
    @Override
    public void tuitionDue(){
        if( getStatus() == -1 ){
            //Part Time
            setTuition(getCredits() * R_PC_FEE + getPART_TIME_FEE());
        }
        else if( getStatus() == 1 ){
            //Full Time with over 16 credits
            setTuition( (getCredits() - getEXCESS_POINT()) * R_PC_FEE + FT_RES_TUI + getFULL_TIME_FEE());
        }
        else{
            //Full Time with equal to or less than 16 credits
            setTuition(FT_RES_TUI + getFULL_TIME_FEE());
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
        String result = super.fullStringRep() +  "resident";
        if( getfinAidApplied() ) {
            DecimalFormat df = new DecimalFormat("###,###.00");
            return result+":financial aid $"+df.format(getaidAmount());
        }else{
            return result;
        }
    }


}