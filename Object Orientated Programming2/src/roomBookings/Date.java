package roomBookings;



import java.io.Serializable;

/**
 *   The calenderBookingClasses.Date class here is used to pick up on the simpler calenderBookingClasses.Date class
 *   from week 2 in order to show how we can restrict the creation of
 *   dates to admissible dates. To this end we write a method
 *   admissible which checks whether inputs of the constructor form a
 *   valid date. If not on constuction an IllegalArgumentException is
 *   thrown.
 *
 *   @version 2017-10-19
 *   @author Manfred Kerber
 */
public class Date implements Serializable {

    /**
     * Three field variables for day, month, and year of types int,
     * String and int, respectively.
     */
    private int day;
    private String month;
    private int year;
    private int hour;
    private int numericValMonth = 0;

    /* A static constant to list the 12 months of the year. */
    public static final String[] MONTHS =
            {"January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"};

    /* A static constant to list the lengths of the 12 months of the year. */
    public static final int[] MONTH_LENGTHS =
            {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static final int[] BOOKING_HOUR_BOUNDS = {9, 17};


    /**
     *  @param day The input of a day such as 21 as an int.
     *  @param month The input of a month such as "October" as a String.
     *  @param year The input of a year such as 2016 as an int.
     *  Note that the constructor throws an IllegalArgumentException if
     *  the date to be constructed would be not admissible.
     *  @exception IllegalArgumentException if the date to be
     *  constructed is not admissible.
     */
    public Date(int day, String month, int year, int hour) {

        if (admissible(day, month, year, hour)) {
            this.day = day;
            this.month = month;
            this.year = year;
            this.hour = hour;
        } else {
            throw new
                    IllegalArgumentException("Invalid date in class calenderBookingClasses.Date.");
        }
    }


    /**
     *  @param day The input of a day such as 21 as an int.
     *  @param month The input of a month such as "October" as a String.
     *  @param year The input of a year such as 2016 as an int.
     *  @return true if the day, the month, and the year are all admissible
     */
    public  boolean admissible(int day, String month, int year, int hour) {
        return admissibleYear(year) && admissibleMonth(month)
                && admissibleDay(day, month, year) && admissibleHour(hour);
    }


    public  boolean admissibleHour(int hour){

        return hour >= BOOKING_HOUR_BOUNDS[0] && hour <= BOOKING_HOUR_BOUNDS[1];
    }
    /**
     *  @param year The input of a year such as 2016 as an int.
     *  @return true if the int is a year greater than 0.
     */
    public  boolean admissibleYear(int year) {
        return year > 0;
    }

    /**
     *  @param month The input of a month such as "October" as a String.
     *  @return true if the String represents one of the 12 months.
     */
    public  boolean admissibleMonth(String month) {
        for (int i = 0; i < MONTHS.length; i++) {
            if (MONTHS[i].equalsIgnoreCase(month)) {
                this.setNumericValMonth(i+1);
                return true;
            }
        }
        return false;
    }

    /**
     *  @param year The input of a year such as 2016 as an int.
     *  @return true if and only if the year is a leap year.
     */
    public  boolean leapYear(int year) {
        /* every year divisible by 400 is a leap year */
        if (year%400 == 0) {
            return true;
            /* every year divisible by 100 (but not by 400) is not a
             * leap year
             */
        } else if (year%100 == 0) {
            return false;
            /* every year divisible by 4 (not covered by the cases above) is a
             * leap year
             */
        } else if (year%4 == 0) {
            return true;
            /* every other year is not a leap year */
        } else {
            return false;
        }
    }

    /**
     *  @param month The input of a month such as "October" as a String.
     *  @param year The input of a year such as 2016 as an int.
     *  @return The number of days in the month, e.g. 31 for "October".
     *  For February (MONTHS[1]) of leap years it is 29, else the value
     *  is looked up in the MONTH_LENGTHS array.
     */
    public  int maxNumberOfDaysPerMonth(String month, int year) {
        if (month.equalsIgnoreCase(MONTHS[1]) && leapYear(year)) {
            return 29;
        }
        for (int i = 0; i < 12; i++) {
            if (month.equalsIgnoreCase(MONTHS[i])) {
                return MONTH_LENGTHS[i];
            }
        }
        return 0;
    }

    /**
     *  @param day The input of a day such as 21 as an int.
     *  @param month The input of a month such as "October" as a String.
     *  @param year The input of a year such as 2016 as an int.
     *  @return true if the day is between 1 and the maximal number of
     *  days of the months in that year.
     *
     */
    public  boolean admissibleDay(int day, String month, int year) {
        return 1 <= day && day <= maxNumberOfDaysPerMonth(month,year);
    }

    public String toString() {
        return this.day + " " + this.month + " " + this.year;
    }

    /**
     *  @param day The new day in a month such as 21 as an int.
     *  @exception IllegalArgumentException if the day to be
     *  set would lead to a non-admissible date.
     */
    public void setDay(int day){
        if (admissible(day, month, year, hour)) {
            this.day = day;
        } else {
            throw new
                    IllegalArgumentException("Invalid day in setDay.");
        }
    }

    /**
     *  @param month The new month.
     *  @exception IllegalArgumentException if the month to be
     *  set would lead to a non-admissible date.
     */
    public void setMonth(String month){
        if (admissible(day, month, year, hour)) {
            this.month = month;
        } else {
            throw new
                    IllegalArgumentException("Invalid month in setMonth.");
        }
    }

    /**
     *  @param year The new year.
     *  @exception IllegalArgumentException if the year to be
     *  set would lead to a non-admissible date.
     */
    public void setYear(int year){
        if (admissible(day, month, year, hour)) {
            this.year = year;
        } else {
            throw new
                    IllegalArgumentException("Invalid year in setYear.");
        }
    }

    public int getYear() {
        return year;
    }

    public int getNumericValMonth() {
        return numericValMonth;
    }

    public void setNumericValMonth(int numericValMonth) {
        this.numericValMonth = numericValMonth;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public String getMonth() {
        return month;
    }

    /*
     *  Main method for some initial tests.
     */
    public static void main(String[] args) {

    }
}