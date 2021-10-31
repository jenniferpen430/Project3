package com.example.project3;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 The com.example.project3.Date class stores the day, month, and year from the user input and makes
 sure a date is valid.
 @author Adams, Jennifer
 */

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;
    public static final int YEAR_MIN = 2021;
    public static final int DAY_NORM = 30;
    public static final int DAY_MAX = 31;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int LEAP_DAYS = 29;
    public static final int NON_LEAP = 28;
    public static final int DAY_MIN = 1;

    /**
     This method is to construct the com.example.project3.Date object from the user input string.
     The day, month, and year is separated by using the String tokenizer.
     @param String of date from user input
     */
    public Date(String date) {
        StringTokenizer st = new StringTokenizer(date, "/");

        this.month = Integer.parseInt(st.nextToken().trim());
        this.day = Integer.parseInt(st.nextToken().trim());
        this.year = Integer.parseInt(st.nextToken().trim());

    }

    /**
     This method creates a com.example.project3.Date object with today’s date with an instance of
     the Calender class
     */
    public Date(){
        Calendar todayNow = Calendar.getInstance();
        this.day = todayNow.get(Calendar.DAY_OF_MONTH+1);
        this.month = todayNow.get(Calendar.MONTH) ;
        this.year = todayNow.get(Calendar.YEAR);
    }
    /**
     This method checks if the date is valid or not
     */
    public boolean isValid() {
        Date todaysDate = new Date();
        if(year < YEAR_MIN || year > todaysDate.getYear() || (year == todaysDate.getYear() && month > todaysDate.getMonth()) || (year == todaysDate.getYear() && month == todaysDate.getMonth() && day > todaysDate.getDay())){
            return false;
        }
        else if(month < JANUARY || month > DECEMBER || day < DAY_MIN){
            return false;
        }
        else if((month == JANUARY  || month == MARCH || month  == MAY || month  == JULY || month == AUGUST || month  == OCTOBER || month == DECEMBER) && day > DAY_MAX ){
            return false;
        }
        else if((month == APRIL || month == JUNE || month == SEPTEMBER || month ==  NOVEMBER) && day > DAY_NORM){
            return false;
        }
        else if (month == FEBRUARY) { //check leap year
            return leapCheck();
        }
        return true;
    }

    /**
     This method checks if the date is a proper leap year
     */
    public boolean leapCheck(){
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                if (year % QUATERCENTENNIAL == 0) {
                    if (day > LEAP_DAYS) {
                        return false;
                    }
                } else { //STEP 5
                    if (day > NON_LEAP) {
                        return false;
                    }
                }
            } else {
                if (day > LEAP_DAYS) {
                    return false;
                }
            }
        } else {
            if (day > NON_LEAP) {
                return false;
            }
        }
        return true;
    }

    /**
     This method checks if the date is greater, lessthan or equal to the input date in the
     parameter
     EX:
     // 09/19/1990 vs 09/18/1991 should return -1
     // 09/19/1991 vs 09/18/1990 should return 1
     // 09/19/1990 vs 08/18/1990 should return 1
     */
    @Override
    public int compareTo(Date date) {
        int GREATER = 1;
        int LESSTHAN = -1;
        int EQUAL = 0;

        if(year > date.getYear() || (year == date.getYear() && month > date.getMonth()) || (year == date.getYear()
                && month == date.getMonth() && day > date.getDay())){
            return GREATER;
        }
        else if(year < date.getYear() || (year == date.getYear() && month < date.getMonth()) || (year == date.getYear()
                && month == date.getMonth() && day < date.getDay())){
            return LESSTHAN;
        }
        return EQUAL;
    }

    /**
     This method takes the date and returns the string version of the date
     formatted
     */
    public String toString(){
        return month + "/" + day + "/" + year;
    }

    /**
     Getter method for day so it can be used in other classes
     @return day
     */
    public int getDay(){
        return day;
    }

    /**
     Getter method for day so it can be used in other classes
     @return month
     */
    public int getMonth(){
        return month;
    }

    /**
     Getter method for year so that
     @return year
     */
    public int getYear(){
        return year;
    }

}