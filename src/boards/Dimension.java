package boards;

import utils.IReader;

class Dimension implements Validable{

    private double value;

    Dimension(){
        this.value=0;
    }

    Dimension(double val){
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
