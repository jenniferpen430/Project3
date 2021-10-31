package com.example.project3;

/**
 * The com.example.project3.Major enum class stores the value of each major that the students that are passed into
 * the roster could be.
 * @author Adams, Jennifer
 */

public enum Major {
    CS, IT, BA, EE, ME;

    /**
     * The includes method takes a string as an argument and returns a boolean based on whether that string
     * is any of the majors. If it is, the method returns true, otherwise it returns false.
     * @param string string representation of a major
     * @return
     */
    public static boolean includes(String string){
        string = string.toUpperCase();
        if(string.equals("CS") || string.equals("IT") || string.equals("BA") || string.equals("EE")
                || string.equals("ME")){
            return true;
        }else {
            return false;
        }
    }
}