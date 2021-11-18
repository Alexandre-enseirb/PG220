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

    static Amount read(IReader r){
        return new Amount(r.readInt());
    }
}
