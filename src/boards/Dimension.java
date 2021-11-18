package boards;

import utils.IReader;

class Dimension implements Validable {

    private int value;

    Dimension(){
        this.value=0;
    }

    Dimension(int val){
        this.value = val;
    }

    boolean isValid(){
        if (this.value <= 0){
            return false;
        }
        return true;
    }

    int getValue() {
        return value;
    }

    void setValue(int value) {
        this.value = value;
    }

    static Dimension read(IReader r){
        return new Dimension(r.readInt());
    }
}
