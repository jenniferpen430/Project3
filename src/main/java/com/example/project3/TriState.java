package com.example.project3;

/**
 * com.example.project3.TriState is a subclass of com.example.project3.InternationalTest.NonResident and contains the necessary data fields and operations for com.example.project3.TriState students.
 * @author Adams, Jennifer
 */

public class TriState extends NonResident{

    private final static int NY_DISCOUNT =  4000;
    private final static int CT_DISCOUNT =  5000;
    private String state;

    /**
     * Constructor for the TriState class, calls superclass and sets associated state to be whatever is passed aside
     * from the Profile and credit load.
     * @param profile profile of Student to be added
     * @param credits credit load of Student to be added
     * @param state state of Student to be added
     */
    public TriState(Profile profile, int credits, String state){
        super(profile, credits);
        this.state = state.toUpperCase();
    }

    /**z
     * Calculates the tuition that an object of this class needs to pay based on its number of credits. Applies a
     * discount depending on what state is associated with the object.
     */
    public void tuitionDue(){
        super.tuitionDue();
        if(getStatus() > -1) {
            if (state.equals("NY")) {
                setTuition(getTuition() - NY_DISCOUNT);
            } else {
                setTuition(getTuition() - CT_DISCOUNT);
            }
        }
        if(getPaymentMade() == 0) {
            setBalance(getTuition());
        }
    }

    /**
     * Adds on to the full string representation of its superclasses by adding the tri-state classification, as well
     * as the specific state associated with the object.
     * @return
     */
    @Override
    public String fullStringRep(){
        String result = super.fullStringRep() +  "(tri-state):";
        if(state.equals("NY")){
            return result+"NY";
        }else {
            return result + "CT";
        }
    }

}