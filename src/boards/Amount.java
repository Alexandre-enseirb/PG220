package boards;

import utils.IReader;

/**
 * class used to represent how many of a given board an actor has
 */
class Amount implements Validable {

    /**
     * value
     */
    private int value;

    /**
     * Basic constructor
     */
    Amount(){
        this.value=0;
    }

    /**
     * Overloaded constructor
     * @param val value to set
     */
    Amount(int val){
        this.value=val;
    }

    /**
     * tests if the value is strictly higher than 0
     * @return true if the value is strictly higher than 0, false else
     */
    public boolean isValid(){
        if (this.value <= 0){
            return false;
        }
        return true;
    }

    /**
     * returns the amount
     * @return amount of boards
     */
    int getValue(){
        return this.value;
    }

    /**
     * sets the amount
     * @param value amount of boards to set
     */
    void setValue(int value) {
        this.value = value;
    }


}
