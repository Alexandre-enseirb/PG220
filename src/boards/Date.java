package boards;

import utils.IReader;

class Date implements Validable {
    private int day;
    private int month;
    private int year;

    private int[] monthlen = {31,28,31,30,31,30,31,31,30,31,30,31};

    int getDay() {
        return day;
    }

    void setDay(int day) {
        this.day = day;
    }

    int getMonth() {
        return month;
    }

    void setMonth(int month) {
        this.month = month;
    }

    int getYear() {
        return year;
    }

    void setYear(int year) {
        this.year = year;
    }

    Date(){
        /* By default : first valid date */
        this.day=1;
        this.month=1;
        this.year=1970;
    }


    Date(int day, int month, int year){
        this.day=day;
        this.month=month;
        this.year=year;
    }

    public boolean isValid(){
        /* year must be between 1970 (epoch) and 2030 (no commands after) */
        boolean leap=false; /* is it a leap year ? */
        int maxdays;

        if (this.year < 0 || this.year > 99){
            return false;
        }
        if (this.month < 1 || this.month > 12){
            return false;
        }

        if ( (this.year%4==0 && this.year%100!=0) || (this.year%400==0) ){
            leap=true;
        }

        maxdays = ( this.month == 2 && leap ) ? monthlen[1]+1:monthlen[this.month-1];

        if (this.day < 1 || this.day > maxdays){
            return false;
        }
        System.out.println("date valid");

        /* finally */
        return true;
    }

    boolean comesAfter(Date d){
        if (this.year > d.getYear())
            return true;
        if (this.month > d.getMonth())
            return true;
        if (this.day > d.getDay())
            return true;
        return false;
    }

    public String toString(){
        return Integer.toString(this.day)+
                "."+Integer.toString(this.month)+
                "."+Integer.toString(this.year);
    }

}
