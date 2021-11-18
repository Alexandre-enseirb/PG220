package boards;

class Date implements Validable {
    private int day;
    private int month;
    private int year;

    private int[] monthlen = {31,28,31,30,31,30,31,31,30,31,30,31};

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

        if (this.year < 1970 || this.year > 2030){
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

        /* finally */
        return true;
    }

}
