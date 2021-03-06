package com.example.project3;

/**
 * com.example.project3.Profile class holds the identifiable information for each com.example.project3.Student; namely their name and major, as well as related
 * operations.
 * @author Adams, Jennifer
 */
public class Profile {
    private String name;
    private Major major; //5 majors and 2-character each: CS, IT, BA, EE, ME

    /**
     * Constructor for the Profile class, converts inputted String representation of a major, converts it, and stores
     * the given name and major.
     * @param name name of new Profile
     * @param major major of new Profile
     */
    public Profile(String name, String major){
        Major majorAsEnum = Major.valueOf(major);
        this.name = name;
        this.major = majorAsEnum;
    }

    /**
     * Returns the name related to this instance of Profile
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the major related to this instance of Profile
     * @return
     */
    public Major getMajor() {
        return major;
    }


    /**
     * Determines the equivalence between two instances of Profile
     * @param obj object to be compared
     * @return
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Profile){
            Profile profile = (Profile) obj;
            if(profile.name.equals(this.name) && profile.major == this.major){
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * Returns string representation of the Major instance
     * @return
     */
    @Override
    public String toString(){
        return name + ":" + major;
    }
}