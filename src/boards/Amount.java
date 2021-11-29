package boards;

import utils.IReader;

class Amount implements Validable {

    private int value;

    Amount(){
        this.value=0;
    }

    Amount(int val){
        this.value=val;
    }

    public boolean isValid(){
        if (this.value <= 0){
            return false;
        }
        return true;
    }

    int getValue(){
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
