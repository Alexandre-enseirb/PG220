package boards;

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

    public int getValue(){
        return this.value;
    }

}
