package boards;

class Price implements Validable {

    private double value;

    Price(){
        this.value = 0;
    }

    Price(double val){
        this.value = val;
    }

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
