package boards;

import utils.IReader;

/**
 * Class representing a dimension. Can be length, width or height in 3D, but is not limited to 3D
 */
class Dimension implements Validable{

    /**
     * length in the given dimension
     */
    private double value;

    Dimension(){
        this.value=0;
    }

    Dimension(double val){
        this.value = val;
    }

    /**
     * tests if the length is greater or equal than 0
     * @return true if greater or equal than 0, false else
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
