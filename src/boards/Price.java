package boards;

/**
 * Price of the board
 */
class Price implements Validable {

    private double value;

    Price(){
        this.value = 0;
    }

    Price(double val){
        this.value = val;
    }

    /**
     * tests if the value is valid
     * @return true if the value is greater than 0, false else
     */
    public boolean isValid(){
        if (this.value <= 0){
            return false;
        }
        return true;
    }
    double getValue() {
        return value;
    }

    void setValue(double value) {
        this.value = value;

    }
}
