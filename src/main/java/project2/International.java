/**
 * International is a subclass of NonResident and contains the necessary data fields and operations for objects of
 * the International type.
 * @author Adams, Jennifer
 */

public class International extends NonResident {
    private boolean studyAbroad;
    private double ADDITIONAL_FEE = 2650;

    /**
     * Constructor for the International class; sets study abroad boolean to be whatever is passed aside from the
     * Profile and credit load.
     * @param profile profile of the International student to be added
     * @param credit credit load of the International student to be added
     * @param studyAbroad study abroad status of the International student to be added
     */
    public International(Profile profile, int credit, boolean studyAbroad){
        super(profile, credit);
        this.studyAbroad = studyAbroad;
    }

    /**
     * Sets studyAbroad status of an object of the International type to be whatever is passed.
     * @param studyAbroad studyAbroad status update
     */
    public void setStudyAbroad(boolean studyAbroad){
        this.studyAbroad = studyAbroad;
    }

    /**
     * Calculates the tuition that an object of this class needs to pay based on its number of credits and an
     * additional fee. Avoids having to pay tuition depending on the value of the studyAbroad boolean.
     */
    @Override
    public void tuitionDue() {
        if(!studyAbroad) {
            super.tuitionDue();
            setTuition(getTuition()+ADDITIONAL_FEE);
        }else{
            setTuition(getFULL_TIME_FEE()+ADDITIONAL_FEE);
        }
        if(getPaymentMade() == 0) {
            setBalance(getTuition());
        }
    }

    /**
     * Returns full string representation of the International object.
     * @return
     */
    @Override
    public String fullStringRep(){
        String result = super.fullStringRep() +  ":international";
        if(studyAbroad){
            return result + ":study abroad";
        }
        return result;
    }
}